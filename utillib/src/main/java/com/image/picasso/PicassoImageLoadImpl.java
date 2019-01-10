package com.image.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.image.IImageLoad;
import com.image.ImageLoadListener;
import com.image.ImageViewImageLoad;
import com.image.ViewImageLoad;
import com.image.transform.ITransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2018/7/2 10:30
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class PicassoImageLoadImpl implements IImageLoad {
    Picasso mPicasso;
    private Context mContext;
    private ExecutorService executorService;
    private PicassoLoadImage mPicassoLoadImage;
    private List<Runnable> runnables;
    private boolean load = true;

    public PicassoImageLoadImpl(Context context, Bitmap.Config config, ExecutorService executorService) {
        Log.i("PicassoImageLoadImpl", "PicassoImageLoadImpl");
        mContext = context;
        this.executorService = executorService == null ? Executors.newFixedThreadPool(5) : executorService;
        Picasso.Builder builder = new Picasso.Builder(context);
        //builder.memoryCache(null);
        builder.defaultBitmapConfig(config);
        //配置线程池
        //    ExecutorService executorService = Executors.newFixedThreadPool(8);
        builder.executor(this.executorService);
        //构造一个Picasso
        mPicasso = builder.build();
        // 设置全局单列instance
        Picasso.setSingletonInstance(mPicasso);
    }

//    public PicassoImageLoadImpl(Context context, Downloader downloader, Cache cache, Bitmap.Config bitmapConfig, ExecutorService executorService) {
//        mPicassoLoadImage = new PicassoLoadImage(context, downloader, cache, bitmapConfig, executorService);
//    }
//
//    public PicassoImageLoadImpl(Context context, Downloader downloader, Bitmap.Config bitmapConfig, ExecutorService executorService) {
//        mPicassoLoadImage = new PicassoLoadImage(context, downloader, bitmapConfig, executorService);
//    }
//
//    public PicassoImageLoadImpl(Context context, Downloader downloader, ExecutorService executorService) {
//        mPicassoLoadImage = new PicassoLoadImage(context, downloader, executorService);
//    }
//
//    public PicassoImageLoadImpl(Context context, Downloader downloader) {
//        mPicassoLoadImage = new PicassoLoadImage(context, downloader);
//    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, int res) {
        set(errId, defaultId, width, heigth, new ViewImageLoad(view), Picasso.with(mContext).load(res), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, File file) {
        set(errId, defaultId, width, heigth, new ViewImageLoad(view), Picasso.with(mContext).load(file), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URI uri) {
        set(errId, defaultId, width, heigth, new ViewImageLoad(view), Picasso.with(mContext).load(uri.getPath()), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URL url) {
        set(errId, defaultId, width, heigth, new ViewImageLoad(view), Picasso.with(mContext).load(url.getPath()), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, String path) {
        set(errId, defaultId, width, heigth, new ViewImageLoad(view), Picasso.with(mContext).load(path), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, int res) {
        set(errId, defaultId, width, heigth, new ImageViewImageLoad(view), Picasso.with(mContext).load(res), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, File file) {
        set(errId, defaultId, width, heigth, new ImageViewImageLoad(view), Picasso.with(mContext).load(file), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URI uri) {
        set(errId, defaultId, width, heigth, new ImageViewImageLoad(view), Picasso.with(mContext).load(uri.getPath()), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URL url) {
        set(errId, defaultId, width, heigth, new ImageViewImageLoad(view), Picasso.with(mContext).load(url.getPath()), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, String path) {
        set(errId, defaultId, width, heigth, new ImageViewImageLoad(view), Picasso.with(mContext).load(path), transform);
    }

    private void set(int errId, int defaultId, int width, int heigth, ImageLoadListener target, RequestCreator build, ITransform transform) {
        Runnable runnable = new PicassoRunnable(errId, defaultId, width, heigth, target, build, transform, mContext);
        synchronized (PicassoImageLoadImpl.class) {
            if (load)
                executorService.submit(runnable);
            else {
                runnables.add(runnable);
            }
        }

    }

    @Override
    public void setLoad(boolean load) {
        synchronized (PicassoImageLoadImpl.class) {
            this.load = load;
            if (load)
                runnables = new ArrayList<>();
            else {
                if (runnables == null || runnables.size() <= 0) {
                    runnables = null;
                    return;
                }
                int size = runnables.size() - 1;
                for (int i = size; i > -1; i--) {
                    executorService.submit(runnables.get(i));
                }
                runnables.clear();
                runnables = null;
            }
        }

    }
}
