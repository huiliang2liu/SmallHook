package com.image.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.image.IImageLoad;
import com.image.ImageLoadListener;
import com.image.ImageViewImageLoad;
import com.image.ImageUtil;
import com.image.ViewImageLoad;
import com.image.transform.ITransform;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 2018/7/3 11:08
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ImageloadImageLoadImpl implements IImageLoad {
    private ImageloadManage loader;
    private ExecutorService executorService;
    private Context mContext;
    private boolean load = true;
    private List<Runnable> runnables;

    public ImageloadImageLoadImpl(Context context, Bitmap.Config bitmapConfig, boolean cacheDis, long cacheDisSize, String file, ExecutorService executorService) {
        Log.i("ImageloadImageLoadImpl", "ImageloadImageLoadImpl");
        loader = new ImageloadManage(context, bitmapConfig, false, 0, cacheDis, cacheDisSize, file);
        this.executorService = executorService;
        mContext = context;
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, int res) {
        try {
            if (res > 0) {
                view.setBackgroundDrawable(new BitmapDrawable(transform.transform(ImageUtil.src(heigth, width, res, view.getContext()))));
                return;
            }

        } catch (Exception e) {

        }
        try {
            if (errId > 0)
                view.setBackgroundDrawable(new BitmapDrawable(transform.transform(ImageUtil.src(heigth, width, errId, view.getContext()))));
        } catch (Exception e) {

        }

    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, File file) {
        load(errId, defaultId, width, heigth, new ViewImageLoad(view), transform, file.getAbsolutePath());
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URI uri) {
        load(errId, defaultId, width, heigth, new ViewImageLoad(view), transform, uri.getPath());
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URL url) {
        load(errId, defaultId, width, heigth, new ViewImageLoad(view), transform, url.getPath());
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, String path) {
        load(errId, defaultId, width, heigth, new ViewImageLoad(view), transform, path);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, int res) {
        try {
            if (res >= 0) {
                view.setImageDrawable(new BitmapDrawable(transform.transform(ImageUtil.src(heigth, width, res, view.getContext()))));
                return;
            }

        } catch (Exception e) {

        }
        if (errId >= 0)
            try {
                view.setImageDrawable(new BitmapDrawable(transform.transform(ImageUtil.src(heigth, width, errId, view.getContext()))));
            } catch (Exception e) {

            }
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, File file) {
        load(errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform, file.getAbsolutePath());
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URI uri) {
        load(errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform, uri.getPath());
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URL url) {
        load(errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform, url.getPath());
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, String path) {
        load(errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform, path);
    }

    public void load(int errId, int defaultId, int width, int heigth, ImageLoadListener aware, ITransform transform, String path) {
        Runnable runnable = new ImageloadRunnable(loader.options(errId, defaultId, transform), loader.mImageLoader, aware, path, errId, defaultId, width, heigth, transform, mContext);
        synchronized (ImageloadImageLoadImpl.class) {
            if (load)
                executorService.submit(runnable);
            else {
                runnables.add(runnable);
            }
        }

    }

    @Override
    public void setLoad(boolean load) {
        synchronized (ImageloadImageLoadImpl.class) {
            this.load = load;
            if (load) {
                if (runnables == null || runnables.size() <= 0) {
                    runnables = null;
                    return;
                }
                int size=runnables.size()-1;
                for (int i=size;i>-1;i--) {
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
