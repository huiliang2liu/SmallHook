package com.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.app.AddListener;
import com.app.ApkState;
import com.app.ChangeListener;
import com.app.DataClearListener;
import com.app.RemoveListener;
import com.app.ReplacListener;
import com.app.RestartListener;
import com.hook.Hook;
import com.hook.ParasXml;
import com.http.FileHttp;
import com.http.Http;
import com.http.down.DownFile;
import com.load.ApkResources;
import com.load.Load;
import com.net.NetCallback;
import com.net.NetState;
import com.reflect.FieldManager;
import com.reflect.MethodManager;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 2018/4/13 18:46 instructions：上下文管理 author:liuhuiliang email:825378291@qq.com
 **/

public class ContentManager {
    private static ContentManager mManager;
    private Application mApplication;
    private Activity mActivity;
    private Hook mHook;
    private Load mLoad;
    private NetState mNetState;
    private ApkState mApkState;
    private Http mHttp;
    private FileHttp mFileHttp;
    private DownFile mDownFile;

    /**
     * 2018/4/13 18:57 annotation：获取application对象 author：liuhuiliang email
     * ：825378291@qq.com
     */
    public Application getApplication() {
        return mApplication;
    }

    /**
     * 2018/4/13 18:57 annotation：获取当前 author：liuhuiliang email
     * ：825378291@qq.com
     */
    public Activity getActivity() {
        return mActivity;
    }

    public Http getHttp() {
        return mHttp;
    }

    public FileHttp getFileHttp() {
        return mFileHttp;
    }

    public DownFile getDownFile() {
        return mDownFile;
    }

    private ContentManager() {
        mApplication = field2Application();
        mHttp = new Http.Build().context(mApplication).build();
        mFileHttp = new FileHttp.Build().build();
        mDownFile = new DownFile.Build().context(mApplication).build();
        mHook = new Hook(mApplication);
        mLoad = new Load();
        mNetState = NetState.getNetState(mApplication);
        mApkState = ApkState.getApkState(mApplication);
    }

    public void registerNetCallback(NetCallback callback) {
        mNetState.registerCallback(callback);
    }

    public void unRegisterNetCallback(NetCallback callback) {
        mNetState.unRegisterCallback(callback);
    }

    public void registerAddListener(AddListener listener) {
        mApkState.registerAddListener(listener);
    }

    public void unRegisterAddListener(AddListener listener) {
        mApkState.unRegisterAddListener(listener);
    }

    public void registerDataClearListener(DataClearListener listener) {
        mApkState.registerDataClearListener(listener);
    }

    public void unRegisterDataClearListener(DataClearListener listener) {
        mApkState.unRegisterDataClearListener(listener);
    }

    public void registerReplacListener(ReplacListener listener) {
        mApkState.registerReplacListener(listener);
    }

    public void unRegisterReplacListener(ReplacListener listener) {
        mApkState.unRegisterReplacListener(listener);
    }

    public void registerRestartListener(RestartListener listener) {
        mApkState.registerRestartListener(listener);
    }

    public void unRegisterRestartListener(RestartListener listener) {
        mApkState.unRegisterRestartListener(listener);
    }

    public void registerRemoveListener(RemoveListener listener) {
        mApkState.registerRemoveListener(listener);
    }

    public void unRegisterRemoveListener(RemoveListener listener) {
        mApkState.unRegisterRemoveListener(listener);
    }

    public void registerChangeListener(ChangeListener listener) {
        mApkState.registerChangeListener(listener);
    }

    public void unRegisterChangeListener(ChangeListener listener) {
        mApkState.unRegisterChangeListener(listener);
    }

    public void setHookServer(String hookServer) {
        mHook.setHookServer(hookServer);
    }

    public void setHookActivity(String hookActivity) {
        mHook.setHookActivity(hookActivity);
    }


    public void loadResourec(Context context, String... apkPaths) {
        mLoad.loadResourec(context, apkPaths);
    }

