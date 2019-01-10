package com.witget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.animation.AnimatorFactory;
import com.animation.ViewEmbellish;

import java.util.ArrayList;
import java.util.List;


/**
 * HookFrame com.xh.util 2018 2018-5-28 下午12:44:01 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class ShufflViewGroup extends FrameLayout {
    List<View> views;
    private int index = 0;
    private int count = -1;
    private int time = 300;
    private int width;
    private int heigth;
    private boolean animator = false;
    private Context mContext;
    private Animator objectAnimator;

    public ShufflViewGroup(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public ShufflViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public ShufflViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init(context);
    }

    @SuppressLint("NewApi")
    public ShufflViewGroup(Context context, AttributeSet attrs,
                           int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        // TODO Auto-generated constructor stub
    }

    private void init(Context context) {
        // TODO Auto-generated method stub
        views = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (objectAnimator != null)
            objectAnimator.cancel();
    }

    @Override
    protected synchronized void onLayout(boolean changed, int left, int top,
                                         int right, int bottom) {
        // TODO Auto-generated method stub
        width = right - left;
        heigth = bottom - top;
        if (count == -1) {
            count = getChildCount();
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    views.add(getChildAt(i));
                }
                for (int i = 1; i < count; i++) {
                    removeView(views.get(i));
                }
            }
        }
        super.onLayout(changed, left, top, right, bottom);

    }

    public synchronized void next() {
        index++;
        if (index >= count)
            index = 0;
        next(index);
    }


    public synchronized void next(int next) {
        if (next >= count || next < 0)
            next = 0;
        Log.e("next", "next=" + next + "   count=" + count);
        View view = getChildAt(0);
        View nextView = views.get(index);
        animator(view, nextView);
    }

    private synchronized void animator(View view, View nextView) {
        // TODO Auto-generated method stub
        // rotation(view, nextView);
        if (animator)
            return;
        animator = true;
        zoom0(view, nextView);
    }

    @SuppressLint("NewApi")
    private void rotationY0(final View view, final View nextView) {
        ViewEmbellish embellish = new ViewEmbellish(view);
        addView(nextView);
        view.setPivotX(0);
        nextView.setPivotX(width);
        nextView.setRotationY(-90);
        objectAnimator = AnimatorFactory.rotatioY(embellish, 0, 90,
                time);
        objectAnimator.setInterpolator(new Interpolator() {

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                nextView.setRotationY(90 * (input - 1));
                return input;
            }
        });
        objectAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                removeView(view);
                view.setRotationY(0.0f);
                view.setPivotX(width * .5f);
                nextView.setPivotX(width * .5f);
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator.start();
    }

    @SuppressLint("NewApi")
    private void rotationY(final View view, final View nextView) {
        objectAnimator = AnimatorFactory.rotatioY(new ViewEmbellish(
                this), 0, 180, time);
        objectAnimator.setInterpolator(new Interpolator() {

            boolean change = true;

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                if (change && input > .49) {
                    change = false;
                    removeView(view);
                    view.setRotationY(0.0f);
                    addView(nextView);
                }
                return input > .5f ? 1 + input : input;
            }
        });
        objectAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator.start();
    }

    @SuppressLint("NewApi")
    private void rotationX0(final View view, final View nextView) {
        objectAnimator = AnimatorFactory.rotatioX(new ViewEmbellish(
                view), 0, -90, time);
        view.setPivotY(0.0f);
        nextView.setPivotY(heigth);
        addView(nextView);
        nextView.setRotationX(90);
        objectAnimator.setInterpolator(new Interpolator() {

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                nextView.setRotationX(90 * (1 - input));
                return input;
            }
        });
        objectAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                removeView(view);
                view.setRotationX(0.0f);
                view.setPivotY(heigth * .5f);
                nextView.setPivotY(heigth * .5f);
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator.start();
    }

    @SuppressLint("NewApi")
    private void rotationX(final View view, final View nextView) {
        objectAnimator = AnimatorFactory.rotatioX(new ViewEmbellish(
                this), 0, 180, time);
        objectAnimator.setInterpolator(new Interpolator() {

            boolean change = true;

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                if (change && input > .49) {
                    change = false;
                    removeView(view);
                    view.setRotationX(0.0f);
                    addView(nextView);
                }
                return input > .5f ? 1 + input : input;
            }
        });
        objectAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator.start();
    }

    @SuppressLint("NewApi")
    private void rotation(final View view, final View nextView) {
        objectAnimator = AnimatorFactory.rotation(new ViewEmbellish(
                this), 0, 180, time);
        objectAnimator.setInterpolator(new Interpolator() {

            boolean change = true;

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                if (change && input > .49) {
                    change = false;
                    removeView(view);
                    view.setRotation(0.0f);
                    addView(nextView);
                }
                return input > .5f ? 1 + input : input;
            }
        });
        objectAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator.start();
    }

    @SuppressLint("NewApi")
    private void zoom(final View view, final View nextView) {
        addView(nextView);
        nextView.setScaleX(0.0f);
        nextView.setScaleY(0.0f);
        AnimatorSet set = new AnimatorSet();
        ViewEmbellish embellish = new ViewEmbellish(view);
        ObjectAnimator animatorX = AnimatorFactory.scaleX(embellish, 1.0f,
                0.0f, time);
        ObjectAnimator animatorY = AnimatorFactory.scaleY(embellish, 1.0f,
                0.0f, time);
        set.playTogether(animatorX, animatorY);
        set.setInterpolator(new Interpolator() {

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                nextView.setScaleX(input);
                nextView.setScaleY(input);
                return input;
            }
        });
        set.setDuration(time);
        set.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                removeView(view);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator = set;
        set.start();
    }

    @SuppressLint("NewApi")
    private void zoom0(final View view, final View nextView) {
        addView(nextView);
        nextView.setScaleX(0.0f);
        nextView.setScaleY(0.0f);
        AnimatorSet set = new AnimatorSet();
        ViewEmbellish embellish = new ViewEmbellish(view);
        ObjectAnimator animatorX = AnimatorFactory.scaleX(embellish, 1.0f,
                0.0f, time);
        ObjectAnimator animatorY = AnimatorFactory.scaleY(embellish, 1.0f,
                0.0f, time);
        set.playTogether(animatorX, animatorY);
        set.setInterpolator(new Interpolator() {

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                float width = ShufflViewGroup.this.width * input;
                float heigth = ShufflViewGroup.this.heigth * input;
                view.setTranslationX(-width * .5f);
                view.setTranslationY(-heigth * .5f);
                nextView.setTranslationX((ShufflViewGroup.this.width - width) * .5f);
                nextView.setTranslationY((ShufflViewGroup.this.heigth - heigth) * .5f);
                nextView.setScaleX(input);
                nextView.setScaleY(input);
                return input;
            }
        });
        set.setDuration(time);
        set.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                removeView(view);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                view.setTranslationX(0.0f);
                view.setTranslationY(0.0f);
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator = set;
        set.start();
    }

    @SuppressLint("NewApi")
    private void alpha(final View view, final View nextView) {
        addView(nextView);
        nextView.setAlpha(0.0f);
        objectAnimator = AnimatorFactory.alpha(
                new ViewEmbellish(view), 1.0f, 0.0f, time);
        objectAnimator.setInterpolator(new Interpolator() {

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                nextView.setAlpha(input);
                return input;
            }
        });
        objectAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                removeView(view);
                view.setAlpha(1.0f);
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator.start();
    }

    @SuppressLint("NewApi")
    private void translationX(final View view, final View nextView) {
        // TODO Auto-generated method stub
        addView(nextView);
        nextView.setTranslationX(width);
        objectAnimator = AnimatorFactory.translationX(
                new ViewEmbellish(view), 0, -width, time);
        objectAnimator.setInterpolator(new Interpolator() {

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                nextView.setTranslationX((1 - input) * width);
                return input;
            }
        });
        objectAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                view.setTranslationX(0);
                removeView(view);
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator.start();
    }

    @SuppressLint("NewApi")
    private void translationY(final View view, final View nextView) {
        // TODO Auto-generated method stub
        addView(nextView);
        nextView.setTranslationY(heigth);
        objectAnimator = AnimatorFactory.translationY(
                new ViewEmbellish(view), 0, -heigth, time);
        objectAnimator.setInterpolator(new Interpolator() {

            @Override
            public float getInterpolation(float input) {
                // TODO Auto-generated method stub
                nextView.setTranslationY((1 - input) * heigth);
                return input;
            }
        });
        objectAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                view.setTranslationY(0);
                removeView(view);
                ShufflViewGroup.this.animator = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        objectAnimator.start();
    }
}
