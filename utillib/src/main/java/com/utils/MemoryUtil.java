package com.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MemoryUtil {
	/**
	 *：获取系统可用内存
	 * @param activity
	 * @return
	 *long
	 */
	public static long getAvaulRam(Activity activity) {
		ActivityManager mActivityManager = (ActivityManager) activity
				.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		mActivityManager.getMemoryInfo(mi);
		return mi.availMem;
	}

	/**
	 * 获取总内存
	 */
	public static long getTotalMemor() {
		// TODO Auto-generated method stub
		return getTotalMemors()[0];
	}

	/**
	 * 获取可用总内存
	 */
	public static long getTotalUsableMemor() {
		// TODO Auto-generated method stub
		return getTotalMemors()[1];
	}

	/**
	 * 获取总内存
	 */
	public static long[] getTotalMemors() {
		// TODO Auto-generated method stub
		File path2 = Environment.getDataDirectory();
		StatFs stat2 = new StatFs(path2.getPath());
		@SuppressWarnings("deprecation")
		long blockSize2 = stat2.getBlockSize();
		@SuppressWarnings("deprecation")
		long totalBlocks2 = stat2.getBlockCount();
		@SuppressWarnings("deprecation")
		long availableBlocks2 = stat2.getAvailableBlocks();

		long totalSize2 = totalBlocks2 * blockSize2;
		long availSize2 = availableBlocks2 * blockSize2;
		return new long[] { totalSize2, availSize2 };
	}

	/**
	 * 获sdc内存
	 */
	public static long getToTalSdc() {
		return getSdcMemor()[0];
	}

	/**
	 * 获sdc可用内存
	 */
	public static long getUsableSdc() {
		return getSdcMemor()[1];
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static long[] getSdcMemor() {
		// 得到文件系统的信息：存储块大小，总的存储块数量，可用存储块数量
		// 获取sd卡空间
		// 存储设备会被分为若干个区块
		// 每个区块的大小 * 区块总数 = 存储设备的总大小
		// 每个区块的大小 * 可用区块的数量 = 存储设备可用大小
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize;
		long totalBlocks;
		long availableBlocks;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			blockSize = stat.getBlockSizeLong();
			totalBlocks = stat.getBlockCountLong();
			availableBlocks = stat.getAvailableBlocksLong();
		} else {
			blockSize = stat.getBlockSize();
			totalBlocks = stat.getBlockCount();
			availableBlocks = stat.getAvailableBlocks();
		}
		return new long[] { blockSize * totalBlocks,
				blockSize * availableBlocks };
	}

	/**
	 * 获取系统内存信息
	 */
	@SuppressLint("NewApi")
	public static Map<String, String> getMeminfo() {
		Map<String, String> map = new HashMap<String, String>(40, 0.95f);
		String str1 = "/proc/meminfo";
		String str2;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			while (str2 != null) {
				str2 = str2.trim();
				if (!str2.isEmpty()) {
					String[] ss = str2.split("\\s+");
					map.put(ss[0].substring(0, ss[0].length() - 1), ss[1]);
				}
				str2 = localBufferedReader.readLine();
			}
			localBufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public long getMemFree() {
		// MemTotal
		Map<String, String> map = getMeminfo();
		if (map.containsKey("MemFree")) {
			return Integer.valueOf(map.get("MemFree")).intValue() * 1024;
		} else {
			return 0;
		}
	}

	public long getCached() {
		// MemTotal
		Map<String, String> map = getMeminfo();
		if (map.containsKey("Cached")) {
			return Integer.valueOf(map.get("Cached")).intValue() * 1024;
		} else {
			return 0;
		}
	}

	/**
	 * 获取系统内存
	 */
	public long getActive() {
		// MemTotal
		Map<String, String> map = getMeminfo();
		if (map.containsKey("Active")) {
			return Integer.valueOf(map.get("Active")).intValue() * 1024;
		} else {
			return 0;
		}
	}

	/**
	 * 获取系统内存
	 */
	public long getInactive() {
		// MemTotal
		Map<String, String> map = getMeminfo();
		if (map.containsKey("Inactive")) {
			return Integer.valueOf(map.get("Inactive")).intValue() * 1024;
		} else {
			return 0;
		}
	}

	/**
	 * 获取系统内存
	 */
	public long getSwapCached() {
		// MemTotal
		Map<String, String> map = getMeminfo();
		if (map.containsKey("SwapCached")) {
			return Integer.valueOf(map.get("SwapCached")).intValue() * 1024;
		} else {
			return 0;
		}
	}

	/**
	 * 获取系统内存
	 */
	public long getMemTotal() {
		// MemTotal
		Map<String, String> map = getMeminfo();
		if (map.containsKey("MemTotal")) {
			return Integer.valueOf(map.get("MemTotal")).intValue() * 1024;
		} else {
			return 0;
		}
	}

}
