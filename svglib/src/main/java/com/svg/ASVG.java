package com.svg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.iface.ISVG;
import com.image.ImageUtil;
import com.utils.StringUtil;

import java.lang.reflect.Field;

/**
 * com.svg
 * 2019/1/11 10:50
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public abstract class ASVG implements ISVG {
    private Matrix matrix;
    private int width;
    private int heigth;
    private int viewBoxWidth;
    private int viewBoxHeigth;
    private float x = 0;
    private float y = 0;
    protected Paint paint;

    public ASVG() {
        matrix = new Matrix();
        matrix.setValues(new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f });
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public Paint getPaint() {
        return paint;
    }

    @Override
    public Bitmap createBitmap(int width, int heigth) {
        // TODO Auto-generated method stub
        return createBitmap(factor(width, heigth));
    }

    @Override
    public Bitmap createBitmap(View view) {
        // TODO Auto-generated method stub
        return createBitmap(factor(getViewWidth(view), getViewHeight(view)));
    }

    @Override
    public Bitmap createBitmap(float factor) {
        // TODO Auto-generated method stub
        return ImageUtil.drawable2bitmap(createDrawable(factor));
    }

    @Override
    public Bitmap createBitmap() {
        // TODO Auto-generated method stub
        return createBitmap(factor(0, 0));
    }

    @Override
    public Drawable createDrawable(int width, int heigth) {
        // TODO Auto-generated method stub
        return createDrawable(factor(width, heigth));
    }

    @Override
    public Drawable createDrawable(View view) {
        // TODO Auto-generated method stub
        return createDrawable(factor(getViewWidth(view), getViewHeight(view)));
    }

    @Override
    public Drawable createDrawable() {
        // TODO Auto-generated method stub
        return createDrawable(factor(0, 0));
    }

    @Override
    public Path createPath(int width, int heigth) {
        // TODO Auto-generated method stub
        return createPath(factor(width, heigth));
    }

    @Override
    public Drawable createDrawable(float factor) {
        // TODO Auto-generated method stub
        return new EntityDrawable(factor);
    }

    @Override
    public Path createPath(View view) {
        // TODO Auto-generated method stub
        return createPath(factor(getViewWidth(view), getViewHeight(view)));
    }

    @Override
    public Path createPath() {
        // TODO Auto-generated method stub
        return createPath(factor(0, 0));
    }

    /**
     *
     * lhl 2018-1-9 下午4:35:20 说明：获取缩放因子
     *
     * @param width
     * @param heigth
     * @return int
     */
    private float factor(int width, int heigth) {
        if (width <= 0 && heigth <= 0) {
            if (this.width > 0 || this.heigth > 0) {
                width = this.width;
                heigth = this.heigth;
            } else
                return 1.0f;
        }
        float fw = width * 1.0f / viewBoxWidth;
        float fh = heigth * 1.0f / viewBoxHeigth;
        if (fw == 0)
            return fh;
        if (fh == 0)
            return fw;
        return fw > fh ? fh : fw;
    }

    private int getViewHeight(View view) {
        // TODO Auto-generated method stub
        if (view != null) {
            int width = 0;
            width = view.getHeight();
            if (width <= 0) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params != null) {
                    width = params.height;
                    if (width < 0)
                        width = getValue(view, "mMaxHeight");
                }
            }
            return width;
        }
        return 0;
    }

    public int getViewWidth(View view) {
        // TODO Auto-generated method stub
        if (view != null) {
            int width = 0;
            width = view.getWidth();
            if (width <= 0) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params != null) {
                    width = params.width;
                    if (width < 0)
                        width = getValue(view, "mMaxWidth");
                }
            }
            return width;
        }
        return 0;
    }

    private int getValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            if (!field.isAccessible())
                field.setAccessible(true);
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE)
                value = fieldValue;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return value;
    }

    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return width;
    }

    @Override
    public int getHeigth() {
        // TODO Auto-generated method stub
        return heigth;
    }

    @Override
    public int getViewBoxWidth() {
        // TODO Auto-generated method stub
        return viewBoxWidth;
    }

    @Override
    public int getViewBoxHeigth() {
        // TODO Auto-generated method stub
        return viewBoxHeigth;
    }

    @Override
    public float getX() {
        // TODO Auto-generated method stub
        return x;
    }

    @Override
    public float getY() {
        // TODO Auto-generated method stub
        return y;
    }

    public void setWidth(String width) {
        this.width = StringUtil.width(width);
    }

    public void setHeigth(String heigth) {
        this.heigth = StringUtil.width(heigth);
    }

    public void setViewBoxWidth(int viewBoxWidth) {
        this.viewBoxWidth = viewBoxWidth;
    }

    public void setViewBoxWidth(String string) {
        this.viewBoxWidth = StringUtil.width(string);
    }

    public void setViewBoxHeigth(int viewBoxHeigth) {
        this.viewBoxHeigth = viewBoxHeigth;
    }

    public void setViewBoxHeigth(String viewBoxHeigth) {
        this.viewBoxHeigth = StringUtil.width(viewBoxHeigth);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    private class EntityDrawable extends Drawable {
        private float factor;

        public EntityDrawable(float factor) {
            // TODO Auto-generated constructor stub
            this.factor = factor;
        }

        @Override
        public void setColorFilter(ColorFilter arg0) {
            // TODO Auto-generated method stub
            paint.setColorFilter(arg0);
        }

        @Override
        public void setAlpha(int arg0) {
            // TODO Auto-generated method stub
            paint.setAlpha(arg0);
        }

        @Override
        public int getOpacity() {
            // TODO Auto-generated method stub
            return PixelFormat.UNKNOWN;
        }

        @Override
        public void draw(Canvas canvas) {
            // TODO Auto-generated method stub
            childDraw(canvas, factor);
        }

    }

    protected abstract void childDraw(Canvas canvas, float factor);
}
