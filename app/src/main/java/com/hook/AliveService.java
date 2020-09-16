package com.hook;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.utils.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/**
 * com.hook
 * 2018/12/29 15:13
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class AliveService extends Service {
    private static final String TAG = "AliveService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(TAG, "服务启动");
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        File[] files = new File("/proc").listFiles();
//        for (File file : files) {
//            LogUtil.e(TAG,file.getName());
//            if (file.isDirectory()) {
//                int pid;
//                try {
//                    pid = Integer.parseInt(file.getName());
//                } catch (NumberFormatException e) {
//                    continue;
//                }
//                Log.e(TAG,"pid:"+pid);
//            }
//        }
        LogUtil.e(TAG, exec("adb shell ls /proc"));
        return super.onStartCommand(intent, flags, startId);
    }

    private String exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            process.waitFor();
            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }


}
