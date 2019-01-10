package com.image;

import android.graphics.Bitmap;

/**
 * 2018/7/6 10:15
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public interface ImageLoadListener {
    /**
     * 2018/7/6 10:22
     * annotation：开始加载回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    void loadStart(Bitmap bitmap);
    /**
     * 2018/7/6 10:22
     * annotation：加载失败回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    void loadFail(Bitmap bitmap);
    /**
     * 2018/7/6 10:22
     * annotation：加载成功回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    void loaded(Bitmap bitmap);
}
