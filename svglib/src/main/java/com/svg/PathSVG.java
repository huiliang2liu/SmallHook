package com.svg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;

import com.svg.vector.VectorPathParser;
import com.util.PathUtil;
import com.utils.LogUtil;

/**
 * com.svg
 * 2019/1/11 11:05
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class PathSVG extends ASVG {
    int color = Color.WHITE;
    private final static String TAG = "PathSVG";
    private Path path;


    public void svg2path(String string) {
        path = SVGPars.doPath(string);
        setW();
    }

    public void vector2path(String string) {
        path = VectorPathParser.createPathFromPathData(string);
        setW();
    }

    private void setW() {
        RectF rectF = new RectF();
        path.computeBounds(rectF, false);
        LogUtil.e(TAG, "l=" + rectF.left + ",r=" + rectF.right + ",t=" + rectF.top + ",b=" + rectF.bottom);
        setViewBoxWidth((int) rectF.right);
        setViewBoxHeigth((int) rectF.bottom);
        LogUtil.e(TAG, "width=" + getViewBoxWidth() + " heigth="
                + getViewBoxHeigth());
    }

    @Override
    public Path createPath(float factor) {
        return PathUtil.factorPath(path, factor);
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int color() {
        // TODO Auto-generated method stub
        return color;
    }

    @Override
    protected void childDraw(Canvas canvas, float factor) {
        // TODO Auto-generated method stub
        LogUtil.e(TAG, "childDraw");
        Path path1 = PathUtil.factorPath(path, factor);
        paint.setColor(color);
        canvas.drawPath(path1, paint);
    }

}
