package com.hook;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.utils.LogUtil;

import java.util.ArrayList;


public class SearchView extends View {
    private final static String TAG = "Search";
    private int defaultWidth = 400;
    private int defaultHeigth = 400;
    private Path path;
    private Paint mPaint;
    private Path mPath;
    private int percent = 0;
    private float length;
    private PathMeasure measure;
    private boolean start = true;
    private float strokeWidth = 4;
    private boolean show = true;

    {
        path = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parasAttribute(context, attrs);
    }

    public SearchView(Context context) {
        super(context);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parasAttribute(context, attrs);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parasAttribute(context, attrs);
    }

    private void parasAttribute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
        show = array.getBoolean(R.styleable.SearchView_show, true);
        if (show)
            percent = 100;
        else
            percent = 0;
        mPaint.setColor(array.getColor(R.styleable.SearchView_color, Color.RED));
        strokeWidth = array.getDimension(R.styleable.SearchView_strokeWidth, 4);
        mPaint.setStrokeWidth(strokeWidth);
        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(defaultWidth, widthMeasureSpec);
        int height = measureHeight(defaultHeigth, heightMeasureSpec);
        if (width > height)
            width = height;
        else if (width < height)
            height = width;
        float sx = width * 1.0f / defaultWidth;
        float harfStrokeWidth = strokeWidth * .5f;
        RectF rectF = new RectF(harfStrokeWidth, harfStrokeWidth, 300 * sx + harfStrokeWidth, 300 * sx + harfStrokeWidth);
        path.addArc(rectF, 45, 180);
        path.addArc(rectF, 225, 180);
        path.lineTo(400 * sx, 400 * sx);
        mPath = path;
        LogUtil.i(TAG, "sx=" + sx);
        measure = new PathMeasure(mPath, false);
        length = 0;
        float len = measure.getLength();
        while (len != 0) {
            length += len;
            measure.nextContour();
            len = measure.getLength();
        }
        LogUtil.i(TAG, "length" + length);
        measure.setPath(mPath, false);
        setMeasuredDimension(width, height);
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        mPaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start = true;
        LogUtil.i(TAG, "onAttachedToWindow");
        if (show)
            show();
        else
            hide();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtil.i(TAG, "onDetachedFromWindow");
        start = false;
    }

    public void show() {
        if (percent != 100)
            return;
        setVisibility(View.VISIBLE);
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

    public void hide() {
        if (percent != 0)
            return;
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (start) {
                    try {
                        if (percent <= 100) {
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
                                    setVisibility(View.GONE);
                                }
                            });
                            break;
                        }
                        sleep(20);
                    } catch (Exception e) {

                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getPaddingLeft(), getPaddingRight());
        Path path = new Path();
        float len = length * percent / 100;
        if (len > measure.getLength()) {
            len -= measure.getLength();
            measure.nextContour();
            measure.getSegment(len, measure.getLength(), path, true);
            canvas.drawPath(path, mPaint);
        } else {
            measure.getSegment(0, measure.getLength(), path, true);
            canvas.drawPath(path, mPaint);
            measure.nextContour();
            measure.getSegment(0, measure.getLength(), path, true);
            canvas.drawPath(path, mPaint);
        }
        measure.setPath(mPath, false);
        canvas.restore();
    }

}
