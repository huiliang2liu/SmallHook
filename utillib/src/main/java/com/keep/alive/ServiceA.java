package com.keep.alive;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;

import com.utils.LogUtil;


/**
 * com.keep.alive
 * 2018/11/16 16:54
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class ServiceA extends Service implements Handler.Callback {
    private final static String TAG = "ServiceA";
    private IKeepAlive mBinderB;
    private String serviceName;
    private long period;
    private Handler handler = new Handler(Looper.getMainLooper(), this);
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.e(TAG, "绑定断开，重新连接B服务");
            bindAliveB();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderB = IKeepAlive.Stub.asInterface(service);
            if (mBinderB != null) {
                try {
                    LogUtil.e(TAG, "成功绑定B服务");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private IKeepAlive.Stub mBinderStub = new IKeepAlive.Stub() {
        @Override
        public String getName() {
            return "serviceA";
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
        LogUtil.e(TAG, "启动A服务" + Process.myPid());
        bindAliveB();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        long time = intent.getLongExtra(KeepAliveManager.SERVICE_TIME, System.currentTimeMillis());
        if (!handler.hasMessages(0)) {
            SharedPreferences preferences = getSharedPreferences("keep_alive", Context.MODE_PRIVATE);
            serviceName = preferences.getString(KeepAliveManager.SERVICE_NAME, "");
            if (serviceName == null || serviceName.isEmpty()) {
                LogUtil.e(TAG, "service name is empty");
            } else {
                long showTime = preferences.getLong(KeepAliveManager.SHOW_TIME, 0);
                period = preferences.getLong(KeepAliveManager.SERVICE_TIME, 6000);
                if (showTime <= 0) {
                    handler.sendEmptyMessageDelayed(0, period);
                } else {
                    showTime = System.currentTimeMillis() - showTime - period;
                    if (showTime <= 0)
                        handler.sendEmptyMessage(0);
                    else
                        handler.sendEmptyMessageDelayed(0, showTime);
                }
            }

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.removeMessages(0);
        super.onDestroy();
    }

    private void bindAliveB() {
        Intent serverIntent = new Intent(getApplicationContext(), ServiceB.class);
        bindService(serverIntent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean handleMessage(Message msg) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(getPackageName(), serviceName);
        intent.setComponent(componentName);
        LogUtil.e(TAG,"启动服务");
        try {
            startService(intent);
        } catch (Exception e) {
            LogUtil.e(TAG, "service error service is " + serviceName);
//            e.printStackTrace();
        }
        handler.sendEmptyMessageDelayed(0, period);
        return true;
    }
}
