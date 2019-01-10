package com.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;

import com.bumptech.glide.request.FutureTarget;
import com.image.ImageLoadListener;
import com.image.ImageUtil;
import com.image.transform.ITransform;

/**
 * 2018/7/5 19:08
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class GlideRunnable implements Runnable {
    private Handler mHandler;
    private FutureTarget<Bitmap> mBuilder;
    private Context mContext;
    private int mErrId;
    private int mDefaultId;
    private int mWidth;
    private int mHeigth;
    private ITransform mTransform;
    private ImageLoadListener mListener;

    public GlideRunnable(int errId, int defaultId, int width, int higth, FutureTarget<Bitmap> builder, Context context, ITransform transform, ImageLoadListener loadListener) {
        mHandler = new Handler(Looper.getMainLooper());
        mListener=loadListener;

        mContext = context;
        mBuilder = builder;
        mErrId = errId;
        mDefaultId = defaultId;
        mWidth = width;
        mHeigth = higth;
        mTransform = transform;

    }

    @Override
    public void run() {
        try {
            if (mDefaultId > 0)
                setBitmap(ImageUtil.src(mHeigth, mWidth, mDefaultId, mContext),0);
        } catch (Exception e) {

        }
        try {
            Bitmap source=mBuilder.get();
            if(source!=null){

                int width = source.getWidth();
                int height = source.getHeight();
                Bitmap  bitmap = Bitmap.createBitmap(mWidth, mHeigth, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                BitmapShader shader =
                        new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
                if (width != 0 || height != 0) {
                    Matrix matrix = new Matrix();
                    matrix.setScale(mWidth*1.0f/width,mHeigth*1.0f/height);
                    shader.setLocalMatrix(matrix);
                }
                paint.setShader(shader);
                paint.setAntiAlias(true);
                canvas.drawRect(0,0,mWidth,mHeigth,paint);
                source.recycle();
                setBitmap(bitmap,1);
                return;
            }
        } catch (Exception e) {

        }
        try {
            if (mErrId > 0)
                setBitmap(ImageUtil.src(mHeigth, mWidth, mErrId, mContext),2);
        } catch (Exception e) {

        }
    }

    public void setBitmap( Bitmap bitmap, final int type) {
        if (bitmap == null)
            return;
        final  Bitmap bitmap1=mTransform.transform(bitmap);
//       bitmap.recycle();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(type==0)
                    mListener.loadStart(bitmap1);
                else if(type==1)
                    mListener.loaded(bitmap1);
                else if(type==2)
                    mListener.loadFail(bitmap1);
            }
        });
    }
}
