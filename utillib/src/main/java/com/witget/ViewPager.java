package com.witget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * com.witget
 * 2019/1/2 14:59
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class ViewPager extends android.support.v4.view.ViewPager {
    private boolean isCanScroll = true; // 是否需要滑动

    public ViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * 设置是否需要滑动
     *
     * @param isCanScroll
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isCanScroll) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isCanScroll) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }
    }
}
