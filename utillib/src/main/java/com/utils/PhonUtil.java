package com.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.webkit.WebView;

import com.reflect.MethodManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * com.util.utils
 * 2018/9/26 18:56
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class PhonUtil {
    public static boolean mobile = true;//是否为移动设备
    public static boolean phon = true;//是否为电话
    public static boolean call = false;//是否可以打电话
    public static String eth;//有线mac
    public static String wlan;//无限mac
    public static String ua;//user agent
    public static String make = "";//生产厂商
    public static String brand = "";//品牌
    public static String model = "";//型号
    public static String mac = "";//mac地址
    public static String imei = "";//手机imei
    public static String imsi = "";//手机imsi
    public static String plmn = "";//运营商
    public static String androidId = "";//android Id
    public static int screenWidth = 0;//屏幕宽度上的密度
    public static int screenHeight = 0;//屏幕高度上的密度
    public static int dpi = 0;//密度dpi
    public static float density;//像素密度（dpi转换为像素的转换率）
    public static float scaledDensity;
    public static String local = "";//系统语言
    public static boolean toch = true;//是否可触摸
    public static volatile boolean init = false;//是否初始化了


    static {
        eth = getEth();
        wlan = getWlan();
        make = Build.MANUFACTURER;
        brand = Build.BRAND;
        model = Build.MODEL;
        mobile = eth == null || eth.isEmpty();
        mac = mobile ? wlan : eth;
        local = Locale.getDefault().toString();
    }

    public static void init(Activity activity) {
        if (activity == null)
            return;
        ua = ua(activity);
        androidId = Settings.Secure.getString(
                activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        DisplayMetrics displayMetrics = activity.getResources()
                .getDisplayMetrics();
        dpi = displayMetrics.densityDpi;
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        density = displayMetrics.density;
        scaledDensity = displayMetrics.scaledDensity;
        toch = hasTouchScreen(activity);
        phon(activity);
        init = true;
    }

    public static String ua(Context context){
        try {
            WebView webView = new WebView(context);
            return webView.getSettings().getUserAgentString();
        } catch (Exception e) {
            return System.getProperty("http.agent");
        }
    }

    private static void phon(Activity activity) {
        if (activity == null)
            return;
        TelephonyManager telephony = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony == null)
            return;
        int type = telephony.getPhoneType();
        call = type != TelephonyManager.PHONE_TYPE_NONE;
        phon = call & mobile;
        plmn = telephony.getSimOperator();
        if (Build.VERSION.SDK_INT < 23) {
            imei = telephony.getDeviceId();
            imsi = telephony.getSubscriberId();
        } else if (activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 26) {
                Method getImei = MethodManager.method(telephony.getClass(), "getImei", int.class);
                imei = (String) MethodManager.invoke(getImei, telephony, 0);
                if (NullUtil.isEmpty(imei))
                    imei = telephony.getDeviceId();
            } else {
                imei = telephony.getDeviceId();
            }
            imsi = telephony.getSubscriberId();
        }
    }

    /**
     * 2018/9/3 18:28
     * annotation：是否可以触摸
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    private static boolean hasTouchScreen(Context context) {
        if (context == null)
            return false;
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
                || context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
    }

    /**
     * 获取有线mac地址
     *
     * @return
     */
    private static String getEth() {
        try {
            String mac = readLine("/sys/class/net/eth0/address");
            return mac;
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 获取无线mac地址
     *
     * @return
     */
    private static String getWlan() {
        try {
            String mac = readLine("/sys/class/net/wlan0/address");
            return mac;
        } catch (IOException e) {
            return "";
        }
    }

    private static String readLine(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename),
                256);
        try {
            return reader.readLine();
        } finally {
            reader.close();
        }
    }

    /**
     * 是否为移动设备
     *
     * @return
     */
    public static boolean isMobile() {
        String mac = getEth();
        if (mac == null || mac.isEmpty())
            return true;
        return false;
    }

    /**
     * 是否为电话
     *
     * @param context
     * @return
     */
    public static boolean isPhon(Context context) {
        return isMobile() && isCall(context);
    }

    /**
     * 是否为pad
     *
     * @param context
     * @return
     */
    public static boolean isPad(Context context) {
        return isMobile() && !isCall(context);
    }

    /**
     * 是否可以打电话
     *
     * @param mContext
     * @return
     */
    public static boolean isCall(Context mContext) {
        if (mContext == null)
            return false;
        TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony == null)
            return false;
        int type = telephony.getPhoneType();
        if (type == TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否横屏
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        if (density <= 0)
            return (int) dpValue;
        return scale(density, dpValue);
    }

    /**
     * px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        if (density <= 0)
            return (int) pxValue;
        return scale(1 / density, pxValue);
    }

    /**
     * 像素转为sp
     *
     * @param pxValue
     * @return int
     */
    public static int px2sp(float pxValue) {
        if (scaledDensity <= 0)
            return (int) pxValue;
        return scale(1 / scaledDensity, pxValue);
    }

    /**
     * sp转为像素
     *
     * @param spValue
     * @return int
     */
    public static int sp2px(float spValue) {
        if (scaledDensity <= 0)
            return (int) spValue;
        return scale(scaledDensity, spValue);
    }

    public static int scaleWidth(float reference, float value) {
        return scale(reference, screenWidth, value);
    }

    public static int scaleHeigth(float refrence, float value) {
        return scale(refrence, screenHeight, value);
    }

    public static int scale(float reference, float entity, float value) {
        return scale(entity <= 0 ? 1 : entity / reference, value);
    }

    public static int scale(float scale, float value) {
        return BigDecimal.valueOf(scale * value).setScale(0, BigDecimal.ROUND_CEILING).intValue();
    }

    /**
     * 横屏
     *
     * @param activity
     */
    public static void landscape(Activity activity) {
        if (activity == null)
            return;
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return;
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    /**
     * 竖屏
     *
     * @param activity
     */
    public static void portrait(Activity activity) {
        if (activity == null)
            return;
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return;
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
