package com.load;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.utils.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * com.load.dex
 * 2018/9/19 9:44
 * instructions：加载dex插件
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Load {
    private final static String TAG = "Load";
    private final static String[] APK_END = {".zip", ".apk"};
    private final static String SO = "so";
    private static final String CPU_ARMEABI = "armeabi";
    private static final String CPU_X86 = "x86";
    private static final String CPU_MIPS = "mips";
    private List<ApkResources> mResources;
    private static final String PREFERENCE_NAME = "dynamic_load_configs";
    private File optimizedDirectory;


    public void loadResourec(Context context, String... apkPaths) {
        if (context == null || apkPaths == null || apkPaths.length <= 0)
            return;
        for (String apkPath : apkPaths) {
            for (String end : APK_END) {
                if (apkPath.endsWith(end)) {
                    ApkResources resources = new ApkResources(apkPath, context);
                    if (resources.mPackage == null || resources.mPackage.isEmpty())
                        break;
                    if (mResources == null) {
                        mResources = new ArrayList<>();
                    }
                    mResources.add(resources);
                    break;
                }
            }
        }

    }

    public void loadSo(String... sos) {
        if (optimizedDirectory == null || sos.length <= 0)
            return;
        for (String so : sos)
            System.load(optimizedDirectory.getAbsolutePath() + File.separator + String.format("lib%s.so", so));
    }

    public void loadSo(Context context, String... soPaths) {
        LoadSo.load(soPaths, context);
    }

    public void loadDex(Context context, String[] dexPaths) {
        LoadDex.load(dexPaths, context);
    }

    public void load(File file, Context context) {
        load(file == null || !file.exists() || file.isFile() ? null : file.listFiles(), context);
    }

    public void load(File[] files, Context context) {
        init(files, context);
    }


    public ApkResources name2resources(String packageName) {
        if (packageName == null || packageName.isEmpty() || mResources == null || mResources.size() <= 0)
            return null;
        for (ApkResources resources : mResources) {
            if (packageName.equals(resources.mPackage))
                return resources;
        }
        return null;
    }

    private void init(File[] files, Context context) {
        if (context == null) {
            LogUtil.i("context is null");
            return;
        }
        if (files == null || files.length <= 0) {
            LogUtil.i(files == null ? "files is null" : "files is empty");
            return;
        }
        List<String> dexs = new ArrayList<>();
        List<String> sos = new ArrayList<>();
        for (File file : files) {
            if (!file.exists() || file.isDirectory())
                continue;
            String fileName = file.getAbsolutePath();
            if (fileName.endsWith(".dex")) {//dex文件
                dexs.add(fileName);
            } else if (fileName.endsWith(".zip")) {//zip文件
                dexs.add(fileName);
                loadResourec(context, fileName);
                List<String> so = compress2soPath(file, context);
                if (so != null && so.size() > 0) {
                    sos.addAll(so);
                }
            } else if (fileName.endsWith(".apk")) {//jar文件
                dexs.add(fileName);
                loadResourec(context, fileName);
                List<String> so = compress2soPath(file, context);
                if (so != null && so.size() > 0) {
                    sos.addAll(so);
                }
            } else if (fileName.endsWith(".so")) {//so文件
                dexs.add(fileName);
                sos.add(fileName);
            } else if (fileName.endsWith(".jar")) {//jar文件
                dexs.add(fileName);
            }
        }
        if (dexs.size() > 0)
            LoadDex.load(dexs.toArray(new String[dexs.size()]), context);
        if (sos.size() > 0)
            LoadSo.load(sos.toArray(new String[sos.size()]), context);
        else {
            Log.e(TAG, "so is null");
        }
    }

    private List<String> compress2soPath(File file, Context context) {
        Log.e(TAG, "复制so文件");
        String cpuArchitect = getCpuArch();
        List<String> fileName = new ArrayList<>();
        if (optimizedDirectory == null) {
            optimizedDirectory = new File(context.getFilesDir().getParent(), SO);
            if (!optimizedDirectory.exists())
                optimizedDirectory.mkdirs();
        }
        try {
            ZipInputStream zipInputStream = new ZipInputStream(
                    new FileInputStream(file));
            ZipEntry zipEntry = null;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String zipEntryName = zipEntry.getName();
                if (zipEntryName.endsWith(".so")
                        && zipEntryName.contains(cpuArchitect)) {
                    final long lastModify = zipEntry.getTime();
                    if (lastModify == getSoLastModifiedTime(context,
                            zipEntryName)) {
                        continue;
                    }
                    String[] files = zipEntryName.split("/");
                    File os = new File(optimizedDirectory, files[files.length - 1]);
                    FileOutputStream fos = new FileOutputStream(os);
                    byte[] buff = new byte[1024];
                    int len = -1;
                    while ((len = zipInputStream.read(buff)) > 0) {
                        fos.write(buff, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    os.setReadOnly();
                    fileName.add(os.getAbsolutePath());
                    setSoLastModifiedTime(context, zipEntryName, lastModify);
                }
                zipInputStream.closeEntry();
            }
            zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            br.close();
            String[] array = text.split(":\\s+", 2);
            if (array.length >= 2) {
                return array[1];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getCpuArch() {
        String cpuArchitect = CPU_ARMEABI;
        String cpuName = getCpuName();
        if (cpuName.toLowerCase().contains("arm")) {
            cpuArchitect = CPU_ARMEABI;
        } else if (cpuName.toLowerCase().contains("x86")) {
            cpuArchitect = CPU_X86;
        } else if (cpuName.toLowerCase().contains("mips")) {
            cpuArchitect = CPU_MIPS;
        }
        return cpuArchitect;
    }

    public static void setSoLastModifiedTime(Context cxt, String soName,
                                             long time) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        prefs.edit().putLong(soName, time).apply();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static long getSoLastModifiedTime(Context cxt, String soName) {
        SharedPreferences prefs = cxt.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        return prefs.getLong(soName, 0);
    }


}
