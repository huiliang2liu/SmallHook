package com.net;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.utils.LogUtil;

import java.util.List;

/**
 * com.net
 * 2018/10/17 11:56
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class WifiUtil {
    //        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
//        <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
//        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    public static String[] wifiArray(Context context) {
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (Build.VERSION.SDK_INT >= 23 && (context.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED || context.checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)) {
                LogUtil.e("获取wifi列表失败");
                return new String[0];
            }
            LogUtil.e("" + (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED));
            wm.startScan();
            // 得到扫描结果
            List<ScanResult> mWifiList = wm.getScanResults();
            if (mWifiList == null || mWifiList.size() <= 0) {
                LogUtil.e("获取wifi列表长度为0");
                return new String[0];
            }
            String[] wifiArray = new String[mWifiList.size()];
            for (int i = 0; i < mWifiList.size(); i++) {
                wifiArray[i] = mWifiList.get(i).SSID;
            }
            return wifiArray;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public static String[] userWifiArray(Context context) {
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (Build.VERSION.SDK_INT >= 23 && (context.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED || context.checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)) {
                LogUtil.e("获取wifi列表失败");
                return new String[0];
            }
            LogUtil.e("" + (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED));
            wm.startScan();
            // 得到扫描结果
            List<WifiConfiguration> mWifiList = wm.getConfiguredNetworks();
            if (mWifiList == null || mWifiList.size() <= 0) {
                LogUtil.e("获取wifi列表长度为0");
                return new String[0];
            }
            String[] wifiArray = new String[mWifiList.size()];
            for (int i = 0; i < mWifiList.size(); i++) {
                wifiArray[i] = mWifiList.get(i).SSID;
            }
            return wifiArray;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    // 打开WIFI
    public static void openWifi(Context context) {
        setWifi(context, true);
    }

    // 关闭WIFI
    public static void closeWifi(Context context) {
        setWifi(context, false);
    }

    public static void setWifi(Context context, boolean open) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wm == null)
            return;
        if (Build.VERSION.SDK_INT >= 23 && context.checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
            return;
        if (wm.isWifiEnabled()) {
            wm.setWifiEnabled(false);
        }
    }

    // 检查当前WIFI状态
    public static boolean wifiState(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wm == null)
            return false;
        if (Build.VERSION.SDK_INT >= 23 && context.checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
            return false;
        return wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
    }
}
