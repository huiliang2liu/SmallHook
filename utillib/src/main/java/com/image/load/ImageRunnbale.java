package com.image.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.image.ImageLoadListener;
import com.image.ImageUtil;
import com.image.transform.ITransform;

import java.net.URL;

/**
 * 2018/7/6 11:09
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ImageRunnbale implements Runnable {
    private String mPath;
    private Context mContext;
    private int mWidth;
    private int mHeigth;
    private int mErrId;
    private int mDefaultId;
    private int mRes;
    private ITransform mTransform;
    private ImageLoadListener mListener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public ImageRunnbale(String path, int width, int heigth, int errId, int defaultId, Context context, ITransform transform, ImageLoadListener loadListener) {
        mPath = path;
        mWidth = width;
        mHeigth = heigth;
        mErrId = errId;
        mDefaultId = defaultId;
        mContext = context;
        mTransform = transform;
        mListener = loadListener;

    }

    public ImageRunnbale(int res, int width, int heigth, int errId, int defaultId, Context context, ITransform transform, ImageLoadListener loadListener) {
        mRes = res;
        mWidth = width;
        mHeigth = heigth;
        mErrId = errId;
        mDefaultId = defaultId;
        mContext = context;
        mTransform = transform;
        mListener = loadListener;
    }

    @Override
    public void run() {
        if (mDefaultId > 0) {
            try {
                setBitmap(ImageUtil.src(mHeigth, mWidth, mDefaultId, mContext), 0);
            } catch (Exception e) {

            }
        }
        if (mRes > 0) {
            try {
                setBitmap(ImageUtil.src(mHeigth, mWidth, mRes, mContext), 1);
                return;
            } catch (Exception e) {

            }
        } else if (mPath != null && !mPath.isEmpty()) {
            try {
                setBitmap(ImageUtil.inputStream2Bitmap(mHeigth, mWidth, new URL(mPath).openStream()), 1);
            } catch (Exception e) {

            }
        }
        if (mErrId > 0) {
            try {
                setBitmap(ImageUtil.src(mHeigth, mWidth, mErrId, mContext), 2);
            } catch (Exception e) {

            }
        }
    }

    void setBitmap(final Bitmap bitmap, final int type) {
        if (bitmap == null)
            return;
        final Bitmap bitmap1 = mTransform.transform(bitmap);
//        bitmap.recycle();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (type == 0)
                    mListener.loadStart(bitmap);
                else if (type == 1)
                    mListener.loaded(bitmap);
                else if (type == 2)
                    mListener.loadFail(bitmap);
            }
        });
    }
}
