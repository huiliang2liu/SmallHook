package com.http.http;

import com.http.AbsFileHttp;
import com.http.ResponseObject;
import com.http.ResponseString;
import com.http.listen.ResponseObjectListener;
import com.http.listen.ResponseStringListener;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * com.http.http
 * 2018/10/19 10:11
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class FileHttp extends AbsFileHttp {
    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file) {

        return null;
    }

    @Override
    public  ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls) {
        return null;
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, ResponseStringListener listener) {

    }

    @Override
    public  void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls, ResponseObjectListener listener) {

    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files) {
        if (files == null || files.size() <= 0)
            return null;
        ResponseString rs = null;
        for (File file : files)
            rs = file(url, heard, params, fileKey, file);
        return rs;
    }

    @Override
    public  ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls) {
        if (files == null || files.size() <= 0)
            return null;
        ResponseObject ro = null;
        for (File file : files)
            ro = file(url, heard, params, fileKey, file, cls);
        return ro;
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, ResponseStringListener listener) {
        if (files == null || files.size() <= 0)
            return;
        for (File file : files)
            fileAsyn(url, heard, params, fileKey, file, listener);
    }

    @Override
    public  void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls, ResponseObjectListener listener) {
        if (files == null || files.size() <= 0)
            return;
        for (File file : files)
            fileAsyn(url, heard, params, fileKey, file, cls, listener);
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap) {
        if (fileMap == null || fileMap.size() <= 0)
            return null;
        ResponseString rs = null;
        for (Map.Entry<String, File> entry : fileMap.entrySet())
            rs = file(url, heard, params, entry.getKey(), entry.getValue());
        return rs;
    }

    @Override
    public  ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls) {
        if (fileMap == null || fileMap.size() <= 0)
            return null;
        ResponseObject ro = null;
        for (Map.Entry<String, File> entry : fileMap.entrySet())
            ro = file(url, heard, params, entry.getKey(), entry.getValue(), cls);
        return ro;
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, ResponseStringListener listener) {
        if (fileMap == null || fileMap.size() <= 0)
            return;
        for (Map.Entry<String, File> entry : fileMap.entrySet())
            fileAsyn(url, heard, params, entry.getKey(), entry.getValue(), listener);
    }

    @Override
    public  void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls, ResponseObjectListener listener) {
        if (fileMap == null || fileMap.size() <= 0)
            return;
        for (Map.Entry<String, File> entry : fileMap.entrySet())
            fileAsyn(url, heard, params, entry.getKey(), entry.getValue(), cls, listener);
    }
}
