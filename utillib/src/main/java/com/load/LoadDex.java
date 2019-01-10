package com.load;

import android.content.Context;

import com.reflect.FieldManager;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * com.load
 * 2018/9/19 12:46
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
class LoadDex {
    private final static Class<BaseDexClassLoader> CLS = BaseDexClassLoader.class;
    private final static String DEX = "dex";
    private final static String[] DEX_END = {".dex", ".zip", ".apk", ".jar"};
    private static Field pathList;
    private static Field dexElements;

    static {
        pathList = FieldManager.field(CLS, "pathList");
    }

    public static boolean load(String[] dexPaths, Context context) {
        if (context == null || dexPaths == null || dexPaths.length <= 0)
            return false;
        File optimizedDirectory = context.getDir(DEX, Context.MODE_PRIVATE);
        try {
            BaseDexClassLoader baseDexClassLoader = new BaseDexClassLoader(loadDex(dexPaths),
                    optimizedDirectory, null, context.getClassLoader());
            Object basePathList = pathList.get(baseDexClassLoader);
            ClassLoader pathClassLoader = context.getClassLoader();
            Object pathPathList = pathList.get(pathClassLoader);
            if (dexElements == null) {
                dexElements = basePathList.getClass().getDeclaredField("dexElements");
                if (!dexElements.isAccessible())
                    dexElements.setAccessible(true);
            }
            Object baseDexElements = dexElements.get(basePathList);
            Object pathDexElements = dexElements.get(pathPathList);
            if (pathDexElements.getClass().isArray()) {//数组
                List list = new ArrayList();
                for (Object dexElement : (Object[]) baseDexElements) {
                    list.add(dexElement);
                }
                for (Object dexElement : (Object[]) pathDexElements) {
                    list.add(dexElement);
                }
                dexElements.set(pathPathList, list.toArray((Object[]) Array.newInstance((baseDexElements).getClass().getComponentType(), list.size())));
            } else {//列表
                ((List) pathDexElements).addAll((List) baseDexElements);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String loadDex(String[] dexFilePaths) {
        if (dexFilePaths == null || dexFilePaths.length <= 0)
            return null;
        StringBuffer dexPath = new StringBuffer();
        for (String path : dexFilePaths) {
            for (String end : DEX_END) {
                if (path.endsWith(end)) {
                    dexPath.append(path).append(":");
                    break;
                }
            }
        }
        if (dexPath.length() <= 0)
            return null;
        return dexPath.substring(0, dexPath.length() - 1);
    }
}
