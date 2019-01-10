package com.image;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;


/**
 * 2018/7/6 10:31
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

 abstract class ImageLoadListenerImpl<T> implements ImageLoadListener {
    private WeakReference<T> reference;
    public ImageLoadListenerImpl(T t){
      reference=new WeakReference<T>(t);
    }
    @Override
    public final void loadStart(Bitmap bitmap) {
        bitmap(bitmap);
    }

    @Override
    public final void loadFail(Bitmap bitmap) {
        bitmap(bitmap);
    }

    @Override
    public final void loaded(Bitmap bitmap) {
        bitmap(bitmap);
    }

    private void bitmap(Bitmap bitmap){
        T t=reference.get();
        if(t==null)
            return;
        setBitmap(t,bitmap);
    }
   protected  abstract void setBitmap(T t,Bitmap bitmap);
}
