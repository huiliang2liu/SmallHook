package com.witget.surface;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

/**
 * com.witget.surface
 * 2019/1/8 17:48
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public abstract class SurfaceView extends android.view.SurfaceView implements SurfaceHolder.Callback {
    protected SurfaceHolder mSurfaceHolder;

    {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setZOrderOnTop(true);
        setKeepScreenOn(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setFocusableInTouchMode(true);
    }

    public SurfaceView(Context context) {
        super(context);
    }

    public SurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        created();
    }

    abstract void created();

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    protected final Canvas drawBoard() {
        return mSurfaceHolder.lockCanvas();
    }

    protected final Canvas drawBoard(Rect dirty) {
        return mSurfaceHolder.lockCanvas(dirty);
    }

    protected final Canvas drawBoard(int left, int top, int right, int bottom) {
        return drawBoard(new Rect(left, top, right, bottom));
    }

    protected final void post(Canvas canvas) {
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }
}
