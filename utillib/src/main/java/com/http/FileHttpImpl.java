package com.http;


import com.http.asyn.AsynHttp;
import com.http.listen.ResponseObjectListener;
import com.http.listen.ResponseStringListener;
import com.http.okhttp.OkHttp;
import com.utils.LogUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * com.http.volley
 * 2018/11/1 16:57
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
class FileHttpImpl extends AbsFileHttp {
    private FileHttp http;
    private final static String TAG = "FileHttpImpl";

    public FileHttpImpl() {
        if (isClass("okhttp3.OkHttpClient")) {
            http = new OkHttp();
            LogUtil.i(TAG, "okhttp");
        } else if (isClass("com.loopj.android.http.AsyncHttpClient")) {
            http = new AsynHttp();
            LogUtil.i(TAG, "asyn");
        } else {
            http = new com.http.http.Http();
            LogUtil.i(TAG, "http");
        }
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file) {
        return http.file(url, heard, params, fileKey, file);
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls) {
        return http.file(url, heard, params, fileKey, file, cls);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, ResponseStringListener listener) {
        http.fileAsyn(url, heard, params, fileKey, file, listener);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls, ResponseObjectListener listener) {
        http.fileAsyn(url, heard, params, fileKey, file, cls, listener);
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files) {
        return http.file(url, heard, params, fileKey, files);
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls) {
        return http.file(url, heard, params, fileKey, files, cls);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, ResponseStringListener listener) {
        http.fileAsyn(url, heard, params, fileKey, files, listener);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls, ResponseObjectListener listener) {
        http.fileAsyn(url, heard, params, fileKey, files, cls, listener);
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap) {
        return http.file(url, heard, params, fileMap);
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls) {
        return http.file(url, heard, params, fileMap, cls);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, ResponseStringListener listener) {
        http.fileAsyn(url, heard, params, fileMap, listener);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls, ResponseObjectListener listener) {
        http.fileAsyn(url, heard, params, fileMap, cls, listener);
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
