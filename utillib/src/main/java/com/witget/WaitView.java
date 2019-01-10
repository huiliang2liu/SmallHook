package com.witget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hook.com.utillib.R;

public class WaitView extends View {
    private final static String TAG = "WaitView";
    /**
     * 半径差
     */
    private int radiusDifference = 50;
    /**
     * 圆的个数
     */
//    private int radius_size = 0;
    /**
     * 颜色
     */
    private int color = Color.rgb(0xff, 0xa8, 0x00);
    /**
     * 是否初始化
     */
    private boolean init = false;
    /**
     * 圆心 x坐标
     */
    private float cx = 0f;
    /**
     * 圆心 y坐标
     */
    private float cy = 0f;
    /**
     * 屏幕宽度
     */
    private int width = 0;
    /**
     * 屏幕高度
     */
    private int heigth = 0;
    /**
     * 频率
     */
    private int frequency = 20;
    /**
     * 每次的位移
     */
    private int dx = 5;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 环容器
     */
    private List<Loop> loops;
    /**
     * 是否刷新
     */
    private boolean refresh = false;
    /**
     * 最大半径
     */
    private int radius = 0;

    {
        paint = new Paint();
        paint.setAntiAlias(true);// 是否抗锯齿
        paint.setDither(true);// 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        loops = new ArrayList<Loop>();
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    public WaitView(Context context) {
        super(context);
    }

    public WaitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public WaitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // TODO Auto-generated method stub
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WaitView, -1, -1);
        frequency = a.getInt(R.styleable.WaitView_frequency, frequency);
        dx = a.getInt(R.styleable.WaitView_displacement, dx);
        color = a.getColor(R.styleable.WaitView_color, color);
        paint.setColor(color);
        radiusDifference = a.getInt(R.styleable.WaitView_radiusDifference, radiusDifference);
        refresh = a.getBoolean(R.styleable.WaitView_waitViewRefresh, refresh);
        if (refresh)
            new RefreshThread().start();
        a.recycle();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        refresh = false;
    }

    /**
     * 开始刷新
     */
    public void start() {
        refresh = true;
        new RefreshThread().start();
    }

    /**
     * 停止刷新
     */
    public void stop() {
        refresh = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        if (!init) {
            width = getWidth();
            heigth = getHeight();
            cx = width * 0.5f;
            cy = heigth * 0.5f;
            init = true;
            radius = Math.min(width, heigth) >> 1;
//            radius_size = radius / radiusDifference;
        }
        if (width == 0 || heigth == 0) {
            Log.e(TAG, "width or heigth is 0");
            return;
        }
        if (loops == null)
            return;
        synchronized (TAG) {
            for (Loop loop : loops) {
                paint.setAlpha(loop.transparency);
                int outer_radius = loop.outerRadius;
                int inner_radius = loop.innerRadius;
                canvas.drawCircle(cx, cy, outer_radius, paint);
                if (loop.innerRadius > 0) {
                    paint.setAlpha(0);
                    canvas.drawCircle(cx, cy, inner_radius, paint);
                }
            }
        }
    }

    private class RefreshThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            while (refresh) {
                try {
                    synchronized (TAG) {
                        for (int i = 0; i < loops.size(); i++) {
                            Loop loop = loops.get(i);
                            loop.outerRadius += dx;
                            int inner_radius = loop.outerRadius
                                    - radiusDifference;
                            loop.innerRadius = inner_radius > 0 ? inner_radius : 0;
                            int a = (int) ((radius - loop.outerRadius * 1.0f)
                                    / radius * 255);
                            loop.transparency = a >= 0 ? a : 0;
                        }
                        if (loops.size() == 0) {
                            loops.add(new Loop());
                        } else if (loops.get(loops.size() - 1)
                                .innerRadius > 0) {
                            Loop loop = new Loop();
                            loop.outerRadius = (loops.get(loops.size() - 1).innerRadius);
                            int a = (int) ((radius - loop.outerRadius * 1.0f)
                                    / radius * 255);
                            loop.transparency = a;
                            loops.add(loop);
                        }
                        Loop loop = loops.get(0);
                        if (loop.innerRadius > radius)
                            loops.remove(0);
                        postInvalidate();
                    }
                    sleep(frequency);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 环
     */
    private class Loop {
        /**
         * 外半径
         */
        private int outerRadius = 0;
        /**
         * 内半径
         */
        private int innerRadius = 0;
        /**
         * 透明度
         */
        private int transparency = 0;


    }
}
