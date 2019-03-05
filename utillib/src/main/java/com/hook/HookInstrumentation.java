package com.hook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.reflect.FieldManager;
import com.reflect.MethodManager;
import com.resource.ResourceUtil;
import com.utils.ContentManager;
import com.utils.LogUtil;
import com.utils.StringUtil;
import com.view.LayoutInflater;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 2018/4/16 11:52 instructions： author:liuhuiliang email:825378291@qq.com
 **/

class HookInstrumentation implements InvocationHandler {
    private final static String TAG = "HookInstrumentation";
    private Instrumentation mInstrumentation;
    private final static Field mActivity;
    private Hook mHook;

    static {
        mActivity = FieldManager.field(ContentManager.class, "mActivity");
    }

    public HookInstrumentation(Instrumentation instrumentation, Hook hook) {
        mInstrumentation = instrumentation;
        mHook = hook;
    }

    @SuppressLint("NewApi")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Class<?>[] types = method.getParameterTypes();
        String methodName = method.getName();
        LogUtil.i(TAG, methodName);
        Method mMethod = MethodManager.method(Instrumentation.class,
                methodName, types);
        Object object = null;
        if (methodName.equals("newActivity")) {
            LogUtil.i(TAG, "newActivity");
            beforeNewActivity(args);
            object = mMethod.invoke(mInstrumentation,
                    args);
            afterNewActivity((Activity) object);
        } else if (methodName.equals("callActivityOnCreate")) {
            final Activity mActivity = (Activity) args[0];
            LogUtil.i(TAG, "callActivityOnCreate");
            Bundle bundle = (Bundle) args[1];
            beforeOnCreate(mActivity, bundle);
            object = mMethod.invoke(mInstrumentation, args);
            afterOnCreate(mActivity, bundle);
        } else if (methodName.equals("callActivityOnNewIntent")) {
            Activity mActivity = (Activity) args[0];
            if (Intent.class.isAssignableFrom(types[1])) {
                Bundle bundle = (Bundle) args[1];
                beforeOnNewIntent(mActivity, bundle);
                object = mMethod.invoke(mInstrumentation, args);
                afterOnNewIntent(mActivity, bundle);
            } else
                object = mMethod.invoke(mInstrumentation, args);
        } else if (methodName.equals("callActivityOnRestart")) {
            beforeOnRestart((Activity) args[0]);
            object = mMethod.invoke(mInstrumentation, args);
            afterOnRestart((Activity) args[0]);
        } else if (methodName.equals("callActivityOnStart")) {
            beforeOnStart((Activity) args[0]);
            object = mMethod.invoke(mInstrumentation, args);
            afterOnStart((Activity) args[0]);
        } else if (methodName.equals("callActivityOnRestoreInstanceState")) {
            Activity activity = (Activity) args[0];
            Bundle bundle = (Bundle) args[1];
            beforeOnRestoreInstanceState(activity, bundle);
            object = mMethod.invoke(mInstrumentation, args);
            afterOnRestoreInstanceState(activity, bundle);
        } else if (methodName.equals("callActivityOnResume")) {
            beforeOnResume((Activity) args[0]);
            object = mMethod.invoke(mInstrumentation, args);
            afterOnResume((Activity) args[0]);
        } else if (methodName.equals("callActivityOnPause")) {
            beforeOnPause((Activity) args[0]);
            object = mMethod.invoke(mInstrumentation, args);
            afterOnPause((Activity) args[0]);
        } else if (methodName.equals("callActivityOnSaveInstanceState")) {
            Activity activity = (Activity) args[0];
            Bundle bundle = (Bundle) args[1];
            beforeOnSaveInstanceState(activity, bundle);
            object = mMethod.invoke(mInstrumentation, args);
            afterOnSaveInstanceState(activity, bundle);
        } else if (methodName.equals("callActivityOnStop")) {
            beforeOnStop((Activity) args[0]);
            object = mMethod.invoke(mInstrumentation, args);
            afterOnStop((Activity) args[0]);
        } else if (methodName.equals("callActivityOnDestroy")) {
            beforeOnDestroy((Activity) args[0]);
            object = mMethod.invoke(mInstrumentation, args);
            afterOnDestroy((Activity) args[0]);
        } else {
            object = mMethod.invoke(mInstrumentation, args);
        }
        return object;
    }

    /**
     * onDestroy之前执行
     *
     * @param activity
     */
    void beforeOnDestroy(Activity activity) {
        if (ContentManager.getManager().getActivity() == activity)
            FieldManager.setField(ContentManager.getManager(), mActivity, null);
    }

    /**
     * onDestroy之后执行
     *
     * @param activity
     */
    void afterOnDestroy(Activity activity) {

    }

    /**
     * onStop之前执行
     *
     * @param activity
     */
    void beforeOnStop(Activity activity) {

    }

    /**
     * onStop之后执行
     *
     * @param activity
     */
    void afterOnStop(Activity activity) {

    }

    /**
     * onSaveInstanceState之前执行
     *
     * @param activity
     */
    void beforeOnSaveInstanceState(Activity activity, Bundle bundle) {

    }

    /**
     * onSaveInstanceState之后执行
     *
     * @param activity
     */
    void afterOnSaveInstanceState(Activity activity, Bundle bundle) {

    }

    /**
     * onPause之前执行
     *
     * @param activity
     */
    void beforeOnPause(Activity activity) {

    }

    /**
     * onPause之后执行
     *
     * @param activity
     */
    void afterOnPause(Activity activity) {

    }

    /**
     * onResume之前执行
     *
     * @param activity
     */
    void beforeOnResume(Activity activity) {
        Log.i(TAG, "beforeOnResume");
        FieldManager.setField(ContentManager.getManager(), mActivity, activity);
        ParasXml.ActivityXml activityXml = ContentManager.getManager().className2ActivityXml(activity.getClass().getName());
        if (activityXml != null) {
            String status = activityXml.statusBarColor;
            if (status != null && !status.isEmpty()) {
                activity.getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                int resourceId = activity.getResources().getIdentifier(
                        "status_bar_height", "dimen", "android");
                int statusBarHeight = activity.getResources()
                        .getDimensionPixelSize(resourceId);
                // 绘制一个和状态栏一样高的矩形
                View statusView = new View(activity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
                statusView.setLayoutParams(params);
                statusView.setBackgroundResource(StringUtil.color(status));
                // 添加 statusView 到布局中
                ViewGroup decorView = (ViewGroup) activity.getWindow()
                        .getDecorView();
                decorView.addView(statusView);
                ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
                rootView.setFitsSystemWindows(true);
                rootView.setClipToPadding(true);
            }
        }
//        status bar
    }

    /**
     * onResume之后执行
     *
     * @param activity
     */
    void afterOnResume(Activity activity) {

    }

    /**
     * onRestoreInstanceState之前执行
     *
     * @param activity
     */
    void beforeOnRestoreInstanceState(Activity activity, Bundle bundle) {

    }

    /**
     * onRestoreInstanceState之后执行
     *
     * @param activity
     */
    void afterOnRestoreInstanceState(Activity activity, Bundle bundle) {

    }

    /**
     * onRestart之前执行
     *
     * @param activity
     */
    void beforeOnRestart(Activity activity) {

    }

    /**
     * onRestart之后执行
     *
     * @param activity
     */
    void afterOnRestart(Activity activity) {

    }

    /**
     * onStart之前执行
     *
     * @param activity
     */
    void beforeOnStart(Activity activity) {

    }

    /**
     * onStart之后执行
     *
     * @param activity
     */
    void afterOnStart(Activity activity) {

    }

    /**
     * onCreate之后执行
     *
     * @param activity
     * @param bundle
     */
    void afterOnCreate(Activity activity, Bundle bundle) {

    }

    /**
     * onCreate之前执行
     *
     * @param activity
     * @param bundle
     */
    void beforeOnCreate(Activity activity, Bundle bundle) {
        FieldManager.setField(ContentManager.getManager(), mActivity, activity);
        ContentManager.getManager().setBase(activity);
        ParasXml.ActivityXml activityXml = ContentManager.getManager().className2ActivityXml(activity.getClass().getName());
        if (activityXml != null) {
            String layout = activityXml.layout;
            if (layout != null && !layout.isEmpty())
                activity.setContentView(LayoutInflater.from(activity).inflate(ResourceUtil.layout(activity.getResources(), layout, activityXml.packageName), null));
            bundle.remove(Hook.MY_ACTIVITY_XML);
//                activity.setTheme(ResourceUtil.style(activity.getResources(),"theme", activityXml.packageName));
        }

    }

    /**
     * onNewIntent之后执行
     *
     * @param activity
     * @param bundle
     */
    void afterOnNewIntent(Activity activity, Bundle bundle) {

    }

    /**
     * onNewIntent之前执行
     *
     * @param activity
     * @param bundle
     */
    void beforeOnNewIntent(Activity activity, Bundle bundle) {
        FieldManager.setField(ContentManager.getManager(), mActivity, activity);
        ContentManager.getManager().setBase(activity);

    }

    /**
     * newActivity之后执行
     *
     * @param mActivity
     */
    void afterNewActivity(Activity mActivity) {
    }

    /**
     * newActivity之前执行
     *
     * @param args
     */
    void beforeNewActivity(Object[] args) {
        if (args == null || args.length <= 0)
            return;
        if (args[0] instanceof Class) {
            Log.e(TAG, ((Class) args[0]).getName());
        }

    }
}
