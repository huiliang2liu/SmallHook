package com.iface;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import org.xml.sax.InputSource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * com.iface
 * 2019/1/11 10:48
 * instructions：svg文件解析接口
 * author:liuhuiliang  email:825378291@qq.com
 **/
public interface ISVGPars {
    /**
     * lhl 2018-1-9 下午5:50:17 说明：将数据流转化为
     *
     * @param is
     * @return
     * @throws Exception ISVG
     */
    public ISVG paras(InputStream is) throws Exception;

    /**
     * lhl 2018-1-9 下午5:50:57 说明：将数据流转化为drawable
     *
     * @param is
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(InputStream is) throws Exception;

    /**
     * lhl 2018-1-9 下午5:51:47 说明：将数据流转化为drawable
     *
     * @param is
     * @param view
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(InputStream is, View view) throws Exception;

    /**
     * lhl 2018-1-9 下午5:51:59 说明：将数据流转化为drawable
     *
     * @param is
     * @param width
     * @param heigth
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(InputStream is, int width, int heigth)
            throws Exception;

    /**
     * lhl 2018-1-9 下午5:52:14 说明：将文件转化为ISVG
     *
     * @param file
     * @return
     * @throws Exception ISVG
     */
    public ISVG paras(File file) throws Exception;

    /**
     * lhl 2018-1-9 下午5:52:43 说明：将文件转化为drawable
     *
     * @param file
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(File file) throws Exception;

    /**
     * lhl 2018-1-9 下午5:53:08 说明：将文件转化为drawable
     *
     * @param file
     * @param view
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(File file, View view) throws Exception;

    /**
     * lhl 2018-1-9 下午5:53:19 说明：将文件转化为drawable
     *
     * @param file
     * @param width
     * @param heigth
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(File file, int width, int heigth) throws Exception;

    /**
     * lhl 2018-1-9 下午5:53:44 说明：将uri转化为ISVG
     *
     * @param uri
     * @return
     * @throws Exception ISVG
     */
    public ISVG paras(String uri) throws Exception;

    /**
     * lhl 2018-1-9 下午5:54:12 说明：将uri转化为drawable
     *
     * @param uri
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(String uri) throws Exception;

    /**
     * lhl 2018-1-9 下午5:54:30 说明：将uri转化为drawable
     *
     * @param uri
     * @param view
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(String uri, View view) throws Exception;

    /**
     * lhl 2018-1-9 下午5:54:41 说明：将uri转化为drawable
     *
     * @param uri
     * @param width
     * @param heigth
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(String uri, int width, int heigth)
            throws Exception;

    /**
     * lhl 2018-1-9 下午5:55:05 说明：将url转化为ISVG
     *
     * @param url
     * @return
     * @throws Exception ISVG
     */
    public ISVG paras(URL url) throws Exception;

    /**
     * lhl 2018-1-9 下午5:55:21 说明：将url转化为drawable
     *
     * @param url
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(URL url) throws Exception;

    /**
     * lhl 2018-1-9 下午5:55:34 说明：将url转化为drawable
     *
     * @param url
     * @param view
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(URL url, View view) throws Exception;

    /**
     * lhl 2018-1-9 下午5:55:41 说明：将url转化为drawable
     *
     * @param url
     * @param width
     * @param heigth
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(URL url, int width, int heigth) throws Exception;

    /**
     * lhl 2018-1-9 下午5:56:14 说明：将InputSource转化为ISVG
     *
     * @param is
     * @return
     * @throws Exception ISVG
     */
    public ISVG paras(InputSource is) throws Exception;

    /**
     * lhl 2018-1-9 下午5:56:32 说明：将InputSource转化为drawable
     *
     * @param is
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(InputSource is) throws Exception;

    /**
     * lhl 2018-1-9 下午5:56:45 说明：将InputSource转化为drawable
     *
     * @param is
     * @param view
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(InputSource is, View view) throws Exception;

    /**
     * lhl 2018-1-9 下午5:56:56 说明：将InputSource转化为drawable
     *
     * @param is
     * @param width
     * @param heigth
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(InputSource is, int width, int heigth)
            throws Exception;

    /**
     * lhl 2018-1-9 下午5:57:09 说明：将resources转化为ISVG
     *
     * @param resources
     * @param resId
     * @return
     * @throws Exception VectorEntity
     */
    public ISVG paras(Resources resources, int resId) throws Exception;

    /**
     * lhl 2018-1-9 下午5:57:43 说明：将resources转化为drawable
     *
     * @param resources
     * @param resId
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(Resources resources, int resId) throws Exception;

    /**
     * lhl 2018-1-9 下午5:57:54 说明：将resources转化为drawable
     *
     * @param resources
     * @param resId
     * @param view
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(Resources resources, int resId, View view)
            throws Exception;

    /**
     * lhl 2018-1-9 下午5:58:04 说明：将resources转化为drawable
     *
     * @param resources
     * @param resId
     * @param width
     * @param heigth
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(Resources resources, int resId, int width,
                             int heigth) throws Exception;

    /**
     * lhl 2018-1-9 下午5:59:31 说明：将assets文件转化为ISVG
     *
     * @param manager
     * @param string
     * @return
     * @throws Exception ISVG
     */
    public ISVG paras(AssetManager manager, String string) throws Exception;

    /**
     * lhl 2018-1-9 下午6:00:16 说明：将assets文件转化为drawable
     *
     * @param string
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(AssetManager manager, String string)
            throws Exception;

    /**
     * lhl 2018-1-9 下午6:00:44 说明：将assets文件转化为drawable
     *
     * @param string
     * @param view
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(AssetManager manager, String string, View view)
            throws Exception;

    /**
     * lhl 2018-1-11 下午6:11:30 说明：
     *
     * @param manager
     * @param string
     * @param width
     * @param heigth
     * @return
     * @throws Exception Drawable
     */
    public Drawable drawable(AssetManager manager, String string, int width,
                             int heigth) throws Exception;

    /**
     * lhl 2018-1-11 下午6:11:41 说明：将字符串转化为
     */
    public ISVG parasString(String pathString);

    /**
     * lhl 2018-1-11 下午6:12:43 说明：将字符串转化为
     */
    public Drawable drawableString(String string);

    /**
     * lhl 2018-1-11 下午6:13:04 说明：将字符串转化为
     */
    public Drawable drawableString(String string, View view);

    /**
     * lhl 2018-1-11 下午6:13:17 说明：将字符串转化为
     */
    public Drawable drawableString(String string, int width, int heigth);
}
