package com.keep.alive;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;

import com.utils.LogUtil;


/**
 * com.keep.alive
 * 2018/11/16 16:54
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class ServiceB extends Service {
    private final static String TAG = "ServiceB";
    private IKeepAlive mBinderA;
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.e(TAG, "绑定断开，重新绑定A服务");
            bindA();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderA = IKeepAlive.Stub.asInterface(service);
            if (mBinderA != null) {
                try {
                    LogUtil.e(TAG, "成功绑定A服务");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private IKeepAlive.Stub mBinderStub = new IKeepAlive.Stub() {
        @Override
        public String getName() {
            return "serviceB";
        }

        @Override
        public void setTime(long time) {
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderStub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //提升Service的优先级
        Notification notification = new Notification();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        startForeground(1, notification);
        LogUtil.e(TAG, "启动B服务" + Process.myPid());
        bindA();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void bindA() {
        Intent clientIntent = new Intent(getApplicationContext(), ServiceA.class);
        bindService(clientIntent, conn, Context.BIND_AUTO_CREATE);
    }
}
