package com.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 2018/7/6 10:42
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ImageViewImageLoad extends ImageLoadListenerImpl<ImageView>{
    public ImageViewImageLoad(ImageView imageView) {
        super(imageView);
    }

    @Override
    protected void setBitmap(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
