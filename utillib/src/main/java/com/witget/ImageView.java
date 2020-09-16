package com.witget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * com.witget
 * 2018/10/22 11:58
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class ImageView extends android.widget.ImageView {
    public ImageView(Context context) {
        super(context);
    }

    public ImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cycle();
    }

    private void cycle() {
        Drawable drawable = getDrawable();
        if (drawable != null
                && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap1 = bitmapDrawable.getBitmap();
            if (bitmap1 != null
                    && !bitmap1.isRecycled()) {
                bitmap1.recycle();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
