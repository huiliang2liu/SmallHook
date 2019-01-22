package com.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 2018/4/13 18:45 instructions：清单文件管理 author:liuhuiliang email:825378291@qq.com
 **/

public class MainfiestManager {
	private static MainfiestManager mMainfiestManager;
	private PackageManager packageManager;
	private String packageName;
	private String name;

	public static enum Type {
		SERVICE, RECEIVER, ACTIVITY, APPLICATION
	}

	public static MainfiestManager mainfiest(Context context) {
		if (mMainfiestManager == null) {
			synchronized (MainfiestManager.class) {
				if (mMainfiestManager == null)
					mMainfiestManager = new MainfiestManager(context);
			}
		}
		return mMainfiestManager;
	}

	private MainfiestManager(Context context) {
		packageManager = context.getPackageManager();
		packageName=context.getPackageName();
		try {
			PackageInfo packageInfo=packageManager.getPackageInfo(packageName,0);
			packageInfo.applicationInfo.loadLabel(packageManager);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取包名
	 * 
	 * @return
	 */
	public String packagePame() {
		return packageName;
	}

	/**
	 * 获取版本名称
	 * 
	 * @return
	 */
	public String versionName() {
		try {
			return packageManager.getPackageInfo(packagePame(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	/**
	 * 获取版本号
	 * 
	 * @return
	 */
	public int versionCode() {
		try {
			return packageManager.getPackageInfo(packagePame(), 0).versionCode;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}

	/**
	 * 获取图标
	 * 
	 * @return
	 */
	public Drawable getAppIcon() {
		try {
			return packageManager.getApplicationInfo(packagePame(), 0)
					.loadIcon(packageManager);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block

		}
		return null;
	}

	/**
	 * 检查是否含有改权限
	 */
	@SuppressLint("NewApi")
	public boolean checkPermission(String permission, String packageName) {
		if (permission == null || permission.isEmpty() || packageName == null
				|| packageName.isEmpty())
			return false;
		return PackageManager.PERMISSION_GRANTED == packageManager
				.checkPermission(permission, packageName);
	}

	/**
	 * 检查权限是否申请成功
	 */
	public boolean shouldShowRequestPermissionRationale(String permission) {
		if (permission == null)
			return false;
		try {
			Method shouldShowRequestPermissionRationaleMethod =packageManager.getClass().getDeclaredMethod("shouldShowRequestPermissionRationale",new Class[] { String.class });
			if (shouldShowRequestPermissionRationaleMethod != null){
				if(!shouldShowRequestPermissionRationaleMethod.isAccessible())
					shouldShowRequestPermissionRationaleMethod.setAccessible(true);
				return (boolean) shouldShowRequestPermissionRationaleMethod.invoke(packageManager,permission);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 检测权限是否申请成功，并且把没有成功的返回
	 */
	public String[] shouldShowRequestPermissionRationale(String[] permissions) {
		if (permissions == null || permissions.length <= 0)
			return permissions;
		List<String> notPermissions = new ArrayList<>(permissions.length);
		for (int i = 0; i < permissions.length; i++) {
			String permission = permissions[i];
			if (shouldShowRequestPermissionRationale(permission))
				continue;
			notPermissions.add(permission);
		}
		return notPermissions.toArray(new String[notPermissions.size()]);
	}

	/**
	 * 检查是否包含这组权限，并且将没有的权限返回
	 */
	@SuppressLint("NewApi")
	public String[] checkPermission(String[] permissions, String packageName) {
		if (permissions == null || permissions.length <= 0
				|| packageName == null || packageName.isEmpty())
			return permissions;
		List<String> checkPermissions = new ArrayList<>(permissions.length);
		for (int i = 0; i < permissions.length; i++) {
			String permission = permissions[i];
			if (checkPermission(permission, packageName))
				continue;
			checkPermissions.add(permission);
		}
		return checkPermissions.toArray(new String[checkPermissions.size()]);
	}

	/**
	 * 获取程序的权限
	 */
	public String[] getAppPremission() {
		try {
			return packageManager.getPackageInfo(packagePame(),
					PackageManager.GET_PERMISSIONS).requestedPermissions;

		} catch (NameNotFoundException e) {

		}
		return null;
	}

	/**
	 * 获取程序的签名
	 */
	public String getAppSignature() {
		try {
			return packageManager.getPackageInfo(packagePame(),
					PackageManager.GET_SIGNATURES).signatures[0]
					.toCharsString();

		} catch (NameNotFoundException e) {
			e.printStackTrace();

		}
		return "";
	}

	/**
	 * 获取application 中的meta—data的值
	 * 
	 * @param metaData
	 *            名称
	 * @return
	 */
	public String metaData(String metaData) {
		return metaData(Type.APPLICATION, metaData, null);
	}


	/**
	 * 获取meta-data的值
	 * 
	 * @param type
	 *            类型
	 * @param metaData
	 *            名称
	 * @param className
	 *            类名
	 * @return
	 */
	public String metaData(Type type, String metaData, String className) {
		ComponentName cn = null;
		if (className != null)
			cn = new ComponentName(packagePame(), className);
		String data = null;
		try {
			switch (type) {
			case APPLICATION:
				data = packageManager.getApplicationInfo(packagePame(),
						PackageManager.GET_META_DATA).metaData
						.getString(metaData);
				break;
			case ACTIVITY:
				data = packageManager.getActivityInfo(cn,
						PackageManager.GET_META_DATA).metaData
						.getString(metaData);
				break;
			case RECEIVER:
				data = packageManager.getReceiverInfo(cn,
						PackageManager.GET_META_DATA).metaData
						.getString(metaData);
				break;
			case SERVICE:
				data = packageManager.getServiceInfo(cn,
						PackageManager.GET_META_DATA).metaData
						.getString(metaData);
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return data;
	}
}
