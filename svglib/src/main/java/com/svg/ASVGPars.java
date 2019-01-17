package com.svg;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.iface.ISVG;
import com.iface.ISVGPars;

import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * com.svg
 * 2019/1/11 10:55
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public abstract class ASVGPars implements ISVGPars {
    SAXParser saxParser;
    public Map<String, SoftReference<ISVG>> map;
    private SAXParasHandler handler;

    /**
     * lhl 2018-1-9 下午6:11:09 说明：获取解析器
     *
     * @return SAXParasHandler
     */
    protected abstract SAXParasHandler parasHandler();

    public ASVGPars() {
        try {
            saxParser = SAXParserFactory.newInstance().newSAXParser();
            handler = parasHandler();
            map = new HashMap<String, SoftReference<ISVG>>();
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public ISVG paras(InputStream is) throws Exception {
        // TODO Auto-generated method stub
        saxParser.parse(is, handler);
        return handler.svg();
    }

    @Override
    public Drawable drawable(InputStream is) throws Exception {
        // TODO Auto-generated method stub
        return paras(is).createDrawable();
    }

    @Override
    public Drawable drawable(InputStream is, View view) throws Exception {
        // TODO Auto-generated method stub
        return paras(is).createDrawable(view);
    }

    @Override
    public Drawable drawable(InputStream is, int width, int heigth)
            throws Exception {
        // TODO Auto-generated method stub
        return paras(is).createDrawable(width, heigth);
    }

    @Override
    public ISVG paras(File file) throws Exception {
        // TODO Auto-generated method stub
        String filePath = file.getAbsolutePath();
        ISVG entity = string2isvg(filePath);
        if (entity != null)
            return entity;
        saxParser.parse(file, handler);
        entity = handler.svg();
        map.put(filePath, new SoftReference<ISVG>(entity));
        return entity;
    }

    @Override
    public Drawable drawable(File file) throws Exception {
        // TODO Auto-generated method stub
        return paras(file).createDrawable();
    }

    @Override
    public Drawable drawable(File file, View view) throws Exception {
        // TODO Auto-generated method stub
        return paras(file).createDrawable(view);
    }

    @Override
    public Drawable drawable(File file, int width, int heigth) throws Exception {
        // TODO Auto-generated method stub
        return paras(file).createDrawable(width, heigth);
    }

    @Override
    public ISVG paras(String uri) throws Exception {
        // TODO Auto-generated method stub
        ISVG entity = string2isvg(uri);
        if (entity != null)
            return entity;
        saxParser.parse(uri, handler);
        entity = handler.svg();
        map.put(uri, new SoftReference<ISVG>(entity));
        return entity;
    }

    @Override
    public Drawable drawable(String uri) throws Exception {
        // TODO Auto-generated method stub
        return paras(uri).createDrawable();
    }

    @Override
    public Drawable drawable(String uri, View view) throws Exception {
        // TODO Auto-generated method stub
        return paras(uri).createDrawable(view);
    }

    @Override
    public Drawable drawable(String uri, int width, int heigth)
            throws Exception {
        // TODO Auto-generated method stub
        return paras(uri).createDrawable(width, heigth);
    }

    @Override
    public ISVG paras(URL url) throws Exception {
        // TODO Auto-generated method stub
        String urlPath = url.getPath();
        ISVG entity = string2isvg(urlPath);
        if (entity != null)
            return entity;
        entity = paras(url.openStream());
        map.put(urlPath, new SoftReference<ISVG>(entity));
        return entity;
    }

    @Override
    public Drawable drawable(URL url) throws Exception {
        // TODO Auto-generated method stub
        return paras(url).createDrawable();
    }

    @Override
    public Drawable drawable(URL url, View view) throws Exception {
        // TODO Auto-generated method stub
        return paras(url).createDrawable(view);
    }

    @Override
    public Drawable drawable(URL url, int width, int heigth) throws Exception {
        // TODO Auto-generated method stub
        return paras(url).createDrawable(width, heigth);
    }

    @Override
    public ISVG paras(InputSource is) throws Exception {
        // TODO Auto-generated method stub
        saxParser.parse(is, handler);
        return handler.svg();
    }

    @Override
    public Drawable drawable(InputSource is) throws Exception {
        // TODO Auto-generated method stub
        return paras(is).createDrawable();
    }

    @Override
    public Drawable drawable(InputSource is, View view) throws Exception {
        // TODO Auto-generated method stub
        return paras(is).createDrawable(view);
    }

    @Override
    public Drawable drawable(InputSource is, int width, int heigth)
            throws Exception {
        // TODO Auto-generated method stub
        return paras(is).createDrawable(width, heigth);
    }

    @Override
    public ISVG paras(Resources resources, int resId) throws Exception {
        // TODO Auto-generated method stub
        String resName = resources.getResourceName(resId);
        ISVG entity = string2isvg(resName);
        if (entity != null)
            return entity;
        entity = paras(resources.openRawResource(resId));
        map.put(resName, new SoftReference<ISVG>(entity));
        return entity;
    }

    @Override
    public Drawable drawable(Resources resources, int resId) throws Exception {
        // TODO Auto-generated method stub
        return paras(resources, resId).createDrawable();
    }

    @Override
    public Drawable drawable(Resources resources, int resId, View view)
            throws Exception {
        // TODO Auto-generated method stub
        return paras(resources, resId).createDrawable(view);
    }

    @Override
    public Drawable drawable(Resources resources, int resId, int width,
                             int heigth) throws Exception {
        // TODO Auto-generated method stub
        return paras(resources, resId).createDrawable(width, heigth);
    }

    @Override
    public ISVG paras(AssetManager manager, String string) throws Exception {
        // TODO Auto-generated method stub
        ISVG entity = string2isvg(string);
        if (entity != null)
            return entity;
        entity = paras(manager.open(string));
        map.put(string, new SoftReference<ISVG>(entity));
        return entity;
    }

    @Override
    public Drawable drawable(AssetManager manager, String string)
            throws Exception {
        // TODO Auto-generated method stub
        return paras(manager, string).createDrawable();
    }

    @Override
    public Drawable drawable(AssetManager manager, String string, View view)
            throws Exception {
        // TODO Auto-generated method stub
        return paras(manager, string).createDrawable(view);
    }

    @Override
    public Drawable drawable(AssetManager manager, String string, int width,
                             int heigth) throws Exception {
        // TODO Auto-generated method stub
        return paras(manager, string).createDrawable(width, heigth);
    }

    @Override
    public Drawable drawableString(String string) {
        // TODO Auto-generated method stub
        return parasString(string).createDrawable();
    }

    @Override
    public Drawable drawableString(String string, View view) {
        // TODO Auto-generated method stub
        return parasString(string).createDrawable(view);
    }

    @Override
    public Drawable drawableString(String string, int width, int heigth) {
        // TODO Auto-generated method stub
        return parasString(string).createDrawable(width, heigth);
    }


    public static abstract class SAXParasHandler extends DefaultHandler {
        /**
         * lhl 2018-1-9 下午6:12:41 说明：获取解析后的svg
         *
         * @return ISVG
         */
        public abstract ISVG svg();
    }

    public ISVG string2isvg(String string) {
        if (map.containsKey(string)) {
            ISVG entity = map.get(string).get();
            if (entity != null)
                return entity;
        }
        return null;
    }

}
