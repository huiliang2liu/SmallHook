package com.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * com.app
 * 2018/10/26 11:14
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class ApkState implements AddListener, DataClearListener, RemoveListener, ReplacListener,RestartListener, ChangeListener {
    private static ApkState start;
    private List<AddListener> addListeners;
    private List<DataClearListener> dataClearListeners;
    private List<ReplacListener> replacListeners;
    private List<RestartListener> restartListeners;
    private List<RemoveListener> removeListeners;
    private List<ChangeListener> changeListeners;
    private BroadcastReceiver receiver;
    private Context context;

    {
        addListeners = new ArrayList<>();
        dataClearListeners = new ArrayList<>();
        replacListeners = new ArrayList<>();
        restartListeners = new ArrayList<>();
        removeListeners = new ArrayList<>();
        changeListeners = new ArrayList<>();
    }

    private ApkState(Context con) {
        context = con.getApplicationContext();
        receiver = new ApkStateReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_DATA_CLEARED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_RESTARTED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addDataScheme("package");
        intentFilter.setPriority(1000);
        context.registerReceiver(receiver, intentFilter);
    }

    public static ApkState getApkState(Context context) {
        if (start == null)
            synchronized (ApkState.class) {
                if (start == null)
                    start = new ApkState(context);
            }
        return start;
    }

    public void registerAddListener(AddListener listener) {
        addListeners.add(listener);
    }

    public void unRegisterAddListener(AddListener listener) {
        addListeners.remove(listener);
    }

    public void registerDataClearListener(DataClearListener listener) {
        dataClearListeners.add(listener);
    }

    public void unRegisterDataClearListener(DataClearListener listener) {
        dataClearListeners.remove(listener);
    }

    public void registerReplacListener(ReplacListener listener) {
        replacListeners.add(listener);
    }

    public void unRegisterReplacListener(ReplacListener listener) {
        replacListeners.remove(listener);
    }

    public void registerRestartListener(RestartListener listener) {
        restartListeners.add(listener);
    }

    public void unRegisterRestartListener(RestartListener listener) {
        restartListeners.remove(listener);
    }

    public void registerRemoveListener(RemoveListener listener) {
        removeListeners.add(listener);
    }

    public void unRegisterRemoveListener(RemoveListener listener) {
        removeListeners.remove(listener);
    }

    public void registerChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    public void unRegisterChangeListener(ChangeListener listener) {
        changeListeners.remove(listener);
    }

    @Override
    public void add(String packageName) {
        if (addListeners.isEmpty())
            return;
        for (AddListener listener : addListeners)
            listener.add(packageName);
    }

    @Override
    public void dataClear(String packageName) {
        if (dataClearListeners.isEmpty())
            return;
        for (DataClearListener listener : dataClearListeners)
            listener.dataClear(packageName);
    }

    @Override
    public void remove(String packageName) {
        if (removeListeners.isEmpty())
            return;
        for (RemoveListener listener : removeListeners)
            listener.remove(packageName);
    }

    @Override
    public void replac(String packageName) {
        if (replacListeners.isEmpty())
            return;
        for (ReplacListener listener : replacListeners)
            listener.replac(packageName);
    }

    @Override
    public void restart(String packageName) {
        if (restartListeners.isEmpty())
            return;
        for (RestartListener listener : restartListeners)
            listener.restart(packageName);
    }

    @Override
    public void change(String packageName) {
        if (changeListeners.isEmpty())
            return;
        for (ChangeListener listener : changeListeners)
            listener.change(packageName);
    }

    public void destory() {
        context.unregisterReceiver(receiver);
        context = null;
        receiver = null;
        addListeners.clear();
        dataClearListeners.clear();
        removeListeners.clear();
        replacListeners.clear();
        restartListeners.clear();
        start = null;
    }







}
