package com.hook;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;

import com.load.ApkResources;
import com.reflect.MethodManager;
import com.utils.ContentManager;
import com.utils.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * HookFrame com.xh.hook 2018 2018-5-14 下午12:33:38 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

class HookActivityManagerServer implements InvocationHandler {
    private final static String TAG = "ActivityManagerServer";
    private Object mActivityManagerServer;
    private Hook mHook;

    public HookActivityManagerServer(Object ams, Hook hook) {
        // TODO Auto-generated constructor stub
        mActivityManagerServer = ams;
        mHook = hook;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // TODO Auto-generated method stub
        Log.i(TAG, method.getName());
        String methodName = method.getName();
        int index = intentIndex(args);
        if (index != -1) {
            Intent intent = (Intent) args[index];
            ComponentName componentName = intent.getComponent();
            if (componentName != null) {
                String className = componentName.getClassName();
                if (className != null && !className.isEmpty()) {
                    if ("startActivity".equals(methodName)) {
                        if (mHook.registerActivities.indexOf(componentName.getClassName()) < 0) {
                            ParasXml.ActivityXml activityXml = mHook.pit(componentName.getClassName());
                            if (activityXml != null) {
                                replace(intent, activityXml.pit);
                                LogUtil.i(TAG, activityXml.replace);
                                intent.setExtrasClassLoader(ParasXml.ActivityXml.class.getClassLoader());
                                intent.putExtra(Hook.MY_ACTIVITY_XML, activityXml);
                                ApkResources resources = ContentManager.getManager().name2resources(activityXml.packageName);
                                if (resources != null) {
                                    ActivityInfo[] infos = resources.mPackageInfo.activities;
                                    for (ActivityInfo info : infos) {
                                        if (info.name.equals(activityXml.className)) {
                                            intent.putExtra(Hook.MY_ACTIVITY_FLAGS, info.flags);
                                            intent.putExtra(Hook.MY_ACTIVITY_META, info.metaData);
                                            intent.putExtra(Hook.MY_ACTIVITY_SOFT_MODE, info.softInputMode);
                                            intent.putExtra(Hook.MY_ACTIVITY_OPTIONS, info.uiOptions);
                                            break;
                                        }
                                    }
                                }
                            } else
                                LogUtil.i(TAG, "not found activityXml");
                        }else
                            LogUtil.i(TAG,"is manifest");
                    } else if ("startService".equals(methodName) || "bindService".equals(methodName)) {
                        LogUtil.i(TAG, "Service");
                        if (mHook.registerServices.indexOf(componentName.getClassName()) < 0) {
                            ParasXml.ActivityXml activityXml = mHook.pit(componentName.getClassName());
                            if (activityXml != null) {
                                mHook.startServers.add(componentName.getClassName());
                                replace(intent, activityXml.pit);
                            }

                        }
                    }
                }
            }
        }
        return MethodManager.invoke(method, mActivityManagerServer, args);
    }

    private void replace(Intent intent, String className) {
        ComponentName componentName = new ComponentName(mHook.hookPackage, className);
        intent.setComponent(componentName);
    }


    private int intentIndex(Object[] args) {
        if (args != null && args.length > 0)
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent)
                    return i;
            }
        return -1;
    }
}
