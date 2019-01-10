package com.load;

import android.content.Context;
import android.util.Log;

import com.reflect.FieldManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * com.load
 * 2018/9/19 11:55
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
class LoadSo {
    private final static String TAG = "LoadSo";
    private final static String[] SO_END = {".so"};
    private final static Class<BaseDexClassLoader> CLS;
    private static Field pathList;
    private static Field nativeLibraryDirectories;

    static {
        CLS = BaseDexClassLoader.class;
        pathList= FieldManager.field(CLS,"pathList");
    }

    public static boolean load(String[] soFilePaths, Context context) {
        if (context == null || soFilePaths == null || soFilePaths.length <= 0)
            return false;
        try {
            Object object = pathList.get(context.getClassLoader());
            if (nativeLibraryDirectories == null) {
                nativeLibraryDirectories = object.getClass().getDeclaredField("nativeLibraryDirectories");
                if (!nativeLibraryDirectories.isAccessible())
                    nativeLibraryDirectories.setAccessible(true);
            }
            List<File> sos = new ArrayList<>();
            for (String path : soFilePaths) {
                for (String end : SO_END) {
                    if (path.endsWith(end)) {
                        File file = new File(path);
                        if (!file.exists() || !file.isFile())
                            break;
                        Log.e(TAG, path);
                        sos.add(file);
                        break;
                    }
                }
            }
            int size = sos.size();
            if (size <= 0)
                return true;
            Object files = nativeLibraryDirectories.get(object);
            if (files == null)
                return false;
            Class filesClass = files.getClass();
            if (filesClass.isArray()) {//数组
                for (File file : (File[]) files) {
                    sos.add(file);
                }
                nativeLibraryDirectories.set(object, sos.toArray(new File[sos.size()]));
            } else {//列表
                ((List) files).addAll(sos);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
