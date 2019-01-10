package com.image.load;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
 * 2018/7/6 11:34
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ImageImageLoadImpl implements IImageLoad {
    private ExecutorService mService;
    private Context mContext;
    private boolean load = true;
    private List<Runnable> runnables;

    public ImageImageLoadImpl(ExecutorService service, Context context) {
        Log.i("ImageImageLoadImpl","ImageImageLoadImpl");
        mService = service;
        mContext = context;
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, int res) {
        load(errId, defaultId, width, heigth, new ViewImageLoad(view), transform, res);
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
        load(errId, defaultId, width, heigth, new ImageViewImageLoad(view), transform, res);
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

    void load(int errId, int defaultId, int width, int heigth, ImageLoadListener loadListener, ITransform transform, Object path) {
        Runnable runnable = null;
        if (path instanceof String)
            runnable = new ImageRunnbale((String) path, width, heigth, errId, defaultId, mContext, transform, loadListener);
        if (path instanceof Integer)
            runnable = new ImageRunnbale((int) path, width, heigth, errId, defaultId, mContext, transform, loadListener);
        if (runnable == null)
            return;
        synchronized (ImageImageLoadImpl.class) {
            if (load)
                mService.submit(runnable);
            else{
                runnables.add(runnable);
            }
        }

    }

    @Override
    public void setLoad(boolean load) {

        synchronized (ImageImageLoadImpl.class) {
            this.load = load;
            if (load) {
                if (runnables == null || runnables.size() <= 0) {
                    runnables = null;
                    return;
                }
                int size=runnables.size()-1;
                for (int i=size;i>-1;i--) {
                    mService.submit(runnables.get(i));
                }
                runnables.clear();
                runnables = null;
            } else {
                runnables = new ArrayList<>();
            }
        }


    }
}
