package com.image.imageload;

import android.content.Context;
import android.graphics.Bitmap;

import com.image.transform.ITransform;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

/**
 * 2018/7/3 11:13
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

 class ImageloadManage {
    protected   ImageLoader mImageLoader;

    protected ImageloadManage(Context context, Bitmap.Config bitmapConfig, boolean cacheMemory, long cacheMemorySize, boolean cacheDis, long cacheDisSize, String file) {
        File cacheDir = new File(file);
        DisplayImageOptions.Builder builder11 = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(bitmapConfig).cacheInMemory(cacheMemory)
                .cacheOnDisk(cacheDis);
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
        builder.threadPoolSize(5); // 线程池大小
        builder.threadPriority(Thread.NORM_PRIORITY - 2); // 设置线程优先级
        builder.denyCacheImageMultipleSizesInMemory(); // 不允许在内存中缓存同一张图片的多个尺寸
        builder.tasksProcessingOrder(QueueProcessingType.LIFO); // 设置处理队列的类型，包括LIFO， FIFO
        builder.memoryCache(new LruMemoryCache(3 * 1024 * 1024)); // 内存缓存策略
        builder.memoryCacheSize(5 * 1024 * 1024);  // 内存缓存大小
        builder.memoryCacheExtraOptions(480, 800); // 内存缓存中每个图片的最大宽高
        builder.memoryCacheSizePercentage(50); // 内存缓存占总内存的百分比

        builder.diskCache(new UnlimitedDiscCache(cacheDir)); // 设置磁盘缓存策略
        builder.diskCacheSize(50 * 1024 * 1024); // 设置磁盘缓存的大小
        builder.diskCacheFileCount(50); // 磁盘缓存文件数量
        builder.diskCacheFileNameGenerator(new Md5FileNameGenerator()); // 磁盘缓存时图片名称加密方式
        builder.imageDownloader(new BaseImageDownloader(context)); // 图片下载器
        builder.defaultDisplayImageOptions(builder11.build());
        builder.writeDebugLogs(); // 打印日志
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(builder.build());
       // mImageLoader.displayImage();
    }

    protected   DisplayImageOptions options(int errorId, int defaultId, ITransform transform){
        return  new DisplayImageOptions.Builder()
                .showImageOnLoading(errorId) // 加载过程中的显示图片
                .showImageForEmptyUri(defaultId) // 路径为空时显示的图片
                .showImageOnFail(defaultId) // 加载失败显示的图片
              //  .resetViewBeforeLoading(true) // 将要开始加载时是否需要替换成onLoading图片
             // .delayBeforeLoading(1000) // 加载延迟时间
               // .preProcessor(xxx) // 图片加入缓存之前的处理
               // .postProcessor(xxx) // 图片在显示之前的处理
               // .decodingOptions(BitmapFactory.Options) // 解码参数
               // .cacheInMemory(true) // 需要缓存在内存中
               // .cacheOnDisk(true) // 需要缓存到磁盘中
                //.considerExifParams(true) // 是否考虑Exif参数
                //.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // 缩放类型
             //   .bitmapConfig(Bitmap.Config.RGB_565) // bitmap模式
               .displayer(new ImageloadTarget(transform)) // 设置图片显示形式(圆角 or 渐变等)
                .build();
    }
}
