package com.witget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import java.math.BigDecimal;


public class OrigamiView extends BitmapViewGroup {
    private static final int NUM_OF_POINT = 8;
    private boolean right = false;
    private boolean touch = false;
    /**
     * 图片的折叠后的总宽度
     */
    private float mTranslateDis;

    protected float mFactor = 1.0f;

    private int mNumOfFolds = 8;

    private Matrix[] mMatrices = new Matrix[mNumOfFolds];

    private Paint mSolidPaint;

    private Paint mShadowPaint;
    private Matrix mShadowGradientMatrix;
    private LinearGradient mShadowGradientShader;

    private float mFlodWidth;
    private GestureDetector mScrollGestureDetector;
    private float mTranslateDisPerFlod;

    {
        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrices[i] = new Matrix();
        }

        mSolidPaint = new Paint();
        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Style.FILL);
        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0, Color.BLACK,
                Color.TRANSPARENT, TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);
        mShadowGradientMatrix = new Matrix();
        mScrollGestureDetector = new GestureDetector(mContext,
                new ScrollGestureDetector());
    }

    public OrigamiView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public OrigamiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public OrigamiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public OrigamiView(Context context, AttributeSet attrs, int defStyleAttr,
                       int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // TODO Auto-generated constructor stub
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        updateFold();
    }

    private void updateFold() {
        mTranslateDis = width * mFactor;
        mFlodWidth = width / mNumOfFolds;
        mTranslateDisPerFlod = mTranslateDis / mNumOfFolds;
        int alpha = (int) (255 * (1 - mFactor));
        mSolidPaint.setColor(Color.argb((int) (alpha * 0.8F), 0, 0, 0));
        mShadowGradientMatrix.setScale(mFlodWidth, 1);
        mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
        mShadowPaint.setAlpha(alpha);
        float depth = (float) (Math.sqrt(mFlodWidth * mFlodWidth
                - mTranslateDisPerFlod * mTranslateDisPerFlod) / 2);
        float[] src = new float[NUM_OF_POINT];
        float[] dst = new float[NUM_OF_POINT];
        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrices[i].reset();
            src[0] = i * mFlodWidth;
            src[1] = 0;
            src[2] = src[0] + mFlodWidth;
            src[3] = 0;
            src[4] = src[2];
            src[5] = heigth;
            src[6] = src[0];
            src[7] = src[5];
            boolean isEven = i % 2 == 0;
            dst[0] = i * mTranslateDisPerFlod;
            dst[1] = isEven ? 0 : depth;
            dst[2] = dst[0] + mTranslateDisPerFlod;
            dst[3] = isEven ? depth : 0;
            dst[4] = dst[2];
            dst[5] = isEven ? heigth - depth : heigth;
            dst[6] = dst[0];
            dst[7] = isEven ? heigth : heigth - depth;
            for (int y = 0; y < 8; y++) {
                dst[y] = Math.round(dst[y]);
            }
            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        }
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isTouch() {
        return touch;
    }

    public void setTouch(boolean touch) {
        this.touch = touch;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (!touch)
            return super.onTouchEvent(event);
        return mScrollGestureDetector.onTouchEvent(event);
    }

    class ScrollGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            mTranslateDis -= distanceX;

            if (mTranslateDis < 0) {
                mTranslateDis = 0;
            }
            if (mTranslateDis > width) {
                mTranslateDis = width;
            }

            float factor = mTranslateDis / width;
            setFactor(factor);
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mFactor >= 0.5)
                show();
            else
                hide();
            return true;
        }
    }

    public void setFactor(float factor) {

        BigDecimal b = new BigDecimal(factor);
        factor = b.setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
        this.mFactor = factor;
        updateFold();
        invalidate();
    }

    @Override
    protected void drawBitmap(Canvas canvas, Bitmap bitmap) {
        // TODO Auto-generated method stub
        if (mFactor == 0)
            return;
        if (mFactor == 1 || mFactor == 0) {
            canvas.drawBitmap(bitmap, 0, 0, null);
            return;
        }
        if (right)
            canvas.translate((width - mTranslateDis), 0);
        for (int i = 0; i < mNumOfFolds; i++) {
            canvas.save();
            canvas.concat(mMatrices[i]);
            canvas.clipRect(mFlodWidth * i, 0, mFlodWidth * i + mFlodWidth,
                    getHeight());
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.translate(mFlodWidth * i, 0);
            if (i % 2 == 0) {
                canvas.drawRect(0, 0, mFlodWidth, heigth, mSolidPaint);
            } else {
                canvas.drawRect(0, 0, mFlodWidth, heigth, mShadowPaint);
            }
            canvas.restore();
        }
    }

    public void hide() {
        Animation animation = new Animation() {
        };
        animation.setDuration(300);
        animation.setInterpolator(new Interpolator() {
            float factor = mFactor;

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                int f = (int) ((1 - input) * 100);
                setFactor(factor * f * .01f);
                return input;
            }
        });
        startAnimation(animation);
    }

    public void show() {
        Animation animation = new Animation() {
        };
        animation.setDuration(300);
        animation.setInterpolator(new Interpolator() {
            float dF = 1 - mFactor;
            float factor = mFactor;

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                int f = (int) (dF * input * 100);
                setFactor(factor + f * .01f);
                return input;
            }
        });
        startAnimation(animation);
    }
}
