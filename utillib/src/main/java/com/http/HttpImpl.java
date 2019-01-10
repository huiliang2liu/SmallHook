package com.http;

import android.content.Context;

import com.http.asyn.AsynHttp;
import com.http.okhttp.OkHttp;
import com.http.volley.VolleyHttp;
import com.utils.LogUtil;

/**
 * com.http
 * 2018/10/18 18:59
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
class HttpImpl implements Http {
    private final static String TAG = "HttpImpl";
    private volatile Http http;

    public HttpImpl(Context context) {
        if (isClass("okhttp3.OkHttpClient")) {
            http = new OkHttp();
            LogUtil.i(TAG, "okhttp");
        } else if (isClass("com.android.volley.RequestQueue")) {
            http = new VolleyHttp(context);
            LogUtil.i(TAG, "volley");
        } else if (isClass("com.loopj.android.http.AsyncHttpClient")) {
            http = new AsynHttp();
            LogUtil.i(TAG, "asyn");
        } else {
            http = new com.http.http.Http();
            LogUtil.i(TAG, "http");
        }
    }
    @Override
    public ResponseString get(RequestEntity entity) {
        return http.get(entity);
    }

    @Override
    public ResponseObject getObject(RequestEntity entity) {
        return http.getObject(entity);
    }

    @Override
    public ResponseString post(RequestEntity entity) {
        return http.post(entity);
    }

    @Override
    public ResponseObject postObject(RequestEntity entity) {
        return http.postObject(entity);
    }

    @Override
    public void getAsyn(RequestEntity entity) {
        http.getAsyn(entity);
    }

    @Override
    public void getObjectAsyn(RequestEntity entity) {
        http.getObjectAsyn(entity);
    }

    @Override
    public void postAsyn(RequestEntity entity) {
        http.postAsyn(entity);
    }

    @Override
    public void postObjectAsyn(RequestEntity entity) {
        http.postObjectAsyn(entity);
    }

    @Override
    public void cancle(Object tag) {
        http.cancle(tag);
    }

    private boolean isClass(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
