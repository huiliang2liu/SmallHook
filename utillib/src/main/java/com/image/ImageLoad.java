package com.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;

import com.image.transform.ITransform;
import com.image.transform.NoTransform;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * com.image
 * 2018/11/1 11:03
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public interface ImageLoad extends IImageLoad, IImageLoad2 {
    class Build {
        private Context context;
        private Bitmap.Config bitmapConfig;
        private boolean cacheDis = true;
        private long cacheDisSize = 500 * 1024 * 1024;
        private String file = "";
        private int errId = 0;
        private int defaultId = 0;
        private ITransform transform;

        public Build context(Context context) {
            this.context = context;
            return this;
        }

        public Build config(Bitmap.Config config) {
            bitmapConfig = config;
            return this;
        }

        public Build cache(boolean cache) {
            cacheDis = cache;
            return this;
        }

        public Build cacheSize(long size) {
            cacheDisSize = size;
            return this;
        }

        public Build file(String file) {
            this.file = file;
            return this;
        }

        public Build error(@IdRes int errId) {
            this.errId = errId;
            return this;
        }

        public Build defaultId(@IdRes int getDefaultId) {
            this.defaultId = defaultId;
            return this;
        }

        public Build transform(ITransform transform) {
            this.transform = transform;
            return this;
        }

        public ImageLoad buidle() {
            if (context == null)
                throw new NullPointerException("context is null");
            if (bitmapConfig == null)
                bitmapConfig = Bitmap.Config.RGB_565;
            if (transform == null)
                transform = new NoTransform();
            return (ImageLoad) Proxy.newProxyInstance(ImageLoad.class.getClassLoader(), new Class[]{ImageLoad.class}, new InvocationHandler() {
                ImageLoad imageLoad = new ImageLoadImpl(context.getApplicationContext(), bitmapConfig, cacheDis, cacheDisSize, file, errId, defaultId, transform);

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(imageLoad, args);
                }
            });
        }
    }
}
