package com.image.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2018/6/22 18:41
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class PicassoLoadImage {
    private Picasso mPicasso;

    public PicassoLoadImage(Context activitt, Downloader downloader, Cache cache, Bitmap.Config bitmapConfig, ExecutorService executorService) {
        Picasso.Builder builder = new Picasso.Builder(activitt);
        builder.downloader(downloader);
        builder.memoryCache(cache);
        builder.defaultBitmapConfig(bitmapConfig);
        //配置线程池
        //    ExecutorService executorService = Executors.newFixedThreadPool(8);
        builder.executor(executorService);
        //构造一个Picasso
        mPicasso = builder.build();
        // 设置全局单列instance
        Picasso.setSingletonInstance(mPicasso);
    }

    public PicassoLoadImage(Context activitt, Downloader downloader, Bitmap.Config bitmapConfig, ExecutorService executorService) {
        Picasso.Builder builder = new Picasso.Builder(activitt);
        builder.downloader(downloader);
        //builder.memoryCache(null);
        builder.defaultBitmapConfig(bitmapConfig);
        //配置线程池
        //    ExecutorService executorService = Executors.newFixedThreadPool(8);
        builder.executor(executorService);
        //构造一个Picasso
        mPicasso = builder.build();
        // 设置全局单列instance
        Picasso.setSingletonInstance(mPicasso);
    }

    public PicassoLoadImage(Context activitt, Downloader downloader, ExecutorService executorService) {
        Picasso.Builder builder = new Picasso.Builder(activitt);
        builder.downloader(downloader);
       // builder.memoryCache(null);
        builder.defaultBitmapConfig(Bitmap.Config.RGB_565);
        //配置线程池
        //    ExecutorService executorService = Executors.newFixedThreadPool(8);
        builder.executor(executorService);
        //构造一个Picasso
        mPicasso = builder.build();
        // 设置全局单列instance
        Picasso.setSingletonInstance(mPicasso);
    }

    public PicassoLoadImage(Context activitt, Downloader downloader) {
        Picasso.Builder builder = new Picasso.Builder(activitt);
        builder.downloader(downloader);
        //builder.memoryCache(null);
        builder.defaultBitmapConfig(Bitmap.Config.RGB_565);
        //配置线程池
        //    ExecutorService executorService = Executors.newFixedThreadPool(8);
        builder.executor(Executors.newFixedThreadPool(5));
        //构造一个Picasso
        mPicasso = builder.build();
        // 设置全局单列instance
        Picasso.setSingletonInstance(mPicasso);
    }

    public PicassoLoadImage(Context activitt) {
        Picasso.Builder builder = new Picasso.Builder(activitt);
        //builder.memoryCache(null);
        builder.defaultBitmapConfig(Bitmap.Config.RGB_565);
        //配置线程池
        //    ExecutorService executorService = Executors.newFixedThreadPool(8);
        builder.executor(Executors.newFixedThreadPool(5));
        //构造一个Picasso
        mPicasso = builder.build();
        // 设置全局单列instance
        Picasso.setSingletonInstance(mPicasso);
    }
    public PicassoLoadImage(Context activitt,Bitmap.Config bitmapConfig) {
        Picasso.Builder builder = new Picasso.Builder(activitt);
        //builder.memoryCache(null);
        builder.defaultBitmapConfig(bitmapConfig);
        //配置线程池
        //    ExecutorService executorService = Executors.newFixedThreadPool(8);
        builder.executor(Executors.newFixedThreadPool(5));
        //构造一个Picasso
        mPicasso = builder.build();
        // 设置全局单列instance
        Picasso.setSingletonInstance(mPicasso);
    }

    public Picasso getPicasso() {
        return mPicasso;
    }

    /**
     * 2018/6/22 19:30
     * annotation：取消设置了给定tag的所有请求
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public void cancelTag(Object tag) {
        mPicasso.cancelTag(tag);
    }

    /**
     * 2018/6/22 19:30
     * annotation：暂停设置了给定tag 的所有请求
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public void pauseTag(Object tag) {
        mPicasso.pauseTag(tag);
    }

    /**
     * 2018/6/22 19:30
     * annotation：resume 被暂停的给定tag的所有请求
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public void resumeTag(Object tag) {
        mPicasso.resumeTag(tag);
    }

    /**
     * 2018/6/22 18:53
     * annotation：设置加载地址，可以是文件地址，网络路径
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public PicassoBuild load(String path) {
        return new PicassoBuild(mPicasso.load(path));
    }

    /**
     * 2018/6/22 18:53
     * annotation：资源id
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public PicassoBuild load(int id) {
        return new PicassoBuild(mPicasso.load(id));
    }

    /**
     * 2018/6/22 18:54
     * annotation：文件
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public PicassoBuild load(File file) {
        return new PicassoBuild(mPicasso.load(file));
    }

    /**
     * 2018/6/22 18:54
     * annotation：uri
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public PicassoBuild load(Uri uri) {
        return new PicassoBuild(mPicasso.load(uri));
    }

    public static class PicassoBuild {
        private RequestCreator mRequestcreator;

        public PicassoBuild(RequestCreator requestcreator) {
            mRequestcreator = requestcreator;
        }

        /**
         * 2018/6/22 19:32
         * annotation：NO_CACHE：表示处理请求的时候跳过检查内存缓存
         * *NO_STORE: ** 表示请求成功之后，不将最终的结果存到内存。
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild memoryPolicy(MemoryPolicy policy) {
            mRequestcreator.memoryPolicy(policy);
            return this;
        }

        public PicassoBuild placeholder(int id) {
            mRequestcreator.placeholder(id);
            return this;
        }

        public PicassoBuild error(int id) {
            mRequestcreator.error(id);
            return this;
        }

        /**
         * 2018/6/22 19:06
         * annotation：不能和placeholder同时设置
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild noPlaceholder() {
            mRequestcreator.noPlaceholder();
            return this;
        }

        /**
         * 2018/6/22 19:06
         * annotation：取消动画
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild noFade() {
            mRequestcreator.noFade();
            return this;
        }

        /**
         * 2018/6/22 19:08
         * annotation：设置大小像素
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild resize(int width, int heigth) {
            mRequestcreator.resize(width, heigth);
            return this;
        }

        /**
         * 2018/6/22 19:11
         * annotation：设置大小 资源id设置
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild resizeDimen(int widthid, int heigthId) {
            mRequestcreator.resizeDimen(widthid, heigthId);
            return this;
        }

        /**
         * 2018/6/22 19:14
         * annotation：这个属性应该不陌生吧！ImageView 的ScaleType 就有这个属性。当我们使用resize 来重新设置图片的尺寸的时候，你会发现有些图片拉伸或者扭曲了（使用ImageView的时候碰到过吧），我要避免这种情况，Picasso 同样给我们提供了一个方法，centerCrop，充满ImageView 的边界，居中裁剪
         * <p>
         * 作者：依然范特稀西
         * 链接：https://www.jianshu.com/p/c68a3b9ca07a
         * 來源：简书
         * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild centerCrop() {
            mRequestcreator.centerCrop();
            return this;
        }

        /**
         * 2018/6/22 19:16
         * annotation：上面的centerCrop是可能看不到全部图片的，如果你想让View将图片展示完全，可以用centerInside，但是如果图片尺寸小于View尺寸的话，是不能充满View边界的
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild centerInside() {
            mRequestcreator.centerInside();
            return this;
        }

        /**
         * 2018/6/22 19:18
         * annotation：fit 方法就帮我们解决了这个问题。fit 它会自动测量我们的View的大小，然后内部调用reszie方法把图片裁剪到View的大小
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild fit() {
            mRequestcreator.fit();
            return this;
        }

        /**
         * 2018/6/22 19:19
         * annotation：旋转
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild rotate(int degree) {
            mRequestcreator.rotate(degree);
            return this;
        }

        /**
         * 2018/6/22 19:19
         * annotation：旋转
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild rotate(int degree, float pivotX, float pivotY) {
            mRequestcreator.rotate(degree, pivotX, pivotY);
            return this;
        }

        public PicassoBuild transform(Transformation transformation) {
            mRequestcreator.transform(transformation);
            return this;
        }

        public PicassoBuild transform(List<? extends Transformation> transformations) {
            mRequestcreator.transform(transformations);
            return this;
        }

        /**
         * 2018/6/22 19:23
         * annotation：设置优先级
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild priority(Picasso.Priority priority) {
            mRequestcreator.priority(priority);
            return this;
        }

        /**
         * 2018/6/22 19:24
         * annotation：设置标记管理线程
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public PicassoBuild tag(Object tag) {
            mRequestcreator.tag(tag);
            return this;
        }

        /**
         * 2018/6/22 18:57
         * annotation：设置到加载地址上
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public void into(ImageView view) {
            mRequestcreator.into(view);
        }

        /**
         * 2018/6/22 18:57
         * annotation：设置到加载地址上
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public void into(Target view) {
            mRequestcreator.into(view);
        }

        /**
         * 2018/6/22 19:34
         * annotation：同步获取图片
         * author：liuhuiliang
         * email ：825378291@qq.com
         */
        public Bitmap get() {
            try {
                return mRequestcreator.get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
