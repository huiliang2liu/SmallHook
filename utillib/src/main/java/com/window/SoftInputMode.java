package com.window;
/**
 * WindowManager.LayoutParams layoutParams 需要设置的softInputMode
 */
public class SoftInputMode {
	// 软输入区域是否可见。
	public static final int SOFT_INPUT_MASK_STATE = 0x0f;

	// 未指定状态。
	public static final int SOFT_INPUT_STATE_UNSPECIFIED = 0;

	// 不要修改软输入法区域的状态。
	public static final int SOFT_INPUT_STATE_UNCHANGED = 1;

	// 隐藏输入法区域（当用户进入窗口时）。
	public static final int SOFT_INPUT_STATE_HIDDEN = 2;

	// 当窗口获得焦点时，隐藏输入法区域。
	public static final int SOFT_INPUT_STATE_ALWAYS_HIDDEN = 3;

	// 显示输入法区域（当用户进入窗口时）。
	public static final int SOFT_INPUT_STATE_VISIBLE = 4;

	// 当窗口获得焦点时，显示输入法区域。
	public static final int SOFT_INPUT_STATE_ALWAYS_VISIBLE = 5;

	// 窗口应当主动调整，以适应软输入窗口。
	public static final int SOFT_INPUT_MASK_ADJUST = 0xf0;

	// 未指定状态，系统将根据窗口内容尝试选择一个输入法样式。
	public static final int SOFT_INPUT_ADJUST_UNSPECIFIED = 0x00;

	// 当输入法显示时，允许窗口重新计算尺寸，使内容不被输入法所覆盖。
	// 不可与SOFT_INPUT_ADJUSP_PAN混合使用,如果两个都没有设置，系统将根据窗口内容自动设置一个选项。
	public static final int SOFT_INPUT_ADJUST_RESIZE = 0x10;

	// 输入法显示时平移窗口。它不需要处理尺寸变化，框架能够移动窗口以确保输入焦点可见。
	// 不可与SOFT_INPUT_ADJUST_RESIZE混合使用;如果两个都没设置,系统将根据窗口内容自动设置一个选项。
	public static final int SOFT_INPUT_ADJUST_PAN = 0x20;

	// 当用户转至此窗口时，由系统自动设置，所以你不要设置它。
	// 当窗口显示之后该标志自动清除。
	public static final int SOFT_INPUT_IS_FORWARD_NAVIGATION = 0x100;

}
