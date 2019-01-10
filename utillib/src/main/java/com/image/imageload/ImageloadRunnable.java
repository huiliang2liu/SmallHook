package com.image.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.image.ImageLoadListener;
import com.image.ImageUtil;
import com.image.transform.ITransform;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 2018/7/5 18:25
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ImageloadRunnable implements Runnable {
    private DisplayImageOptions mDisplayImageOptions;
    private ImageLoader mImageLoader;
    private String mPath;
    private int mErrId;
    private int mDefaultId;
    private int mWidth;
    private int mHeigth;
    private ITransform mTransform;
    private Handler mHandler;
    private Context mContext;
    private ImageLoadListener mListener;

    public ImageloadRunnable(DisplayImageOptions displayImageOptions, ImageLoader imageLoader, ImageLoadListener loadListener, String path, int errId, int defaultId, int width, int heigth, ITransform transform, Context context) {
        mDisplayImageOptions = displayImageOptions;
        mImageLoader = imageLoader;
        mPath = path;
        mErrId = errId;
        mDefaultId = defaultId;
        mWidth = width;
        mHeigth = heigth;
        mTransform = transform;
        mHandler = new Handler(Looper.getMainLooper());
        mContext = context;
        mListener = loadListener;

    }

    @Override
    public void run() {
        if (mDefaultId > 0) {
            try {
                Bitmap bitmap = ImageUtil.src(mHeigth, mWidth, mDefaultId, mContext);
                setBitmap(bitmap,0);
            } catch (Exception e) {

            }
        }
        Bitmap bitmap = mImageLoader.loadImageSync(mPath, mDisplayImageOptions);
        if (bitmap != null) {
            setBitmap(bitmap,1);
            return;
        }
        if (mErrId > 0) {
            try {
                bitmap = ImageUtil.src(mHeigth, mWidth, mErrId, mContext);
                setBitmap(ImageUtil.zoom(mHeigth,mWidth,bitmap),2);
            } catch (Exception e) {

            }
        }

    }

    private void setBitmap( Bitmap bitmap, final int type) {
        if (bitmap == null)
            return;
        final Bitmap tBitmap = mTransform.transform(bitmap);
//        bitmap.recycle();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(type==0)
                    mListener.loadStart(tBitmap);
                else if(type==1)
                    mListener.loaded(tBitmap);
                else if(type==2)
                    mListener.loadFail(tBitmap);
            }
        });
    }
}
