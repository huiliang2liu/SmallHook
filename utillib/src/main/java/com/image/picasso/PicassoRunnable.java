package com.image.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;

import com.image.ImageLoadListener;
import com.image.ImageUtil;
import com.image.transform.ITransform;
import com.squareup.picasso.RequestCreator;



/**
 * 2018/7/5 9:39
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

class PicassoRunnable implements Runnable {
    private final static String TAG = "PicassoRunnable";
    private int mErrId;
    private int mDefaultId;
    private int mWidth;
    private int mHeigth;
    private ImageLoadListener mListener;
    private RequestCreator mBuild;
    private ITransform mTransform;
    private Handler mHandler;
    private Context mContext;

    public PicassoRunnable(int errId, int defaultId, int width, int heigth, ImageLoadListener loadListener, RequestCreator build, ITransform transform, Context context) {
        mErrId = errId;
        mDefaultId = defaultId;
        mWidth = width;
        mHeigth = heigth;
        mListener = loadListener;
        mBuild = build;
        mTransform = transform;
        mContext = context;
        mHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public void run() {
        if (mDefaultId > 0)

            try {
                setBitmap(ImageUtil.src(mHeigth, mWidth, mDefaultId, mContext), 0);
            } catch (Exception e) {

            }
        try {
            Bitmap source = mBuild.get();
            int width = source.getWidth();
            int height = source.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeigth, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(source,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            if (width != 0 || height != 0) {
                Matrix matrix = new Matrix();
                matrix.setScale(mWidth * 1.0f / width, mHeigth * 1.0f / height);
                shader.setLocalMatrix(matrix);
            }
            paint.setShader(shader);
            paint.setAntiAlias(true);
            canvas.drawRect(0, 0, mWidth, mHeigth, paint);
            source.recycle();
            if (bitmap != null) {
                setBitmap(bitmap, 1);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mErrId > 0)
            try {
                setBitmap(ImageUtil.src(mHeigth, mWidth, mErrId, mContext), 2);
            } catch (Exception e) {

            }

    }

    private void setBitmap(Bitmap bitmap, final int type) {
        if (bitmap == null)
            return;
        final Bitmap bitmap1 = mTransform.transform(bitmap);
//        bitmap.recycle();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (type == 0)
                    mListener.loadStart(bitmap1);
                else if (type == 1)
                    mListener.loaded(bitmap1);
                else if (type == 2)
                    mListener.loadFail(bitmap1);
            }
        });
    }
}
