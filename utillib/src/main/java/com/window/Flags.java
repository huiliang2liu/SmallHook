package com.window;
/**
 * WindowManager.LayoutParams layoutParams 需要设置的flags 默认是 none
 */
public class Flags {
	// 窗口之后的内容变暗。
	public static final int FLAG_DIM_BEHIND = 0x00000002;

	// 窗口之后的内容变模糊。
	public static final int FLAG_BLUR_BEHIND = 0x00000004;

	// 不许获得焦点。
	// 不能获得按键输入焦点，所以不能向它发送按键或按钮事件。那些时间将发送给它后面的可以获得焦点的窗口。此选项还会设置FLAG_NOT_TOUCH_MODAL选项。设置此选项，意味着窗口不能与软输入法进行交互，所以它的Z序独立于任何活动的输入法（换句话说，它可以全屏显示，如果需要的话，可覆盖输入法窗口）。要修改这一行为，可参考FLAG_ALT_FOCUSALBE_IM选项。
	public static final int FLAG_NOT_FOCUSABLE = 0x00000008;

	// 不接受触摸屏事件。
	public static final int FLAG_NOT_TOUCHABLE = 0x00000010;

	// 当窗口可以获得焦点（没有设置 FLAG_NOT_FOCUSALBE
	// 选项）时，仍然将窗口范围之外的点设备事件（鼠标、触摸屏）发送给后面的窗口处理。否则它将独占所有的点设备事件，而不管它们是不是发生在窗口范围内。
	public static final int FLAG_NOT_TOUCH_MODAL = 0x00000020;

	// 如果设置了这个标志，当设备休眠时，点击触摸屏，设备将收到这个第一触摸事件。
	// 通常第一触摸事件被系统所消耗，用户不会看到他们点击屏幕有什么反应。
	public static final int FLAG_TOUCHABLE_WHEN_WAKING = 0x00000040;

	// 当此窗口为用户可见时，保持设备常开，并保持亮度不变。
	public static final int FLAG_KEEP_SCREEN_ON = 0x00000080;

	// 窗口占满整个屏幕，忽略周围的装饰边框（例如状态栏）。此窗口需考虑到装饰边框的内容。
	public static final int FLAG_LAYOUT_IN_SCREEN = 0x00000100;

	// 允许窗口扩展到屏幕之外。
	public static final int FLAG_LAYOUT_NO_LIMITS = 0x00000200;

	// 窗口显示时，隐藏所有的屏幕装饰（例如状态条）。使窗口占用整个显示区域。
	public static final int FLAG_FULLSCREEN = 0x00000400;

	// 此选项将覆盖FLAG_FULLSCREEN选项，并强制屏幕装饰（如状态条）弹出。
	public static final int FLAG_FORCE_NOT_FULLSCREEN = 0x00000800;

	// 抖动。指 对半透明的显示方法。又称“点透”。图形处理较差的设备往往用“点透”替代Alpha混合。
	public static final int FLAG_DITHER = 0x00001000;

	// 不允许屏幕截图。
	public static final int FLAG_SECURE = 0x00002000;

	// 一种特殊模式，布局参数用于指示显示比例。
	public static final int FLAG_SCALED = 0x00004000;

	// 当屏幕有可能贴着脸时，这一选项可防止面颊对屏幕造成误操作。
	public static final int FLAG_IGNORE_CHEEK_PRESSES = 0x00008000;

	// 当请求布局时，你的窗口可能出现在状态栏的上面或下面，从而造成遮挡。当设置这一选项后，窗口管理器将确保窗口内容不会被装饰条（状态栏）盖住。
	public static final int FLAG_LAYOUT_INSET_DECOR = 0x00010000;

	// 反转FLAG_NOT_FOCUSABLE选项。
	// 如果同时设置了FLAG_NOT_FOCUSABLE选项和本选项，窗口将能够与输入法交互，允许输入法窗口覆盖；
	// 如果FLAG_NOT_FOCUSABLE没有设置而设置了本选项，窗口不能与输入法交互，可以覆盖输入法窗口。
	public static final int FLAG_ALT_FOCUSABLE_IM = 0x00020000;

	// 如果你设置了FLAG_NOT_TOUCH_MODAL，那么当触屏事件发生在窗口之外事，可以通过设置此标志接收到一个MotionEvent.ACTION_OUTSIDE事件。注意，你不会收到完整的down/move/up事件，只有第一次down事件时可以收到ACTION_OUTSIDE。
	public static final int FLAG_WATCH_OUTSIDE_TOUCH = 0x00040000;

	// 当屏幕锁定时，窗口可以被看到。这使得应用程序窗口优先于锁屏界面。可配合FLAG_KEEP_SCREEN_ON选项点亮屏幕并直接显示在锁屏界面之前。可使用FLAG_DISMISS_KEYGUARD选项直接解除非加锁的锁屏状态。此选项只用于最顶层的全屏幕窗口。
	public static final int FLAG_SHOW_WHEN_LOCKED = 0x00080000;

	// 请求系统墙纸显示在你的窗口后面。窗口必须是半透明的。
	public static final int FLAG_SHOW_WALLPAPER = 0x00100000;

	// 窗口一旦显示出来，系统将点亮屏幕，正如用户唤醒设备那样。
	public static final int FLAG_TURN_SCREEN_ON = 0x00200000;

	// 解除锁屏。只有锁屏界面不是加密的才能解锁。如果锁屏界面是加密的，那么用户解锁之后才能看到此窗口，除非设置了FLAG_SHOW_WHEN_LOCKED选项。
	public static final int FLAG_DISMISS_KEYGUARD = 0x00400000;

	// 锁屏界面淡出时，继续运行它的动画。
	public static final int FLAG_KEEP_SURFACE_WHILE_ANIMATING = 0x10000000;

	// 以原始尺寸显示窗口。用于在兼容模式下运行程序。
	public static final int FLAG_COMPATIBLE_WINDOW = 0x20000000;

	// 用于系统对话框。设置此选项的窗口将无条件获得焦点。
	public static final int FLAG_SYSTEM_ERROR = 0x40000000;
}