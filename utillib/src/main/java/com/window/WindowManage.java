package com.window;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class WindowManage {
	private Window window;
	private WindowManager.LayoutParams layoutParams;
	public static final int TOP = Gravity.TOP;
	public static final int BOTTOM = Gravity.BOTTOM;
	public static final int CENTER = Gravity.CENTER;
	public static final int CENTER_HORIZONTAL = Gravity.CENTER_HORIZONTAL;
	public static final int CLIP_VERTICAL = Gravity.CLIP_VERTICAL;
	@SuppressLint("RtlHardcoded")
	public static final int LEFT = Gravity.LEFT;
	@SuppressLint("RtlHardcoded")
	public static final int RIGHT = Gravity.RIGHT;
	public static final int TOP_LEFT = TOP | LEFT;
	public static final int TOP_RIGHT = TOP | RIGHT;
	public static final int BOTTOM_LEFT = BOTTOM | LEFT;
	public static final int BOTTOM_RIGHT = BOTTOM | RIGHT;

	public WindowManage(Window window) {
		// TODO Auto-generated constructor stub
		this.window = window;
		layoutParams = window.getAttributes();
	}

	/**
	 * 获取高度
	 * 
	 * @return
	 */
	public int getHeigth() {
		return layoutParams.height;
	}

	/**
	 * 设置高度
	 * 
	 * @param heigth
	 */
	public void setHeigth(int heigth) {
		layoutParams.height = heigth;
		window.setAttributes(layoutParams);
	}

	/**
	 * 获取宽度
	 * 
	 * @return
	 */
	public int getWidth() {
		return layoutParams.width;
	}

	/**
	 * 设置宽度
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		layoutParams.width = width;
		window.setAttributes(layoutParams);
	}

	/**
	 * 获取位置相对父窗口的位置
	 * 
	 * @return
	 */
	public int getGravity() {
		return layoutParams.gravity;
	}

	/**
	 * 设置位置相对父窗口的位置
	 * 
	 * @return
	 */
	public void setGravity(int gravity) {
		layoutParams.gravity = gravity;
		window.setAttributes(layoutParams);
	}

	public int getFormat() {
		return layoutParams.format;
	}

	/**
	 * 期望的位图格式。默认为不透明。参考android.graphics.PixelFormat
	 * 
	 * @param format
	 */
	public void setFormat(int format) {
		layoutParams.format = format;
		window.setAttributes(layoutParams);
	}

	public int getWindowAnimations() {
		return layoutParams.windowAnimations;
	}

	/**
	 * 窗口所使用的动画设置。它必须是一个系统资源而不是应用程序资源，因为窗口管理器不能访问应用程序。
	 * 
	 * @param windowAnimations
	 */
	public void setWindowAnimations(int windowAnimations) {
		layoutParams.windowAnimations = windowAnimations;
		window.setAttributes(layoutParams);
	}

	public float getAlpha() {
		return layoutParams.alpha;
	}

	/**
	 * 整个窗口的半透明值，1.0表示不透明，0.0表示全透明。
	 * 
	 * @param alpha
	 */
	public void setAlpha(float alpha) {
		layoutParams.alpha = alpha;
		window.setAttributes(layoutParams);
	}

	public float getDimAmount() {
		return layoutParams.dimAmount;
	}

	/**
	 * 当FLAG_DIM_BEHIND设置后生效。该变量指示后面的窗口变暗的程度。 1.0表示完全不透明，0.0表示没有变暗。
	 * 
	 * @param dimAmount
	 */
	public void setDimAmount(float dimAmount) {
		layoutParams.flags = layoutParams.flags | Flags.FLAG_DIM_BEHIND;
		layoutParams.dimAmount = dimAmount;
		window.setAttributes(layoutParams);
	}

	/**
	 * 设置标记
	 * 
	 * @param flags
	 */
	public void setFlags(int flags) {
		layoutParams.flags = layoutParams.flags | flags;
		window.setAttributes(layoutParams);
	}

	/**
	 * 获取屏幕亮度
	 * 
	 * @return
	 */
	public float getScreenBrightness() {
		return layoutParams.screenBrightness;
	}

	/**
	 * 用来覆盖用户设置的屏幕亮度。表示应用用户设置的屏幕亮度。从0到1调整亮度从暗到最亮发生变化
	 * 
	 * @param screenBrightness
	 */
	public void setScreenBrightness(float screenBrightness) {
		layoutParams.screenBrightness = screenBrightness;
		window.setAttributes(layoutParams);
	}

	public int getX() {
		return layoutParams.x;
	}

	public void setX(int x) {
		layoutParams.x = x;
		window.setAttributes(layoutParams);
	}

	public int getY() {
		return layoutParams.y;
	}

	public void setY(int y) {
		layoutParams.y = y;
		window.setAttributes(layoutParams);
	}

	public void setView(int id) {
		window.setContentView(id);
	}

	public void setView(View view) {
		window.setContentView(view);
	}

	public View findId(int id) {
		return window.findViewById(id);
	}

	public void setPadding(int left, int top, int right, int bottom) {
		window.getDecorView().setPadding(left, top, right, bottom);
	}
}