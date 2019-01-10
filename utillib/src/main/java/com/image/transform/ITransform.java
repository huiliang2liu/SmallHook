package com.image.transform;

import android.graphics.Bitmap;

/**
 * 2018/7/2 10:20
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public interface ITransform {
    /**
     * 2018/7/2 10:21
     * annotation：图片变换
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    Bitmap transform(Bitmap bitmap);
}
