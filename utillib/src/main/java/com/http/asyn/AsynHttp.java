package com.http.asyn;

import com.http.AbsHttp;
import com.http.FileHttp;
import com.http.ResponseObject;
import com.http.ResponseString;
import com.http.listen.ResponseObjectListener;
import com.http.listen.ResponseStringListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * com.http.asyn
 * 2018/10/18 17:14
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class AsynHttp extends AbsHttp implements FileHttp {
    Map<Object, List<RequestHandle>> handleMap = new HashMap<>();
    AsyncHttpClient httpClient = new AsyncHttpClient();

    @Override
    public ResponseString get(RequestEntity entity) {
        AysnResponseString ars = new AysnResponseString(entity.tag, this);
        RequestHandle handle = getClient(entity.heard).get(url(entity.url, entity.params), ars);
        ars.setHandle(handle);
        put(entity.tag, handle);
        return ars.get();
    }

    @Override
    public ResponseObject getObject(RequestEntity entity) {
        AysnResponseObject aro = new AysnResponseObject(entity.cls, entity.tag, this);
        RequestHandle handle = getClient(entity.heard).get(entity.url, new RequestParams(entity.params), aro);
        aro.setHandle(handle);
        put(entity.tag, handle);
        return aro.get();
    }

    @Override
    public ResponseString post(RequestEntity entity) {
        AysnResponseString ars = new AysnResponseString(entity.tag, this);
        RequestHandle handle = null;
        if (entity.raw != null && !entity.raw.isEmpty()) {
            handle = getClient(entity.heard).post(null, entity.url, new RawEntity(entity.raw, entity.type), null, ars);
        } else
            handle = getClient(entity.heard).post(entity.url, new RequestParams(entity.params), ars);
        ars.setHandle(handle);
        put(entity.tag, handle);
        return ars.get();
    }

    @Override
    public ResponseObject postObject(RequestEntity entity) {
        AysnResponseObject aro = new AysnResponseObject(entity.cls, entity.tag, this);
        RequestHandle handle = null;
        if (entity.raw != null && !entity.raw.isEmpty()) {
            handle = getClient(entity.heard).post(null, entity.url, new RawEntity(entity.raw, entity.type), null, aro);
        } else
            handle = getClient(entity.heard).post(entity.url, new RequestParams(entity.params), aro);
        aro.setHandle(handle);
        put(entity.tag, handle);
        return aro.get();
    }

    @Override
    public void getAsyn(RequestEntity entity) {
        AysnResponseString ars = new AysnResponseString(entity.tag, this);
        ars.setListener(entity.stringListener);
        RequestHandle handle = getClient(entity.heard).get(url(entity.url, entity.params), ars);
        ars.setHandle(handle);
        put(entity.tag, handle);
    }

    @Override
    public void getObjectAsyn(RequestEntity entity) {
        AysnResponseObject aro = new AysnResponseObject(entity.cls, entity.tag, this);
        aro.setListener(entity.objectListener);
        RequestHandle handle = getClient(entity.heard).get(entity.url, new RequestParams(entity.params), aro);
        aro.setHandle(handle);
        put(entity.tag, handle);
    }

    @Override
    public void postAsyn(RequestEntity entity) {
        AysnResponseString ars = new AysnResponseString(entity.tag, this);
        RequestHandle handle = null;
        if (entity.raw != null && !entity.raw.isEmpty()) {
            handle = getClient(entity.heard).post(null, entity.url, new RawEntity(entity.raw, entity.type), null, ars);
        } else
            handle = getClient(entity.heard).post(entity.url, new RequestParams(entity.params), ars);
        ars.setHandle(handle);
        put(entity.tag, handle);
    }

    @Override
    public void postObjectAsyn(RequestEntity entity) {
        AysnResponseObject aro = new AysnResponseObject(entity.cls, entity.tag, this);
        aro.setListener(entity.objectListener);
        RequestHandle handle = null;
        if (entity.raw != null && !entity.raw.isEmpty()) {
            handle = getClient(entity.heard).post(null, entity.url, new RawEntity(entity.raw, entity.type), null, aro);
        } else
            handle = getClient(entity.heard).post(entity.url, new RequestParams(entity.params), aro);
        aro.setHandle(handle);
        put(entity.tag, handle);
    }

    @Override
    public synchronized void cancle(Object tag) {
        List<RequestHandle> handles = handleMap.get(tag);
        if (handles == null || handles.size() <= 0)
            return;
        for (RequestHandle handle : handles)
            handle.cancel(true);
        handleMap.remove(tag);
    }


    AsyncHttpClient getClient(Map<String, String> heard) {
        if (heard != null) {
            Iterator<String> keys = heard.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                httpClient.addHeader(key, heard.get(key));
            }
        }
        return httpClient;
    }

    private synchronized void put(Object tag, RequestHandle handle) {
        List<RequestHandle> handles = handleMap.get(tag);
        if (handles != null)
            handles.add(handle);
        else {
            handles = new ArrayList<>();
            handles.add(handle);
            handleMap.put(tag, handles);
        }
    }

    synchronized void remove(Object tag, RequestHandle handle) {
        List<RequestHandle> handles = handleMap.get(tag);
        if (handles != null && handles.size() > 0) {
            int index = handles.indexOf(handle);
            if (index >= 0)
                handles.remove(index);
            if (handles.size() <= 0)
                handleMap.remove(tag);
        }
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
    public void fileAsyn(String url, Map<String, File> fileMap, Class cls, ResponseObjectListener listener) {
        fileAsyn(url, null, fileMap, cls, listener);
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file) {
        AysnResponseString ars = new AysnResponseString();
        RequestParams rp = new RequestParams(params);
        try {
            rp.put(fileKey, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getClient(heard).post(url, rp, ars);
        return ars.get();
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls) {
        AysnResponseObject aro = new AysnResponseObject(cls);
        RequestParams rp = new RequestParams(params);
        try {
            rp.put(fileKey, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getClient(heard).post(url, rp, aro);
        return aro.get();
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, ResponseStringListener listener) {
        AysnResponseString ars = new AysnResponseString();
        ars.setListener(listener);
        RequestParams rp = new RequestParams(params);
        try {
            rp.put(fileKey, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getClient(heard).post(url, rp, ars);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls, ResponseObjectListener listener) {
        AysnResponseObject aro = new AysnResponseObject(cls);
        RequestParams rp = new RequestParams(params);
        try {
            rp.put(fileKey, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        aro.setListener(listener);
        getClient(heard).post(url, rp, aro);
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files) {
        AysnResponseString ars = new AysnResponseString();
        RequestParams rp = new RequestParams(params);
        try {
            rp.put(fileKey, files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getClient(heard).post(url, rp, ars);
        return ars.get();
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls) {
        AysnResponseObject aro = new AysnResponseObject(cls);
        RequestParams rp = new RequestParams(params);
        try {
            rp.put(fileKey, files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getClient(heard).post(url, rp, aro);
        return aro.get();
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, ResponseStringListener listener) {
        AysnResponseString ars = new AysnResponseString();
        RequestParams rp = new RequestParams(params);
        try {
            rp.put(fileKey, files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ars.setListener(listener);
        getClient(heard).post(url, rp, ars);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls, ResponseObjectListener listener) {
        AysnResponseObject aro = new AysnResponseObject(cls);
        RequestParams rp = new RequestParams(params);
        try {
            rp.put(fileKey, files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        aro.setListener(listener);
        getClient(heard).post(url, rp, aro);
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap) {
        AysnResponseString ars = new AysnResponseString();
        RequestParams rp = new RequestParams(params);
        if (fileMap != null && fileMap.size() > 0)
            try {
                for (Map.Entry<String, File> entry : fileMap.entrySet())
                    rp.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        getClient(heard).post(url, rp, ars);
        return ars.get();
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls) {
        AysnResponseObject aro = new AysnResponseObject(cls);
        RequestParams rp = new RequestParams(params);
        if (fileMap != null && fileMap.size() > 0)
            try {
                for (Map.Entry<String, File> entry : fileMap.entrySet())
                    rp.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        getClient(heard).post(url, rp, aro);
        return aro.get();
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, ResponseStringListener listener) {
        AysnResponseString ars = new AysnResponseString();
        RequestParams rp = new RequestParams(params);
        if (fileMap != null && fileMap.size() > 0)
            try {
                for (Map.Entry<String, File> entry : fileMap.entrySet())
                    rp.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        ars.setListener(listener);
        getClient(heard).post(url, rp, ars);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls, ResponseObjectListener listener) {
        AysnResponseObject aro = new AysnResponseObject(cls);
        RequestParams rp = new RequestParams(params);
        if (fileMap != null && fileMap.size() > 0)
            try {
                for (Map.Entry<String, File> entry : fileMap.entrySet())
                    rp.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        aro.setListener(listener);
        getClient(heard).post(url, rp, aro);
    }
}
