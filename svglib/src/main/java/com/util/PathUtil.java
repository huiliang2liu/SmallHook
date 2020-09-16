package com.util;

import android.graphics.Matrix;
import android.graphics.Path;

/**
 * com.util
 * 2019/1/11 12:40
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class PathUtil {
    public static Path factorPath(Path path, float factor) {
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f});
        matrix.postScale(factor, factor);
        Path path1 = new Path();
        if (path != null)
            path1.addPath(path, matrix);
        return path1;
    }

    public static float string2float(String f) {
        if (f.endsWith("px"))
            return Float.valueOf(f.substring(0, f.length() - 2));
        return Float.valueOf(f);
    }
}
