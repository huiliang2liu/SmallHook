package com.load;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.reflect.MethodManager;

import java.lang.reflect.Method;

/**
 * com.load
 * 2018/9/19 11:36
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class ApkResources {
    private static Method addAssetPath;
    public Resources.Theme mTheme;
    public PackageInfo mPackageInfo;
    public String mPackage = "";
    public AssetManager mAssetManager;
    public Resources mResources;

    static {
            addAssetPath = MethodManager.method(AssetManager.class,"addAssetPath",String.class);
    }

    public ApkResources(String apkPath, Context context) {
        try {
            mPackageInfo = context
                    .getPackageManager()
                    .getPackageArchiveInfo(
                            apkPath,
                            PackageManager.GET_ACTIVITIES
                                    | PackageManager.GET_SERVICES
                                    | PackageManager.GET_META_DATA
                                    | PackageManager.GET_PERMISSIONS
                                    | PackageManager.GET_SIGNATURES);
            mPackage = mPackageInfo.packageName;
            mAssetManager = AssetManager.class.newInstance();
            addAssetPath.invoke(mAssetManager,
                    apkPath);
            Resources superRes = context.getResources();
            mResources = new Resources(mAssetManager,
                    superRes.getDisplayMetrics(),
                    superRes.getConfiguration());
            mTheme = mResources.newTheme();
            // Finals适配三星以及部分加载XML出现异常BUG
            mTheme.applyStyle(mPackageInfo.applicationInfo.theme, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
