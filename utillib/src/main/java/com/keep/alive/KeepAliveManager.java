package com.keep.alive;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.preference.Preference;

import com.utils.LogUtil;

/**
 * com.keep.alive
 * 2018/12/18 10:20
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class KeepAliveManager {
    private static final String TAG="KeepAliveManager";
    public static final String SERVICE_NAME = "service_name";
    public static final String SERVICE_TIME = "service_time";
    public static final String SHOW_TIME = "show_time";

    public static void start(Context context, long period, String serviceName) {
        if(context==null){
            LogUtil.e(TAG,"context is null");
            return;
        }
        if(serviceName==null||serviceName.isEmpty()){
            LogUtil.e(TAG,"service name  is empty");
            return;
        }
        if (period < 6000)
            period = 6000;
        SharedPreferences preference = context.getSharedPreferences("keep_alive", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putLong(SERVICE_TIME, period);
        editor.putLong(SHOW_TIME, 0);
        editor.putString(SERVICE_NAME, serviceName);
        editor.commit();
        if (Build.VERSION.SDK_INT >= 21) {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(context.getPackageName().hashCode(), new ComponentName(context.getPackageName(), KeepAliveJobService.class.getName()));
            builder.setPeriodic(period);
            if (Build.VERSION.SDK_INT >= 23) {
                if (context.checkSelfPermission(Manifest.permission.RECEIVE_BOOT_COMPLETED) == PackageManager.PERMISSION_GRANTED)
                    builder.setPersisted(true);
            } else
                builder.setPersisted(true);
            builder.setRequiresDeviceIdle(true);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            jobScheduler.schedule(builder.build());
        } else {
            Intent intent = new Intent(context, ServiceA.class);
            intent.putExtra(SERVICE_NAME, serviceName);
            context.startService(intent);
        }
    }
}
