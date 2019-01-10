package com.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.image.glide.GlideImageLoadImpl;
import com.image.imageload.ImageloadImageLoadImpl;
import com.image.load.ImageImageLoadImpl;
import com.image.picasso.PicassoImageLoadImpl;
import com.image.transform.ITransform;
import com.image.transform.NoTransform;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2018/7/3 18:21
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

 class ImageLoadImpl implements ImageLoad {
    IImageLoad mImageLoad;
    private int mErrId;
    private int mDefaultId;
    private ITransform mTransform;
    private ExecutorService executorService;

    public ImageLoadImpl(Context context, Bitmap.Config bitmapConfig, boolean cacheDis, long cacheDisSize, String file, int errId, int defaultId, ITransform transform) {
        if (context == null)
            throw new NullPointerException("context is null");
        if (bitmapConfig == null)
            bitmapConfig = Bitmap.Config.ARGB_4444;
        if (transform == null)
            transform = new NoTransform();
        mErrId = errId;
        mDefaultId = defaultId;
        mTransform = transform;
        executorService = Executors.newFixedThreadPool(5);
        if (file == null || file.isEmpty()) {
            file = context.getCacheDir().getAbsolutePath() + File.separator + "image";
        }
        if (isClass("com.bumptech.glide.Glide"))
            mImageLoad = new GlideImageLoadImpl(context, cacheDisSize, file, bitmapConfig, executorService);
        else if (isClass("com.nostra13.universalimageloader.core.ImageLoader"))
            mImageLoad = new ImageloadImageLoadImpl(context, bitmapConfig, cacheDis, cacheDisSize, file, executorService);
        else if (isClass("com.squareup.picasso.Picasso"))
            mImageLoad = new PicassoImageLoadImpl(context, bitmapConfig, executorService);
        else
            mImageLoad = new ImageImageLoadImpl(executorService, context);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, int res) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, res);
    }

    @Override
    public void load(int width, int heigth, View view, ITransform transform, int res) {
        load(mErrId, mDefaultId, width, heigth, view, transform, res);
    }

    @Override
    public void load(View view, ITransform transform, int res) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, res);
    }

    @Override
    public void load(View view, int res) {
        load(view, mTransform, res);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, File file) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, file);
    }

    @Override
    public void load(int width, int heigth, View view, ITransform transform, File file) {
        load(mErrId, mDefaultId, width, heigth, view, transform, file);
    }

    @Override
    public void load(View view, ITransform transform, File file) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, file);
    }

    @Override
    public void load(View view, File file) {
        load(view, mTransform, file);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URI uri) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, uri);
    }

    @Override
    public void load(int width, int heigth, View view, ITransform transform, URI uri) {
        load(mErrId, mDefaultId, width, heigth, view, transform, uri);
    }

    @Override
    public void load(View view, ITransform transform, URI uri) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, uri);
    }

    @Override
    public void load(View view, URI uri) {
        load(view, mTransform, uri);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URL url) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, url);
    }

    @Override
    public void load(int width, int heigth, View view, ITransform transform, URL url) {
        load(mErrId, mDefaultId, width, heigth, view, transform, url);
    }

    @Override
    public void load(View view, ITransform transform, URL url) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, url);
    }

    @Override
    public void load(View view, URL url) {
        load(view, mTransform, url);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, String path) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, path);
    }

    @Override
    public void load(int width, int heigth, View view, ITransform transform, String path) {
        load(mErrId, mDefaultId, width, heigth, view, transform, path);
    }

    @Override
    public void load(View view, ITransform transform, String path) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, path);
    }

    @Override
    public void load(View view, String path) {
        load(view, mTransform, path);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, int res) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, res);
    }

    @Override
    public void load(int width, int heigth, ImageView view, ITransform transform, int res) {
        load(mErrId, mDefaultId, width, heigth, view, transform, res);
    }

    @Override
    public void load(ImageView view, ITransform transform, int res) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, res);
    }

    @Override
    public void load(ImageView view, int res) {
        load(view, mTransform, res);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, File file) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, file);
    }

    @Override
    public void load(int width, int heigth, ImageView view, ITransform transform, File file) {
        load(mErrId, mDefaultId, width, heigth, view, transform, file);
    }

    @Override
    public void load(ImageView view, ITransform transform, File file) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, file);
    }

    @Override
    public void load(ImageView view, File file) {
        load(view, mTransform, file);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URI uri) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, uri);
    }

    @Override
    public void load(int width, int heigth, ImageView view, ITransform transform, URI uri) {
        load(mErrId, mDefaultId, width, heigth, view, transform, uri);
    }

    @Override
    public void load(ImageView view, ITransform transform, URI uri) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, uri);
    }

    @Override
    public void load(ImageView view, URI uri) {
        load(view, mTransform, uri);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URL url) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, url);
    }

    @Override
    public void load(int width, int heigth, ImageView view, ITransform transform, URL url) {
        load(mErrId, mDefaultId, width, heigth, view, transform, url);
    }

    @Override
    public void load(ImageView view, ITransform transform, URL url) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, url);
    }

    @Override
    public void load(ImageView view, URL url) {
        load(view, mTransform, url);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, String path) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, path);
    }

    @Override
    public void load(int width, int heigth, ImageView view, ITransform transform, String path) {
        load(mErrId, mDefaultId, width, heigth, view, transform, path);
    }

    @Override
    public void load(ImageView view, ITransform transform, String path) {
        load(ImageUtil.getViewWidth(view, 0), ImageUtil.getViewHeight(view, 0), view, transform, path);
    }

    @Override
    public void load(ImageView view, String path) {
        load(view, mTransform, path);
    }

    @Override
    public void setLoad(boolean load) {
        if (mImageLoad != null)
            mImageLoad.setLoad(load);
    }

    private boolean isClass(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
