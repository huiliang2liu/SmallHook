package com.hook;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import com.reflect.ClassManager;
import com.reflect.FieldManager;
import com.reflect.MethodManager;
import com.utils.ContentManager;
import com.utils.LogUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * com.hook
 * 2018/9/21 17:52
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Application extends android.app.Application implements android.app.Application.ActivityLifecycleCallbacks, Thread.UncaughtExceptionHandler {
    private final static String TAG = "Application";
    private boolean background = false;
    private static Field mBaseField = FieldManager.field(ContextWrapper.class, "mBase");
    private static Method attach = MethodManager.method(android.app.Application.class, "attach", Context.class);
    private static Field mComponentCallbacksField = FieldManager.field(android.app.Application.class, "mComponentCallbacks");
    private static Field mActivityLifecycleCallbacksField = FieldManager.field(android.app.Application.class, "mActivityLifecycleCallbacks");
    private static Field mAssistCallbacksField = FieldManager.field(android.app.Application.class, "mAssistCallbacks");
    private List<android.app.Application> applications = new ArrayList<>();
    private Set<ScreenStateListener> listeners = new HashSet<>();
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public void registerScreenStateListener(ScreenStateListener listener) {
        listeners.add(listener);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void unRegisterScreenStateListener(ScreenStateListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onCreate() {
        // 程序创建的时候执行
        Log.d(TAG, "onCreate");
        ContentManager.getManager();
        IntentFilter screenOffFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        Thread.setDefaultUncaughtExceptionHandler(this);
        registerActivityLifecycleCallbacks(this);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {//熄屏回调
                if (!background) {
                    background = true;
                    for (ScreenStateListener listener : listeners) {
                        listener.background();
                    }
                }
            }
        }, screenOffFilter);
        super.onCreate();
    }


    public android.app.Application addApplication(String app) {
        if (app == null || app.isEmpty())
            return null;
        Class cl = ClassManager.forName(app);
        if (cl == null)
            return null;
        if (!android.app.Application.class.isAssignableFrom(cl))
            return null;
        android.app.Application application = (android.app.Application) ClassManager.newObject(cl);
        if (application == null)
            return null;
        MethodManager.invoke(attach, application, FieldManager.getField(this, mBaseField));
        application.onCreate();
        List<ComponentCallbacks> mComponentCallbacks = (List<ComponentCallbacks>) FieldManager.getField(application, mComponentCallbacksField);
        if (mComponentCallbacks != null && mComponentCallbacks.size() > 0) {
            for (ComponentCallbacks componentCallbacks : mComponentCallbacks) {
                registerComponentCallbacks(componentCallbacks);
            }
        }
        List<ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = (List<ActivityLifecycleCallbacks>) FieldManager.getField(application, mActivityLifecycleCallbacksField);
        if (mActivityLifecycleCallbacks != null && mActivityLifecycleCallbacks.size() > 0) {
            for (ActivityLifecycleCallbacks callbacks : mActivityLifecycleCallbacks) {
                registerActivityLifecycleCallbacks(callbacks);
            }
        }
        if (Build.VERSION.SDK_INT >= 18) {
            List<OnProvideAssistDataListener> mAssistCallbacks = (List<OnProvideAssistDataListener>) FieldManager.getField(application, mAssistCallbacksField);
            if (mAssistCallbacks != null && mAssistCallbacks.size() > 0) {
                for (OnProvideAssistDataListener listener : mAssistCallbacks) {
                    registerOnProvideAssistDataListener(listener);
                }
            }
        }
        applications.add(application);
        return application;
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "onTerminate");
        if (applications.size() > 0) {
            for (android.app.Application application : applications) {
                application.onTerminate();
            }
        }
        ContentManager.getManager().destory();
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Log.d(TAG, "onLowMemory");
        if (applications.size() > 0) {
            for (android.app.Application application : applications) {
                application.onLowMemory();
            }
        }
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        Log.i(TAG, "onTrimMemory");
        if (level == TRIM_MEMORY_UI_HIDDEN && !background) {//运行在后台
            background = true;
            for (ScreenStateListener listener : listeners) {
                listener.background();
            }
        }
        if (applications.size() > 0) {
            for (android.app.Application application : applications) {
                application.onTrimMemory(level);
            }
        }
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged");
        if (applications.size() > 0) {
            for (android.app.Application application : applications) {
                application.onConfigurationChanged(newConfig);
            }
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (background) {
            background = false;
            for (ScreenStateListener listener : listeners) {
                listener.foreground();
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (ContentManager.getManager().getActivity() == null && !background) {
            background = true;
            for (ScreenStateListener listener : listeners) {
                listener.background();
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public final void uncaughtException(Thread t, Throwable e) {
        String throwa = throwable2string(e);
        saveErr(throwa);
        if (defaultUncaughtExceptionHandler != null) {
            defaultUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            System.exit(0);
            Process.killProcess(Process.myPid());
        }
    }

    protected void saveErr(String err) {
        LogUtil.e(TAG, err);
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     * @throws Throwable
     */
    private String throwable2string(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        sb.append("model:");
        sb.append(Build.MODEL);
        sb.append("\nmake:");
        sb.append(Build.MANUFACTURER);
        sb.append("\nbrand:");
        sb.append(Build.BRAND);
        sb.append("\nsystem_version:");
        sb.append(Build.VERSION.RELEASE);
        sb.append("\n");
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        return sb.toString();
    }
}
