package com.hook;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.utils.LogUtil;


/**
 * com.hook
 * 2018/12/29 15:13
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class AliveService extends Service {
    private static final String TAG="AliveService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(TAG,"服务启动");
        return super.onStartCommand(intent, flags, startId);
    }
}