    public void startActivity(Intent intent) {
        mHook.startActivity(mActivity == null ? mApplication : mActivity, intent);
    }

    public void startActivity(Context context, Intent intent) {
        mHook.startActivity(context, intent);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        mHook.startActivityForResult(mActivity, intent, requestCode);
    }

    public void startServiceForApplication(Intent intent) {
        startService(mApplication, intent);
    }

    public void startServiceForActivity(Intent intent) {
        startService(mActivity, intent);
    }

    public void startService(Context context, Intent intent) {
        mHook.startService(context, intent);
    }

    public boolean bindServiceForApplication(Intent intent, ServiceConnection conn, int flags) {
        return bindService(mApplication, intent, conn, flags);
    }

    public boolean bindServiceForActivity(Intent intent, ServiceConnection conn, int flags) {
        return bindService(mActivity, intent, conn, flags);
    }

    public boolean bindService(Context context, Intent intent, ServiceConnection conn, int flags) {
        return mHook.bindService(context, intent, conn, flags);
    }

    public void loadSo(Context context, String... soPaths) {
        mLoad.loadSo(context, soPaths);
    }

    public void loadDex(Context context, String[] dexPaths) {
        mLoad.loadDex(context, dexPaths);
    }

    public void setBase(Context context, String name) {
        mHook.setBase(context, name);
    }

    public void setBase(Context context) {
        mHook.setBase(context);
    }

    public ParasXml.ActivityXml className2ActivityXml(String className) {
        return mHook.pit(className);
    }

    public Intent name2intent(String packageName, String classNam) {
        ComponentName componentName = new ComponentName(packageName, classNam);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        return intent;
    }


    public boolean isXml(Context context, Intent intent) {
        ComponentName componentName = intent.getComponent();
        return mHook.isXml(componentName.getClassName());
    }

    public void paras(InputStream inputStream) {
        mHook.paras(inputStream);
    }

    public ApkResources name2resources(String packageName) {
        return mLoad.name2resources(packageName);
    }

    public void load(File file, Context context) {
        mLoad.load(file, context);
    }

    public void load(File file) {
        mLoad.load(file, getApplication());
    }

    public void loadSo(String... so) {
        mLoad.loadSo(so);
    }

    public void load(File[] files, Context context) {
        mLoad.load(files, context);
    }

    /**
     * 2018/4/13 18:55 annotation：上下文管理 author：liuhuiliang email
     * ：825378291@qq.com
     */
    public static ContentManager getManager() {
        if (mManager == null) {
            synchronized (ContentManager.class) {
                if (mManager == null)
                    mManager = new ContentManager();
            }
        }
        return mManager;
    }

    /**
     * 2018/4/16 10:19 annotation：通过mInitialApplication获取application
     * author：liuhuiliang email ：825378291@qq.com
     */
    private Application field2Application() {
        Application application = null;
        try {
            Class actvivtyThreadClass = Class
                    .forName("android.app.ActivityThread");
            Field mInitialApplicationField = FieldManager.field(
                    actvivtyThreadClass, "mInitialApplication");
            Method currentActivityThreadMethod = MethodManager.method(
                    actvivtyThreadClass, "currentActivityThread");
            Object activityThread = currentActivityThreadMethod.invoke(null);
            application = (Application) FieldManager.getField(activityThread,
                    mInitialApplicationField);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return application;
    }

    /**
     * 2018/4/16 10:19 annotation：通过getApplication放过获取application
     * author：liuhuiliang email ：825378291@qq.com
     */
    private Application method2Application() {
        Application application = null;
        try {
            Class activityThreadClass = Class
                    .forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = MethodManager.method(
                    activityThreadClass, "currentActivityThread");
            Object activityThread = currentActivityThreadMethod.invoke(null);
            Method getApplicationMethod = MethodManager.method(
                    activityThreadClass, "getApplication");
            application = (Application) getApplicationMethod
                    .invoke(activityThread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return application;
    }

    public void destory() {
        mNetState.destroy();
        mApkState.destory();
        mManager = null;
    }
}
