package com.witget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

import com.mesh.InhaleMesh;

import hook.com.utillib.R;

/**
 * HookFrame com.xh.util 2018 2018-5-30 上午10:51:18 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class RecycleViewGroup extends BitmapViewGroup {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private InhaleMesh mInhaleMesh = null;
    private boolean layout = false;
    private InhaleMesh.InhaleDir dir = InhaleMesh.InhaleDir.DOWN;
    private boolean hide = false;
    private boolean intercept = false;

    {
        setFocusable(true);
        mInhaleMesh = new InhaleMesh(WIDTH, HEIGHT);
    }

    public RecycleViewGroup(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RecycleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RecycleViewGroup(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public RecycleViewGroup(Context context, AttributeSet attrs,
                            int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RecycleViewGroup, -1, -1);
        int d = a.getInt(R.styleable.RecycleViewGroup_inhaleDir, 1);
        if (d == 0)
            dir = InhaleMesh.InhaleDir.UP;
        else if (d == 1)
            dir = InhaleMesh.InhaleDir.DOWN;
        else if (d == 2)
            dir = InhaleMesh.InhaleDir.LEFT;
        else
            dir = InhaleMesh.InhaleDir.RIGHT;
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        layout = true;
        super.onLayout(changed, left, top, right, bottom);
        mInhaleMesh.setBitmapSize(width, heigth);
        setInhaleDir(dir);
    }

    public void setInhaleDir(InhaleMesh.InhaleDir dir) {
        this.dir = dir;
        if (!layout)
            return;
        mInhaleMesh.setInhaleDir(dir);
        float w = width;
        float h = heigth;
        float endX = 0;
        float endY = 0;
        switch (dir) {
            case DOWN:
                endX = w / 2;
                endY = 0;
                break;
            case UP:
                endX = w / 2;
                endY = getHeight();
                break;
            case LEFT:
                endX = getWidth();
                endY = h / 2;
                break;
            case RIGHT:
                endX = 0;
                endY = h / 2;
                break;
        }

        buildPaths(endX, endY);
        buildMesh(w, h);
    }

    private void buildMesh(float w, float h) {
        mInhaleMesh.buildMeshes(w, h);
    }

    private void buildPaths(float endX, float endY) {
        mInhaleMesh.buildPaths(endX, endY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (intercept)
            return intercept;
        return super.onInterceptTouchEvent(ev);
    }

    public synchronized boolean hide() {
        if (hide)
            return false;
        return startAnimation(false);
    }

    public synchronized boolean show() {
        if (!hide)
            return false;
        return startAnimation(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Animation anim = this.getAnimation();
        if (anim == null || anim.hasEnded())
            return;
        anim.cancel();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private boolean startAnimation(final boolean reverse) {
        Animation anim = this.getAnimation();
        if (null != anim && !anim.hasEnded()) {
            return false;
        }
        if ((!reverse && hide) || (reverse && !hide))
            return false;
        hide = !reverse;
        intercept = true;
        PathAnimation animation = new PathAnimation(0, HEIGHT + 1, reverse,
                new PathAnimation.IAnimationUpdateListener() {
                    @Override
                    public void onAnimUpdate(int index) {
                        mInhaleMesh.buildMeshes(index);
                        invalidate();
                    }
                });
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (reverse) {
                    intercept = false;
                } else {
                    hideEnd();
                }
            }

        });

        if (null != animation) {
            animation.setDuration(1000);
            this.startAnimation(animation);
        }

        return true;
    }

    protected void hideEnd() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void drawBitmap(Canvas canvas, Bitmap mBitmap) {
        canvas.drawBitmapMesh(mBitmap, mInhaleMesh.getWidth(),
                mInhaleMesh.getHeight(), mInhaleMesh.getVertices(), 0, null, 0,
                null);
    }

    private static class PathAnimation extends Animation {
        public interface IAnimationUpdateListener {
            public void onAnimUpdate(int index);
        }

        private int mFromIndex = 0;
        private int mEndIndex = 0;
        private boolean mReverse = false;
        private IAnimationUpdateListener mListener = null;

        public PathAnimation(int fromIndex, int endIndex, boolean reverse,
                             IAnimationUpdateListener listener) {
            mFromIndex = fromIndex;
            mEndIndex = endIndex;
            mReverse = reverse;
            mListener = listener;
        }

        public boolean getTransformation(long currentTime,
                                         Transformation outTransformation) {

            boolean more = super.getTransformation(currentTime,
                    outTransformation);
            // Log.d("leehong2", "getTransformation    more = " + more);
            return more;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            int curIndex = 0;
            Interpolator interpolator = this.getInterpolator();
            if (null != interpolator) {
                float value = interpolator.getInterpolation(interpolatedTime);
                interpolatedTime = value;
            }

            if (mReverse) {
                interpolatedTime = 1.0f - interpolatedTime;
            }

            curIndex = (int) (mFromIndex + (mEndIndex - mFromIndex)
                    * interpolatedTime);

            if (null != mListener) {
                // Log.i("leehong2", "onAnimUpdate  =========== curIndex = "
                // + curIndex);
                mListener.onAnimUpdate(curIndex);
            }
        }
    }

}
