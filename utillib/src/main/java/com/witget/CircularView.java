package com.witget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.utils.LogUtil;

/**
 * View com.xh.witget 2018 2018-5-30 下午6:41:02 instructions： author:liuhuiliang
 * email:825378291@qq.com
 **/

public class CircularView extends BitmapViewGroup {
    private final static String TAG = "CircularView";
    private boolean cirular = true;
    private float rx;
    private float ry;
    private Paint mBitmapPaint;
    private BitmapShader mBitmapShader;

    {
        mBitmapPaint = new Paint();
    }

    // private

    public CircularView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public CircularView(Context context, AttributeSet attrs, int defStyleAttr,
                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        float x = ev.getX();
        float y = ev.getY();
        if (cirular && Math.pow(x / rx - 1, 2) + Math.pow(y / ry - 1, 2) > 1) {
            return true;
        } else {
            if ((x > rx && rx < (width - rx)) || (y > ry && y < (heigth - ry)))
                return super.onInterceptTouchEvent(ev);
            if (y < ry && x > (width - rx)) {
                x = width - x;
            } else if (x < rx && y > (heigth - ry)) {
                y = heigth - y;
            } else if (x > (width - rx) && y > (heigth - ry)) {
                x = width - x;
                y = heigth - y;
            }
            if (Math.pow(x / rx - 1, 2) + Math.pow(y / ry - 1, 2) > 1)
                return true;

        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        LogUtil.i(TAG, "left=" + left + " right=" + right + " top=" + top + " bottom=" + bottom);
        if (cirular) {
            heigth = width = getMeasuredWidth();
            rx = ry = width * .5f;
        } else {
            heigth = getMeasuredHeight();
            width = getMeasuredWidth();
            rx = width * .05f;
            ry = heigth * .05f;
        }
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (cirular) {
            int mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void drawBitmap(Canvas canvas, Bitmap bitmap) {
        // TODO Auto-generated method stub
        // super.drawBitmap(canvas, bitmap);
        if (mBitmapShader == null || !ready) {
            mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP,
                    TileMode.CLAMP);
            mBitmapPaint.setShader(mBitmapShader);
        }
        canvas.drawRoundRect(new RectF(0, 0, width, heigth),rx, ry, mBitmapPaint);
//        canvas.drawRect();
//        canvas.drawRoundRect(0, 0, width, heigth, rx, ry, mBitmapPaint);
    }

}
