package com.witget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.image.ImageUtil;
import com.utils.LogUtil;

import hook.com.utillib.R;

/**
 * com.witget.surface
 * 2019/1/9 10:37
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class StripClothes extends View {
    private static final String TAG = "StripClothes";
    private static final long LONG_CLICK_TIME = 3000;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Paint mPaint;
    private boolean move = false;
    private long downTime = 0;
    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
    }

    public StripClothes(Context context) {
        super(context);
    }

    public StripClothes(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StripClothes(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public StripClothes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.StripClothes, -1, 0);
        int image1 = a.getResourceId(R.styleable.StripClothes_imag1, -1);
        if (image1 != -1)
            setImage1(image1);
        int image2 = a.getResourceId(R.styleable.StripClothes_imag2, -1);
        if (image2 != -1)
            setImage2(image2);
        a.recycle();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.onClickListener = l;
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        this.onLongClickListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.e(TAG, "onTouchEvent");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downTime = System.currentTimeMillis();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            move = true;
            int x = (int) event.getX();
            int y = (int) event.getY();
            for (int i = -19; i < 20; i++) {
                for (int j = -19; j < 20; j++) {
                    try {
                        mBitmap2.setPixel(x + i, y + j, Color.TRANSPARENT);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
            postInvalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!move) {
                if (System.currentTimeMillis() - downTime >= LONG_CLICK_TIME) {
                    if (onLongClickListener != null)
                        onLongClickListener.onLongClick(this);
                } else if (onClickListener != null)
                    onClickListener.onClick(this);
            }
            move = false;
        }
        return true;
    }

    public void setImage1(@DrawableRes int image1) {
        setImage1(ImageUtil.src(image1, getContext()));
    }

    public void setImage1(Bitmap image1) {
        this.mBitmap1 = image1;
    }

    public void setImage2(@DrawableRes int image2) {
        setImage2(ImageUtil.src(image2, getContext()));
    }

    public void setImage2(Bitmap image2) {
        this.mBitmap2 = Bitmap.createBitmap(image2.getWidth(), image2.getHeight(), image2.getConfig());
        Canvas canvas = new Canvas(this.mBitmap2);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(image2, new Matrix(), paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap1 == null && mBitmap2 == null)
            return;
        if (mBitmap1 != null)
            canvas.drawBitmap(mBitmap1, 0, 0, mPaint);
        if (mBitmap2 != null)
            canvas.drawBitmap(mBitmap2, 0, 0, mPaint);
    }
}
