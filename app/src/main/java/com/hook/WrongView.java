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



public class WrongView extends View {
    private final static String TAG = "CheckView";
    private int defaultWidth = 400;
    private int defaultHeigth = 400;
    private float strokeWidth = 4;
    private Path path;
    private Paint paint;
    private boolean show = true;
    private float length = 0;
    private PathMeasure measure;
    private int percent = 0;
    private boolean start = true;

    {
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
    }

    public WrongView(Context context) {
        super(context);
    }

    public WrongView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parasAttribut(context, attrs);
    }

    public WrongView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parasAttribut(context, attrs);
    }

    public WrongView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parasAttribut(context, attrs);
    }

    private void parasAttribut(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
        show = array.getBoolean(R.styleable.SearchView_show, true);
        if (show)
            percent = 100;
        else
            percent = 0;
        strokeWidth = array.getDimension(R.styleable.SearchView_strokeWidth, 4);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(array.getColor(R.styleable.SearchView_color, Color.RED));
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
        path = new Path();
        path.moveTo(width, 0);
        path.lineTo(0, height);
        path.moveTo(0, 0);
        path.lineTo(width, height);
        measure = new PathMeasure(path, false);
        length = 0;
        while (measure.getLength() != 0) {
            length += measure.getLength();
            measure.nextContour();
        }
        measure.setPath(path, false);
        setMeasuredDimension(width, height);
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

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        paint.setStrokeWidth(strokeWidth);
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

    public void show() {
        if (percent != 100)
            return;
        percent = 100;
        setVisibility(VISIBLE);
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
                        } else {
                            break;
                        }
                        sleep(20);
                    } catch (Exception e) {
                        e.printStackTrace();
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
                                    setVisibility(GONE);
                                }
                            });
                            break;
                        }
                        sleep(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getPaddingLeft(), getPaddingTop());
        float len = length * percent / 100;
        Path path1 = new Path();
        if (len > measure.getLength()) {
            len -= measure.getLength();
            measure.nextContour();
            measure.getSegment(len, measure.getLength(), path1, true);
            canvas.drawPath(path1, paint);
        } else {
            measure.getSegment(len, measure.getLength(), path1, true);
            canvas.drawPath(path1, paint);
            measure.nextContour();
            measure.getSegment(0, measure.getLength(), path1, true);
            canvas.drawPath(path1, paint);
        }
        measure.setPath(path, false);
        canvas.restore();
    }
}
