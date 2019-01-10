package com.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;


/**
 * 2018/7/6 10:39
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ViewImageLoad extends ImageLoadListenerImpl<View> {
    public ViewImageLoad(View view){
        super(view);
    }
    @Override
    protected void setBitmap(View view, Bitmap bitmap) {
     view.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }
}
