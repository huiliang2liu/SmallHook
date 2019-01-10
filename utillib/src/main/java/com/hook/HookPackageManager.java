package com.hook;

import android.content.ComponentName;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * com.hook
 * 2018/9/20 18:50
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
class HookPackageManager implements InvocationHandler {
    private Object mActivityManagerObject;
    private Hook mHook;

    HookPackageManager(Object mActivityManagerObject, Hook hook) {
        this.mActivityManagerObject = mActivityManagerObject;
        mHook = hook;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        String method_name = method.getName();
        if ("getActivityInfo".equals(method_name)) {
            ComponentName componentName = new ComponentName(
                    mHook.hookPackage, mHook.hookActivity);
            args[0] = componentName;
        } else if ("getServiceInfo".equals(method_name)) {
            ComponentName componentName = new ComponentName(
                    mHook.hookPackage, mHook.hookServer);
            args[0] = componentName;
        }
        return method.invoke(mActivityManagerObject, args);
    }
}
