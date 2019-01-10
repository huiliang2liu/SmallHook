package com.witget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.animation.AnimatorFactory;
import com.animation.ViewEmbellish;
import com.utils.LogUtil;
import com.view.FloatView;


/**
 * com.witget
 * 2018/10/22 12:35
 * instructions：放大到全屏，缩小到初始位置
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class FullScreenView extends FrameLayout {
    private final static String TAG = "FullScreenView";
    private ViewGroup mViewGroup;
    private ViewGroup.LayoutParams lp;
    private FloatView mFloatView;
    private View mView;
    private int widthPixels;
    private int heightPixels;
    private int left;
    private int right;
    private int top;
    private int bottom;
    private boolean end = false;
    private int statusBarHeight = 0;
    private Animator animator;

    public FullScreenView(@NonNull FloatView floatView) {
        super(floatView.getActivity());
        DisplayMetrics dm = getResources().getDisplayMetrics();
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        mFloatView = floatView;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        if (statusBarHeight < 0)
            statusBarHeight = 0;
    }

    public void enter(@NonNull View view) {
        mViewGroup = (ViewGroup) view.getParent();
        mView = view;
        view.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(animator!=null)
            animator.cancel();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private void init() {
        lp = mView.getLayoutParams();
        int[] location = new int[2];
        mView.getLocationOnScreen(location);
        left = location[0];
        top = location[1];
        right = left + mView.getWidth();
        bottom = top + mView.getHeight();
        LogUtil.i(TAG, "left=" + left + " right=" + right + " top=" + top + " bottom=" + bottom);
        if (mViewGroup != null)
            mViewGroup.removeView(mView);
        addView(mView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        LayoutParams lp = new LayoutParams(right - left, bottom - top);
        lp.leftMargin = left;
        lp.topMargin = top;
        FullScreenView.this.setLayoutParams(lp);
        mFloatView.addView(FullScreenView.this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (end)
                    exit();
            }
        });
        enter();
    }

    private void enter() {
        LogUtil.i(TAG, "enter");
        end = false;
        AnimatorSet enter = new AnimatorSet();
        ViewEmbellish embellish = new ViewEmbellish(this);
        ObjectAnimator translationX = AnimatorFactory.translationX(embellish, 0, -left, 500);
        ObjectAnimator translationY = AnimatorFactory.translationY(embellish, -statusBarHeight, -top, 500);
        ObjectAnimator width = AnimatorFactory.width(embellish, right - left, widthPixels, 500);
        ObjectAnimator height = AnimatorFactory.height(embellish, bottom - top, heightPixels, 500);
        enter.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                end = true;
            }
        });
        enter.playTogether(translationX, translationY, width, height);
        enter.setDuration(500);
        animator=enter;
        enter.start();
    }

    private void exit() {
        LogUtil.i(TAG, "exit");
        AnimatorSet exit = new AnimatorSet();
        ViewEmbellish embellish = new ViewEmbellish(this);
        ObjectAnimator translationX = AnimatorFactory.translationX(embellish, -left, 0, 500);
        ObjectAnimator translationY = AnimatorFactory.translationY(embellish, -top, -statusBarHeight, 500);
        ObjectAnimator width = AnimatorFactory.width(embellish, widthPixels, right - left, 500);
        ObjectAnimator height = AnimatorFactory.height(embellish, heightPixels, bottom - top, 500);
        exit.playTogether(translationX, translationY, width, height);
        exit.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtil.i(TAG, "onAnimationEnd");
                removeView(mView);
                if (mViewGroup != null)
                    mViewGroup.addView(mView, lp);
                mFloatView.remove(FullScreenView.this);
            }
        });
        exit.setDuration(500);
        animator=exit;
        exit.start();
    }
}
