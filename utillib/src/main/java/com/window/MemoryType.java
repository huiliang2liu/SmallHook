package com.window;
/**
 * WindowManager.LayoutParams layoutParams 需要设置的memoryType 默认是 NORMAL
 */
public class MemoryType {
	// 窗口缓冲位于主内存。
	public static final int MEMORY_TYPE_NORMAL = 0;
	// 窗口缓冲位于可以被DMA访问，或者硬件加速的内存区域。
	public static final int MEMORY_TYPE_HARDWARE = 1;
	// 窗口缓冲位于可被图形加速器访问的区域。
	public static final int MEMORY_TYPE_GPU = 2;
	// 窗口缓冲不拥有自己的缓冲区，不能被锁定。缓冲区由本地方法提供。
	public static final int MEMORY_TYPE_PUSH_BUFFERS = 3;
}
