package com.http;

import com.http.listen.ResponseObjectListener;
import com.http.listen.ResponseStringListener;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * com.http
 * 2018/10/18 14:49
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public abstract class AbsFileHttp implements FileHttp {
    @Override
    public ResponseString file(String url, Map<String, String> params, String fileKey, File file) {
        return file(url, null, params, fileKey, file);
    }

    @Override
    public ResponseString file(String url, String fileKey, File file) {
        return file(url, null, fileKey, file);
    }

    @Override
    public ResponseObject file(String url, Map<String, String> params, String fileKey, File file, Class cls) {
        return file(url, null, params, fileKey, file, cls);
    }

    @Override
    public ResponseObject file(String url, String fileKey, File file, Class cls) {
        return file(url, null, fileKey, file, cls);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> params, String fileKey, File file, ResponseStringListener listener) {
        fileAsyn(url, null, params, fileKey, file, listener);
    }

    @Override
    public void fileAsyn(String url, String fileKey, File file, ResponseStringListener listener) {
        fileAsyn(url, null, fileKey, file, listener);
    }

    @Override
    public void fileAsyn(String url, String fileKey, File file) {
        fileAsyn(url, fileKey, file, null);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> params, String fileKey, File file, Class cls, ResponseObjectListener listener) {
        fileAsyn(url, null, params, fileKey, file, cls, listener);
    }

    @Override
    public void fileAsyn(String url, String fileKey, File file, Class cls, ResponseObjectListener listener) {
        fileAsyn(url, null, fileKey, file, cls, listener);
    }

    @Override
    public ResponseString file(String url, Map<String, String> params, String fileKey, List<File> files) {
        return file(url, null, params, fileKey, files);
    }

    @Override
    public ResponseString file(String url, String fileKey, List<File> files) {
        return file(url, null, fileKey, files);
    }

    @Override
    public ResponseObject file(String url, Map<String, String> params, String fileKey, List<File> files, Class cls) {
        return file(url, null, params, fileKey, files, cls);
    }

    @Override
    public ResponseObject file(String url, String fileKey, List<File> files, Class cls) {
        return file(url, null, fileKey, files, cls);
    }


    @Override
    public void fileAsyn(String url, Map<String, String> params, String fileKey, List<File> files, ResponseStringListener listener) {
        fileAsyn(url, null, params, fileKey, files, listener);
    }

    @Override
    public void fileAsyn(String url, String fileKey, List<File> files, ResponseStringListener listener) {
        fileAsyn(url, null, fileKey, files, listener);
    }

    @Override
    public void fileAsyn(String url, String fileKey, List<File> files) {
        fileAsyn(url, fileKey, files, null);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> params, String fileKey, List<File> files, Class cls, ResponseObjectListener listener) {
        fileAsyn(url, null, params, fileKey, files, cls, listener);
    }

    @Override
    public void fileAsyn(String url, String fileKey, List<File> files, Class cls, ResponseObjectListener listener) {
        fileAsyn(url, null, fileKey, files, cls, listener);
    }

    @Override
    public ResponseString file(String url, Map<String, String> params, Map<String, File> fileMap) {
        return file(url, null, params, fileMap);
    }

    @Override
    public ResponseString file(String url, Map<String, File> fileMap) {
        return file(url, null, fileMap);
    }


    @Override
    public ResponseObject file(String url, Map<String, String> params, Map<String, File> fileMap, Class cls) {
        return file(url, null, params, fileMap, cls);
    }

    @Override
    public ResponseObject file(String url, Map<String, File> fileMap, Class cls) {
        return file(url, null, fileMap, cls);
    }


    @Override
    public void fileAsyn(String url, Map<String, String> params, Map<String, File> fileMap, ResponseStringListener listener) {
        fileAsyn(url, null, params, fileMap, listener);
    }

    @Override
    public void fileAsyn(String url, Map<String, File> fileMap, ResponseStringListener listener) {
        fileAsyn(url, null, fileMap, listener);
    }

    @Override
    public void fileAsyn(String url, Map<String, File> fileMap) {
        fileAsyn(url, fileMap, null);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> params, Map<String, File> fileMap, Class cls, ResponseObjectListener listener) {
        fileAsyn(url, null, params, fileMap, cls, listener);
    }

    @Override
    public void fileAsyn(String url, Map<String, File> fileMap, Class cls, ResponseObjectListener listener) {
        fileAsyn(url, null, fileMap, cls, listener);
    }
}
