package com.witget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * HookFrame com.xh.util 2018 2018-5-30 上午10:41:22 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class BitmapViewGroup extends FrameLayout {
    private Canvas mCanvas;
    private Bitmap mBitmap;
    protected boolean ready = false;
    protected int width;
    protected int heigth;
    protected Context mContext;

    public BitmapViewGroup(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public BitmapViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public BitmapViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public BitmapViewGroup(Context context, AttributeSet attrs,
                           int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // TODO Auto-generated constructor stub
        init(context);
    }

    private void init(Context context) {
        // TODO Auto-generated method stub
        mCanvas = new Canvas();
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
        // TODO Auto-generated method stub
        ready = false;
        super.setLayoutParams(params);
    }

    @SuppressLint("NewApi")
    @Override
    public void setTranslationX(float translationX) {
        // TODO Auto-generated method stub
        ready = false;
        super.setTranslationX(translationX);
    }

    @SuppressLint("NewApi")
    @Override
    public void setTranslationY(float translationX) {
        // TODO Auto-generated method stub
        ready = false;
        super.setTranslationY(translationX);
    }

    @SuppressLint("NewApi")
    @Override
    public void setTranslationZ(float translationX) {
        // TODO Auto-generated method stub
        ready = false;
        super.setTranslationZ(translationX);
    }

    @SuppressLint("NewApi")
    @Override
    public void setRotationX(float rotationX) {
        // TODO Auto-generated method stub
        ready = false;
        super.setRotationX(rotationX);
    }

    @SuppressLint("NewApi")
    @Override
    public void setRotationY(float rotationX) {
        // TODO Auto-generated method stub
        ready = false;
        super.setRotationY(rotationX);
    }

    @SuppressLint("NewApi")
    @Override
    public void setRotation(float rotationX) {
        // TODO Auto-generated method stub
        ready = false;
        super.setRotation(rotationX);
    }

    @Override
    public void requestLayout() {
        // TODO Auto-generated method stub
        ready = false;
        super.requestLayout();
    }

    @Override
    public void addView(View child) {
        // TODO Auto-generated method stub
        ready = false;
        super.addView(child);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        heigth = getMeasuredHeight();
        if (width > 0 && heigth > 0) {
            mBitmap = Bitmap.createBitmap(width, heigth, Config.ARGB_8888);
            mCanvas.setBitmap(mBitmap);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        if (mBitmap == null) {
            super.dispatchDraw(canvas);
            return;
        }
        if (!ready) {
            super.dispatchDraw(mCanvas);
            drawBitmap(canvas, mBitmap);
            ready = true;
        } else
            drawBitmap(canvas, mBitmap);
    }

    protected void drawBitmap(Canvas canvas, Bitmap bitmap) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
}
