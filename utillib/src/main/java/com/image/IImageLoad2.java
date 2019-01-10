package com.image;

import android.view.View;
import android.widget.ImageView;

import com.image.transform.ITransform;

import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * com.image
 * 2018/11/1 10:58
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
 interface IImageLoad2 {
    public void load(int width, int heigth, View view, ITransform transform, int res);

    public void load(View view, ITransform transform, int res);

    public void load(View view, int res);

    public void load(int width, int heigth, View view, ITransform transform, File file);

    public void load(View view, ITransform transform, File file);

    public void load(View view, File file);
    public void load(int width, int heigth, View view, ITransform transform, URI uri);

    public void load(View view, ITransform transform, URI uri);

    public void load(View view, URI uri) ;
    public void load(int width, int heigth, View view, ITransform transform, URL url);

    public void load(View view, ITransform transform, URL url) ;

    public void load(View view, URL url);
    public void load(int width, int heigth, View view, ITransform transform, String path);

    public void load(View view, ITransform transform, String path) ;

    public void load(View view, String path);
    public void load(int width, int heigth, ImageView view, ITransform transform, int res);

    public void load(ImageView view, ITransform transform, int res);

    public void load(ImageView view, int res);
    public void load(int width, int heigth, ImageView view, ITransform transform, File file);

    public void load(ImageView view, ITransform transform, File file);

    public void load(ImageView view, File file);
    public void load(int width, int heigth, ImageView view, ITransform transform, URI uri) ;

    public void load(ImageView view, ITransform transform, URI uri) ;

    public void load(ImageView view, URI uri);
    public void load(int width, int heigth, ImageView view, ITransform transform, URL url) ;

    public void load(ImageView view, ITransform transform, URL url);

    public void load(ImageView view, URL url);
    public void load(int width, int heigth, ImageView view, ITransform transform, String path) ;

    public void load(ImageView view, ITransform transform, String path);

    public void load(ImageView view, String path);
}
