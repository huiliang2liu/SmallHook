package com.iface;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * com.iface
 * 2019/1/11 10:47
 * instructions：svg接口
 * author:liuhuiliang  email:825378291@qq.com
 **/
public interface ISVG {
        /**
         *
         * lhl 2018-1-9 下午4:22:35 说明：创建位图
         *
         * @param width
         * @param heigth
         * @return Bitmap
         */
        public Bitmap createBitmap(int width, int heigth);

        /**
         *
         * lhl 2018-1-9 下午4:23:04 说明：创建位图
         *
         * @param view
         * @return Bitmap
         */
        public Bitmap createBitmap(View view);

        /**
         *
         * lhl 2018-1-9 下午4:23:32 说明：创建位图
         *
         * @param factor
         * @return Bitmap
         */
        public Bitmap createBitmap(float factor);

        /**
         *
         * lhl 2018-1-9 下午4:23:15 说明：创建位图
         *
         * @return Bitmap
         */
        public Bitmap createBitmap();

        /**
         *
         * lhl 2018-1-9 下午4:23:43 说明：创建drawable
         *
         * @param width
         * @param heigth
         * @return Drawable
         */
        public Drawable createDrawable(int width, int heigth);

        /**
         *
         * lhl 2018-1-9 下午4:24:14 说明：创建drawable
         *
         * @param view
         * @return Drawable
         */
        public Drawable createDrawable(View view);

        /**
         *
         * lhl 2018-1-9 下午4:24:29 说明：创建drawable
         *
         * @param factor
         * @return Drawable
         */
        public Drawable createDrawable(float factor);

        /**
         *
         * lhl 2018-1-9 下午4:24:02 说明：创建drawable
         *
         * @return Drawable
         */
        public Drawable createDrawable();

        /**
         *
         * lhl 2018-1-9 下午4:25:31 说明：创建路径
         *
         * @param width
         * @param heigth
         * @return Path
         */
        public Path createPath(int width, int heigth);

        /**
         *
         * lhl 2018-1-9 下午4:26:05 说明：：创建路径
         *
         * @param view
         * @return Path
         */
        public Path createPath(View view);

        /**
         *
         * lhl 2018-1-9 下午4:26:14 说明：：创建路径
         *
         * @param factor
         * @return Path
         */
        public Path createPath(float factor);

        /**
         *
         * lhl 2018-1-9 下午4:25:56 说明：：创建路径
         *
         * @return Path
         */
        public Path createPath();

        /**
         *
         * lhl 2018-1-9 下午4:26:38 说明：获取颜色
         *
         * @return int
         */
        public int color();

        /**
         *
         * lhl 2018-1-9 下午4:21:18 说明：获取图片宽度
         *
         * @return int
         */
        public int getWidth();

        /**
         *
         * lhl 2018-1-9 下午4:20:53 说明：获取图片高度
         *
         * @return int
         */
        public int getHeigth();

        /**
         *
         * lhl 2018-1-9 下午4:20:30 说明：获取画布高度
         *
         * @return int
         */
        public int getViewBoxWidth();

        /**
         *
         * lhl 2018-1-9 下午4:19:56 说明：获取画布高度
         *
         * @return int
         */
        public int getViewBoxHeigth();

        /**
         *
         * lhl 2018-1-9 下午4:19:37 说明：获取横坐标的偏移量
         *
         * @return float
         */
        public float getX();

        /**
         *
         * lhl 2018-1-9 下午4:18:42 说明：获取纵坐标的便宜量
         *
         * @return float
         */
        public float getY();
}
