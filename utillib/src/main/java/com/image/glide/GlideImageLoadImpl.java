package com.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.image.IImageLoad;
import com.image.ImageLoadListener;
import com.image.ImageViewImageLoad;
import com.image.ViewImageLoad;
import com.image.transform.ITransform;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


/**
 * 2018/7/3 10:31
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class GlideImageLoadImpl implements IImageLoad {
    private Context mContext;
    private ExecutorService executorService;
    private List<Runnable> runnables;
    private boolean load = true;

    public GlideImageLoadImpl(Context context, long size, String path, Bitmap.Config bitmapConfig, ExecutorService executorService) {
        Log.i("GlideImageLoadImpl", "GlideImageLoadImpl");
        mContext = context;
        new GlideGlideModule(size, path, bitmapConfig).registerComponents(context, Glide.get(context));
        this.executorService = executorService;
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, int res) {
        load(Glide.with(mContext).load(res), errId, defaultId, width, heigth, new ViewImageLoad(view), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, File file) {
        load(Glide.with(mContext).load(file), errId, defaultId, width, heigth, new ViewImageLoad(view), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URI uri) {
        load(Glide.with(mContext).load(uri), errId, defaultId, width, heigth, new ViewImageLoad(view), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URL url) {
        try{
            load(Glide.with(mContext).load(url.toURI()), errId, defaultId, width, heigth, new ViewImageLoad(view), transform);
        }catch (Exception e){

        }
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, String path) {
        load(Glide.with(mContext).load(path), errId, defaultId, width, heigth, new ViewImageLoad(view), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, int res) {
        load(Glide.with(mContext).load(res), errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, File file) {
        load(Glide.with(mContext).load(file), errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URI uri) {
        load(Glide.with(mContext).load(uri), errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URL url) {
       try{
           load(Glide.with(mContext).load(url.toURI()), errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform);
       }catch (Exception e){

       }
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, String path) {
        load(Glide.with(mContext).load(path), errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform);
    }

    private void load(DrawableTypeRequest request, int errId, int defaultId, int width, int heigth, ImageLoadListener target, ITransform transform) {
//        request.bitmapTransform(new GlideTransform(mContext,transform)).diskCacheStrategy(DiskCacheStrategy.ALL).error(errId).placeholder(defaultId).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(width,heigth);
        Runnable runnable = new GlideRunnable(errId, defaultId, width, heigth, request.asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).error(errId).placeholder(defaultId).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(width, heigth), mContext, transform, target);
        synchronized (GlideImageLoadImpl.class) {
            if (load)
                executorService.submit(runnable);
            else {
                runnables.add(runnable);
            }


        }

    }

    @Override
    public void setLoad(boolean load) {
        synchronized (GlideImageLoadImpl.class) {
            this.load = load;
            if (load) {
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
            } else {
                runnables = new ArrayList<>();
            }
        }

    }
}
