package com.image.transform;

import android.graphics.Bitmap;

import com.image.ImageUtil;

/**
 * 2018/7/4 11:19
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class WarmthFilterTransform implements ITransform {
    private int mCenterX;
    private int mCenterY;

    public WarmthFilterTransform() {
        this(0, 0);
    }

    public WarmthFilterTransform(int centerX, int centerY) {
        mCenterX = centerX;
        mCenterY = centerY;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        if (mCenterY < 0 || mCenterY > bitmap.getHeight())
            mCenterY = bitmap.getHeight() >> 1;
        if (mCenterX < 0 || mCenterX > bitmap.getWidth())
            mCenterX = bitmap.getWidth() >> 1;
        return ImageUtil.warmthFilter(bitmap, mCenterX, mCenterY);
    }
}
