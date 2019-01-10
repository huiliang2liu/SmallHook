package com.http.http;

import com.http.AbsHttp;
import com.http.FileHttp;
import com.http.ResponseObject;
import com.http.ResponseString;
import com.http.http.request.FileUploadRequest;
import com.http.http.request.FlowRequest;
import com.http.http.request.Request;
import com.http.http.util.Method;
import com.http.listen.ResponseObjectListener;
import com.http.listen.ResponseStringListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * com.http.http
 * 2018/10/18 17:58
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Http extends AbsHttp implements FileHttp {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    Map<Object, List<Future>> futureMap = new HashMap<>();

    @Override
    public ResponseString get(RequestEntity entity) {
        ResponseListenerString string = new ResponseListenerString(getRequest(entity.url, entity.heard, entity.params), null, executorService, this, entity.tag);
        return string.response();
    }

    @Override
    public ResponseObject getObject(RequestEntity entity) {
        ResponseListenerObject object = new ResponseListenerObject(getRequest(entity.url, entity.heard, entity.params), null, entity.cls, executorService, this, entity.tag);
        return object.response();
    }

    @Override
    public ResponseString post(RequestEntity entity) {
        ResponseListenerString string = new ResponseListenerString(postRequest(entity.url, entity.heard, entity.params), null, executorService, this, entity.tag);
        return string.response();
    }

    @Override
    public ResponseObject postObject(RequestEntity entity) {
        ResponseListenerObject object = new ResponseListenerObject(postRequest(entity.url, entity.heard, entity.params), null, entity.cls, executorService, this, entity.tag);
        return object.response();
    }

    @Override
    public void getAsyn(RequestEntity entity) {
        new ResponseListenerString(getRequest(entity.url, entity.heard, entity.params), entity.stringListener, executorService, this, entity.tag);
    }

    @Override
    public void getObjectAsyn(RequestEntity entity) {
        new ResponseListenerObject(getRequest(entity.url, entity.heard, entity.params), entity.objectListener, entity.cls, executorService, this, entity.tag);
    }

    @Override
    public void postAsyn(RequestEntity entity) {
        new ResponseListenerString(postRequest(entity.url, entity.heard, entity.params), entity.stringListener, executorService, this, entity.tag);
    }

    @Override
    public void postObjectAsyn(RequestEntity entity) {
        new ResponseListenerObject(postRequest(entity.url, entity.heard, entity.params), entity.objectListener, entity.cls, executorService, this, entity.tag);
    }

    @Override
    public synchronized void cancle(Object tag) {
        List<Future> futures = futureMap.get(tag);
        if (futures == null || futures.size() <= 0)
            return;
        for (Future future : futures)
            if (!future.isDone())
                future.cancel(true);
    }

    synchronized void add(Object tag, Future future) {
        List<Future> futures = futureMap.get(tag);
        if (futures != null) {
            futures.add(future);
            return;
        }
        futures = new ArrayList<>();
        futures.add(future);
        futureMap.put(tag, futures);
    }

    synchronized void remove(Object tag, Future future) {
        List<Future> futures = futureMap.get(tag);
        if (futures == null || futures.size() <= 0)
            return;
        int index = futures.indexOf(future);
        if (index >= 0)
            futures.remove(index);
        if (futures.size() <= 0)
            futureMap.remove(tag);
    }

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
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file) {
        ResponseListenerString rls = new ResponseListenerString(postRequest(url, heard, params, fileKey, file), null, executorService, this, url);
        return rls.response();
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls) {
        ResponseListenerObject rlo = new ResponseListenerObject(postRequest(url, heard, params, fileKey, file), null, cls, executorService, this, url);
        return rlo.response();
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, ResponseStringListener listener) {
        new ResponseListenerString(postRequest(url, heard, params, fileKey, file), listener, executorService, this, url);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls, ResponseObjectListener listener) {
        new ResponseListenerObject(postRequest(url, heard, params, fileKey, file), listener, cls, executorService, this, url);
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
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls) {
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
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls, ResponseObjectListener listener) {
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
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls) {
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
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls, ResponseObjectListener listener) {
        if (fileMap == null || fileMap.size() <= 0)
            return;
        for (Map.Entry<String, File> entry : fileMap.entrySet())
            fileAsyn(url, heard, params, entry.getKey(), entry.getValue(), cls, listener);
    }

    @Override
    public void fileAsyn(String url, Map<String, File> fileMap, Class cls, ResponseObjectListener listener) {
        fileAsyn(url, null, fileMap, cls, listener);
    }

    private Request getRequest(String url, Map<String, String> heard, Map<String, String> params) {
        Request request = new Request();
        request.addHead(heard);
        request.addParams(new HashMap<String, Object>(params));
        request.setMethod(Method.GET);
        request.setPath(url);
        return request;
    }

    private Request postRequest(String url, Map<String, String> heard, Map<String, String> params) {
        Request request = new Request();
        request.addHead(heard);
        request.addParams(new HashMap<String, Object>(params));
        request.setMethod(Method.POST);
        request.setPath(url);
        return request;
    }

    private Request postRequest(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file) {
        FileUploadRequest request = new FileUploadRequest(file);
        request.addHead(heard);
        request.addParams(new HashMap<String, Object>(params));
        request.setMethod(Method.POST);
        request.setFileKey(fileKey);
        request.setPath(url);
        return request;
    }

    private Request rawRequest(String url, Map<String, String> heard, String raw, String type) {
        Request request = new FlowRequest(raw);
        request.addHead(heard);
        request.setContentType(String.format("application/%s", type));
        request.setMethod(Method.POST);
        request.setPath(url);
        return request;
    }
}
