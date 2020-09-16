package com.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class CpuUtil {
    private static String cpuMode;
    private static String maxCpuFreq;
    private static String minCpuFreq;

    @SuppressLint("NewApi")
    public static String getCpuInfo() {
        if (!NullUtil.isEmpty(cpuMode))
            return cpuMode;
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            if (str2 != null) {
                str2 = str2.trim();
                if (!str2.isEmpty()) {
                    String[] ss = str2.split("\\s+", 2);
                    if (ss.length > 1)
                        str2 = ss[1];
                }
            }
            localBufferedReader.close();
            cpuMode = str2;
        } catch (IOException e) {
        }
        return cpuMode;
    }

    // 获取CPU最大频率（单位KHZ）
    // "/system/bin/cat" 命令行
    // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
    public static String getMaxCpuFreq() {
        if (!NullUtil.isEmpty(maxCpuFreq))
            return maxCpuFreq;
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        maxCpuFreq = result.trim();
        return maxCpuFreq;
    }

    // 获取CPU最小频率（单位KHZ）
    public static String getMinCpuFreq() {
        if (!NullUtil.isEmpty(minCpuFreq))
            return minCpuFreq;
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        minCpuFreq = result.trim();
        return minCpuFreq;
    }

    // 实时获取CPU当前频率（单位KHZ）
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, String> getCpuUser() {
        Map<String, String> params = new HashMap<>();
        InputStream inputStream = null;
        InputStreamReader streamReader = null;
        BufferedReader bufferedReader = null;
        try {
            Process p = Runtime.getRuntime().exec("top -n 1");
            inputStream = p.getInputStream();
            streamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(streamReader);
            String result;
            while ((result = bufferedReader.readLine()) != null) {
                if (result.trim().length() < 1) {
                    continue;
                } else {
                    String[] strings = result.split(",");
                    for (String s : strings) {
                        String[] strings1 = s.split(" ");
                        params.put(strings1[0].trim(), strings1[1].trim());
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (streamReader != null)
                try {
                    streamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return params;
    }
}
