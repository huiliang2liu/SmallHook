package com.image.transform;

import android.graphics.Bitmap;

import com.image.ImageUtil;

/**
 * 2018/7/4 10:33
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class SunshineTransform implements ITransform {
    private  int mLigthX;
    private  int mLigthY;
    public SunshineTransform(){
        this(0,0);
    }
    public SunshineTransform(int ligthX,int ligthY){
        mLigthX=ligthX;
        mLigthY=ligthY;
    }
    @Override
    public Bitmap transform(Bitmap bitmap) {
        if(mLigthX<0||mLigthX>bitmap.getWidth())
            mLigthX=bitmap.getWidth()>>1;
        if(mLigthY<0||mLigthY>bitmap.getHeight())
            mLigthY=bitmap.getHeight()>>1;
        return ImageUtil.sunshine(bitmap,mLigthX,mLigthY);
    }
}
