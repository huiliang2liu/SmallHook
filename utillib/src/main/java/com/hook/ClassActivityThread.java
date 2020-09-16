package com.hook;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import com.reflect.ClassManager;
import com.reflect.FieldManager;
import com.reflect.MethodManager;
import com.utils.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;


/**
 * HookFrame com.xh.hook 2018 2018-5-14 上午11:33:25 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

@SuppressLint("NewApi")
class ClassActivityThread implements Callback {
    private final static String TAG = "ClassActivityThread";
    private static Class<?> activityThreadClass;
    private static Object activityThread;
    private static Object mH;
    private static Field mCallback;
    private static Class<?> h;
    private static int CREATE_SERVICE;
    private static int LAUNCH_ACTIVITY;
    private static int LOW_MEMORY;
    private static int TRIM_MEMORY;
    private static Method installContentProviders;
    private ClassLoader mClassLoader;
    private Hook mHook;
    private static Field activityIntent;
    private static Field activityInfoField;
    private HookActivityManagerServer managerServer;//兼容性问题废弃
    private HookPackageManager handler;

    static {
        activityThreadClass = ClassManager
                .forName("android.app.ActivityThread");
        installContentProviders = MethodManager.method(activityThreadClass, "installContentProviders", Context.class, List.class);
        h = ClassManager.forName("android.app.ActivityThread$H");
        mCallback = FieldManager.field(Handler.class, "mCallback");
        try {
            LAUNCH_ACTIVITY = (int) FieldManager.getField(null,
                    FieldManager.field(h, "LAUNCH_ACTIVITY"));
            CREATE_SERVICE = (int) FieldManager.getField(null,
                    FieldManager.field(h, "CREATE_SERVICE"));
            LOW_MEMORY = (int) FieldManager.getField(null,
                    FieldManager.field(h, "LOW_MEMORY"));
            TRIM_MEMORY = (int) FieldManager.getField(null,
                    FieldManager.field(h, "TRIM_MEMORY"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        activityThread = FieldManager.getField(null, FieldManager.field(
                activityThreadClass, "sCurrentActivityThread"));
        mH = FieldManager.getField(activityThread,
                FieldManager.field(activityThreadClass, "mH"));
        Class cls = ClassManager.forName("android.app.ActivityThread$ActivityClientRecord");
        activityIntent = FieldManager.field(cls, "intent");
        activityInfoField = FieldManager.field(cls, "activityInfo");
    }

    public ClassActivityThread(ClassLoader classLoader, Hook hook) {
        mClassLoader = classLoader;
        mHook = hook;
        hookActivityManagerServer();
        FieldManager.setField(mH, mCallback, this);
        hookInstrumentation();
    }

    public void installContentProviders(Context context, List<ProviderInfo> providerInfos) {
        LogUtil.i(TAG, "启动provider");
        MethodManager.invoke(installContentProviders, activityThread, context, providerInfos);
    }

    //兼容性问题废弃
    private void hookActivityManagerServer() {
        // TODO Auto-generated method stub
        Object singleton = null;
        Class singletonClass = ClassManager.forName("android.util.Singleton");
        if (Build.VERSION.SDK_INT < 26) {
            singleton = FieldManager.getField(null,
                    FieldManager.field(ClassManager
                            .forName("android.app.ActivityManagerNative"), "gDefault"));
        } else {
            singleton = FieldManager.getField(null,
                    FieldManager.field(ActivityManager.class,
                            "IActivityManagerSingleton"));
        }
        Field mInstance = FieldManager.field(singletonClass, "mInstance");
        Object ams = FieldManager.getField(singleton, mInstance);
        managerServer = new HookActivityManagerServer(ams, mHook);
        ams = Proxy.newProxyInstance(mClassLoader,
                ams.getClass().getInterfaces(),
                managerServer);
        FieldManager.setField(singleton, mInstance, ams);
    }

    private void hookInstrumentation() {
        // TODO Auto-generated method stub
        Method currentActivityThread = MethodManager.method(
                activityThreadClass, "currentActivityThread");
        // 获取主线程对象
        Object activityThreadObject = MethodManager.invoke(
                currentActivityThread, null);
        // 获取Instrumentation字段
        Field mInstrumentation = FieldManager.field(activityThreadClass,
                "mInstrumentation");
        Instrumentation instrumentation = (Instrumentation) FieldManager
                .getField(activityThreadObject, mInstrumentation);
        IInstrumentation iInstrumentation = (IInstrumentation) Proxy
                .newProxyInstance(mClassLoader,
                        CustomInstrumentation.class.getInterfaces(),
                        new HookInstrumentation(instrumentation, mHook));
        CustomInstrumentation customInstrumentation = new CustomInstrumentation(
                iInstrumentation);
        // 替换掉原来的,就是把系统的instrumentation替换为自己的Instrumentation对象
        FieldManager.setField(activityThreadObject, mInstrumentation,
                customInstrumentation);
        Log.i(TAG, "Hook Instrumentation成功");
    }

    @Override
    public boolean handleMessage(Message msg) {
        // TODO Auto-generated method stub
        Log.e(TAG, "handleMessage");
        if (msg.what == LAUNCH_ACTIVITY) {
            launchActivity(msg);
        } else if (msg.what == CREATE_SERVICE) {
            launchServer(msg);
        } else if (msg.what == LOW_MEMORY) {//低内存
            return false;
        } else if (msg.what == TRIM_MEMORY) {//减少内存
            return false;
        } else {
            LogUtil.i(TAG, "其他参数");
        }
        if (handler == null) {
            Object iPackageManager = MethodManager.invoke(MethodManager.method(
                    activityThreadClass, "getPackageManager"), activityThread);
            handler = new HookPackageManager(
                    iPackageManager, mHook);
//            Class<?> iPackageManagerIntercept = ClassManager.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread()
                            .getContextClassLoader(),
                    iPackageManager.getClass().getInterfaces(), handler);
            // 获取 sPackageManager 属性
            FieldManager.setField(activityThread,
                    FieldManager.field(activityThreadClass, "sPackageManager"),
                    proxy);
        }
        return false;
    }

    private void launchActivity(Message msg) {
        Object obj = msg.obj;
        Log.e(TAG, "launchActivity");
        Intent intent = (Intent) FieldManager.getField(obj, activityIntent);
        intent.setExtrasClassLoader(ParasXml.ActivityXml.class.getClassLoader());
        ParasXml.ActivityXml activityXml = intent.getParcelableExtra(Hook.MY_ACTIVITY_XML);
        String className = null;
        if (activityXml != null) {
            className = activityXml.replace;
            if (className == null || className.isEmpty())
                className = activityXml.className;
            Log.e(TAG, "class " + className);
        }
        if (className != null && !className.isEmpty())
            intent.setComponent(new ComponentName(intent.getComponent().getPackageName(), className));
        LogUtil.i(TAG, "替换配置");
        ActivityInfo info = (ActivityInfo) FieldManager.getField(obj, activityInfoField);
        info.flags = intent.getIntExtra(Hook.MY_ACTIVITY_FLAGS, info.flags);
        info.metaData = intent.getBundleExtra(Hook.MY_ACTIVITY_META);
        info.softInputMode = intent.getIntExtra(Hook.MY_ACTIVITY_SOFT_MODE, info.softInputMode);
        info.uiOptions = intent.getIntExtra(Hook.MY_ACTIVITY_OPTIONS, info.uiOptions);
        intent.removeExtra(Hook.MY_ACTIVITY_FLAGS);
        intent.removeExtra(Hook.MY_ACTIVITY_META);
        intent.removeExtra(Hook.MY_ACTIVITY_SOFT_MODE);
        intent.removeExtra(Hook.MY_ACTIVITY_OPTIONS);
    }

    private void launchServer(Message msg) {
        Object obj = msg.obj;
        Log.e(TAG, "launchServer");
        Field field = FieldManager.field(obj.getClass(), "info");
        ServiceInfo info = (ServiceInfo) FieldManager.getField(obj,
                field);
        if (mHook.startServers.size() > 0) {
            info.name = mHook.startServers.get(0);
            mHook.startServers.remove(0);
            FieldManager.setField(obj, field, info);
        }
    }

}
