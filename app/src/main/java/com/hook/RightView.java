package com.hook;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * com.hook
 * 2018/10/11 16:03
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class RightView extends View {
    private int defaultWidth = 400;
    private int defaultHeight = (int) (400 * 1.62f);
    private Path path;
    private Paint paint;
    private int percent = 0;
    private PathMeasure measure;
    private float length;
    private boolean start = true;
    private boolean show = true;
    private float strokeWidth=8;

    {
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.RED);
    }

    public RightView(Context context) {
        super(context);
    }

    public RightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parasAttribute(context,attrs);
    }

    public RightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parasAttribute(context,attrs);
    }

    public RightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parasAttribute(context,attrs);
    }

    private void parasAttribute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
        show = array.getBoolean(R.styleable.SearchView_show, true);
        if (show)
            percent = 100;
        else
            percent = 0;
        strokeWidth=array.getDimension(R.styleable.SearchView_strokeWidth, 4);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(array.getColor(R.styleable.SearchView_color, Color.RED));
        array.recycle();
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setStrokeWidth(float strokeWidth) {
        paint.setStrokeWidth(strokeWidth);
        this.strokeWidth=strokeWidth;
        invalidate();
    }

    public void setColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start = true;
        if (show)
            show();
        else
            hide();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        start = false;
    }

    private void hide() {
        if (percent != 0)
            return;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (start) {
                        if (percent < 100) {
                            percent += 5;
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    invalidate();
                                }
                            });
                        } else {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    setVisibility(GONE);
                                }
                            });
                            break;
                        }
                        sleep(20);
                    }
                } catch (Exception e) {

                }
            }
        }.start();
    }

    private void show() {
        setVisibility(VISIBLE);
        if (percent != 100)
            return;
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (start) {
                    try {
                        if (percent > 0) {
                            percent -= 5;
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    invalidate();
                                }
                            });
                        } else
                            break;
                        sleep(20);
                    } catch (Exception e) {

                    }
                }
            }
        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(defaultWidth, widthMeasureSpec);
        int heigth = measureHeight(defaultHeight, heightMeasureSpec);
        if (width * 1.62f > heigth)
            width = (int) (heigth / 1.62f);
        else if (width < heigth)
            heigth = (int) (width * 1.62f);
        float move=(float) (strokeWidth/4*Math.pow(2,.5));
        path.moveTo(move,heigth * .5f);
        path.lineTo(width * .5f, heigth-move);
        path.lineTo(width-move, 0);
        measure = new PathMeasure(path, false);
        length = measure.getLength();
        setMeasuredDimension(width, heigth);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int width = 0;
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                width = defaultWidth + getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.EXACTLY:
                width = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                width = Math.max(defaultWidth, specSize);
        }
        return width;
    }


    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int heigth = 0;
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                heigth = defaultHeight + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                heigth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                heigth = Math.max(defaultHeight, specSize);
                break;
        }
        return heigth;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getPaddingLeft(), getPaddingTop());
        Path path1 = new Path();
        measure.getSegment(length * percent / 100, length, path1, true);
        canvas.drawPath(path1, paint);
        canvas.restore();
    }
}
