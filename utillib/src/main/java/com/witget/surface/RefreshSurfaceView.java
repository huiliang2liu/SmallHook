package com.witget.surface;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import hook.com.utillib.R;

/**
 * com.witget.surface
 * 2019/1/8 18:05
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public abstract class RefreshSurfaceView extends SurfaceView implements Runnable {
    private boolean refresh = false;
    protected long refreshTime = 50;

    public RefreshSurfaceView(Context context) {
        super(context);
    }

    public RefreshSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RefreshSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public RefreshSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RefreshSurfaceView, -1, -1);
        refresh = a.getBoolean(R.styleable.RefreshSurfaceView_refresh, true);
        a.recycle();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        refresh = false;
    }

    public void stop() {
        refresh = false;
    }

    public void start() {
        refresh = true;
        new Thread(this).start();
    }

    @Override
    void created() {
        if (refresh) {
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        while (refresh) {
            long startTime = System.currentTimeMillis();
            refresh();
            long endTime = System.currentTimeMillis();
            long sleepTime = refreshTime - (endTime - startTime);
            if (sleepTime > 0)
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    abstract void refresh();
}
