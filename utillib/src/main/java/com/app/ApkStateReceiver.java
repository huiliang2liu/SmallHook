package com.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.utils.LogUtil;


/**
 * com.app
 * 2018/10/26 11:16
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
class ApkStateReceiver extends BroadcastReceiver {
    private final static String TAG = "ApkStartReceiver";
    private AddListener mAddListener;
    private DataClearListener mDataClearListener;
    private RemoveListener mRemoveListener;
    private ReplacListener mReplacListener;
    private RestartListener mRestartListener;
    private ChangeListener mChangeListener;

    public ApkStateReceiver(ApkState apkState) {
        mAddListener = apkState;
        mDataClearListener = apkState;
        mRemoveListener = apkState;
        mReplacListener = apkState;
        mRestartListener = apkState;
        mChangeListener = apkState;
    }

    //    1\安装
//    android.intent.action.PACKAGE_ADDED
//2覆盖安装
//    android.intent.action.PACKAGE_REPLACED
//3\清理数据
//    android.intent.action.PACKAGE_DATA_CLEARED
//4\重启
//    Action: android.intent.action.PACKAGE_RESTARTED
//5\卸载
//    android.intent.action.PACKAGE_REMOVED
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.w(TAG, "onReceive");
        String packageName = intent.getData().getSchemeSpecificPart();
        String action = intent.getAction();
        if (Intent.ACTION_PACKAGE_ADDED.equals(action))
            mAddListener.add(packageName);
        else if (Intent.ACTION_PACKAGE_REPLACED.equals(action))
            mReplacListener.replac(packageName);
        else if (Intent.ACTION_PACKAGE_DATA_CLEARED.equals(action))
            mDataClearListener.dataClear(packageName);
        else if (Intent.ACTION_PACKAGE_RESTARTED.equals(action))
            mRestartListener.restart(packageName);
        else if (Intent.ACTION_PACKAGE_REMOVED.equals(action))
            mRemoveListener.remove(packageName);
        else if (Intent.ACTION_PACKAGE_CHANGED.equals(action))
            mChangeListener.change(packageName);
    }
}
