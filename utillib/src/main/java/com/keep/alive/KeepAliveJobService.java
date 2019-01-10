package com.keep.alive;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.utils.LogUtil;


/**
 * com.keep.alive
 * 2018/12/17 17:25
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class KeepAliveJobService extends JobService {
    private final static String TAG = "KeepAliveJobService";


    @Override
    public boolean onStartJob(JobParameters params) {
        LogUtil.e(TAG, "onStartJob");
        SharedPreferences preferences=getSharedPreferences("keep_alive", Context.MODE_PRIVATE);
        String service_name=preferences.getString(KeepAliveManager.SERVICE_NAME,"");
        if(service_name==null||service_name.isEmpty()){
            LogUtil.e(TAG,"service name is empty");
        }else{
            Intent intent=new Intent();
            ComponentName componentName=new ComponentName(getPackageName(),service_name);
            intent.setComponent(componentName);
            try {
                startService(intent);
            } catch (Exception e) {
                LogUtil.e(TAG,"service error，service is "+service_name);
            }
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
