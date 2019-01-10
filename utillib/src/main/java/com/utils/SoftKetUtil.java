package com.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;



public class SoftKetUtil {
	/**
	 * 键盘显示则隐藏，没有显示则弹出
	 * 
	 * @param context
	 */
	public static void hidden(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 软键盘是否显示
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isSoftShowing(Activity activity) {
		// 获取当前屏幕内容的高度
		int screenHeight = activity.getWindow().getDecorView().getHeight();
		// 获取View可见区域的bottom
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

		return screenHeight - rect.bottom != 0;
	}

	/**
	 * 底部虚拟按键栏的高度
	 * 
	 * @return
	 */
	public static int getSoftButtonsBarHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		// 这个方法获取可能不是真实屏幕的高度
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int usableHeight = metrics.heightPixels;
		// 获取当前屏幕的真实高度
		activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
		int realHeight = metrics.heightPixels;
		if (realHeight > usableHeight) {
			return realHeight - usableHeight;
		} else {
			return 0;
		}
	}
}
