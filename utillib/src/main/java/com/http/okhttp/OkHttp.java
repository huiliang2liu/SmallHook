package com.http.okhttp;

import android.os.Environment;

import com.http.AbsHttp;
import com.http.FileHttp;
import com.http.ResponseObject;
import com.http.ResponseString;
import com.http.listen.ResponseObjectListener;
import com.http.listen.ResponseStringListener;
import com.json.Json;
import com.thread.PoolManager;
import com.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * com.http.okhttp
 * 2018/10/17 17:46
 * instructions：okhttp网络请求
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class OkHttp extends AbsHttp implements FileHttp {
    private final static String TAG = "OkHttp";
    private static final long cacheSize = 1024 * 1024 * 20;// 缓存文件最大限制大小20M
    private String cacheDirectory = Environment.getExternalStorageDirectory() + "/okttpcaches"; // 设置缓存文件路径
    private Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //
    private OkHttpClient okHttp;
    private Map<Object, List<Call>> callMap = new HashMap<>();

     {
        //如果无法生存缓存文件目录，检测权限使用已经加上，检测手机是否把文件读写权限禁止了
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS); // 设置连接超时时间
        builder.writeTimeout(8, TimeUnit.SECONDS);// 设置写入超时时间
        builder.readTimeout(8, TimeUnit.SECONDS);// 设置读取数据超时时间
//         retryAndFollowUpInterceptor
//         RetryIntercepter
        builder.retryOnConnectionFailure(true);// 设置进行连接失败重试
        builder.cache(cache);// 设置缓存
         okHttp = builder.build();

    }


    @Override
    public ResponseString get(RequestEntity entity) {
        Request request = builder(url(entity.url, entity.params), entity.heard).build();
        StringCallback callback = new StringCallback(entity.tag, this, null);
        Call call = okHttp.newCall(request);
        put(entity.tag, call);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public ResponseObject getObject(RequestEntity entity) {
        Request request = builder(url(entity.url, entity.params), entity.heard).build();
        ObjectCallback callback = new ObjectCallback(entity.tag, this, null, entity.cls);
        Call call = okHttp.newCall(request);
        put(entity.tag, call);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public ResponseString post(RequestEntity entity) {
        Request request = null;
        if (entity.raw != null && !entity.raw.isEmpty())
            request = builder(entity.url, entity.heard).post(requestBody(entity.raw, entity.type)).build();
        else
            request = builder(entity.url, entity.heard).post(requestBody(entity.params)).build();
        StringCallback callback = new StringCallback(entity.tag, this, null);
        Call call = okHttp.newCall(request);
        put(entity.tag, call);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public ResponseObject postObject(RequestEntity entity) {
        Request request = null;
        if (entity.raw != null && !entity.raw.isEmpty())
            request = builder(entity.url, entity.heard).post(requestBody(entity.raw, entity.type)).build();
        else
            request = builder(entity.url, entity.heard).post(requestBody(entity.params)).build();
        ObjectCallback callback = new ObjectCallback(entity.tag, this, null, entity.cls);
        Call call = okHttp.newCall(request);
        put(entity.tag, call);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public void getAsyn(RequestEntity entity) {
        Request request = builder(url(entity.url, entity.params), entity.heard).build();
        StringCallback callback = new StringCallback(entity.tag, this, entity.stringListener);
        Call call = okHttp.newCall(request);
        put(entity.tag, call);
        call.enqueue(callback);
    }

    @Override
    public void getObjectAsyn(RequestEntity entity) {
        Request request = builder(url(entity.url, entity.params), entity.heard).build();
        ObjectCallback callback = new ObjectCallback(entity.tag, this, entity.objectListener, entity.cls);
        Call call = okHttp.newCall(request);
        put(entity.tag, call);
        call.enqueue(callback);
    }

    @Override
    public void postAsyn(RequestEntity entity) {
        Request request = null;
        if (entity.raw != null && !entity.raw.isEmpty())
            request = builder(entity.url, entity.heard).post(requestBody(entity.raw, entity.type)).build();
        else
            request = builder(entity.url, entity.heard).post(requestBody(entity.params)).build();
        StringCallback callback = new StringCallback(entity.tag, this, entity.stringListener);
        Call call = okHttp.newCall(request);
        put(entity.tag, call);
        call.enqueue(callback);
    }

    @Override
    public void postObjectAsyn(RequestEntity entity) {
        Request request = null;
        if (entity.raw != null && !entity.raw.isEmpty())
            request = builder(entity.url, entity.heard).post(requestBody(entity.raw, entity.type)).build();
        else
            request = builder(entity.url, entity.heard).post(requestBody(entity.params)).build();
        ObjectCallback callback = new ObjectCallback(entity.tag, this, entity.objectListener, entity.cls);
        Call call = okHttp.newCall(request);
        put(entity.tag, call);
        call.enqueue(callback);
    }

    synchronized void put(Object tag, Call call) {
        List<Call> calls = callMap.get(tag);
        if (calls != null) {
            calls.add(call);
            return;
        }
        calls = new ArrayList<>();
        calls.add(call);
        callMap.put(tag, calls);
    }

    synchronized void remove(Object tag, Call call) {
        List<Call> calls = callMap.get(tag);
        if (calls == null || calls.size() <= 0)
            return;
        int index = calls.indexOf(call);
        if (index >= 0)
            calls.remove(index);
        if (calls.size() <= 0)
            callMap.remove(tag);
    }

    @Override
    public synchronized void cancle(Object tag) {
        List<Call> calls = callMap.get(tag);
        if (calls == null || calls.size() <= 0)
            return;
        for (Call call : calls) {
            LogUtil.i(TAG, "取消请求");
            call.cancel();
        }

        callMap.remove(tag);
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file) {
        Request request = builder(url, heard).post(requestBody(params, fileKey, file)).build();
        StringCallback callback = new StringCallback(null, null, null);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls) {
        Request request = builder(url, heard).post(requestBody(params, fileKey, file)).build();
        ObjectCallback callback = new ObjectCallback(null, null, null, cls);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, ResponseStringListener listener) {
        Request request = builder(url, heard).post(requestBody(params, fileKey, file)).build();
        StringCallback callback = new StringCallback(null, null, listener);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls, ResponseObjectListener listener) {
        Request request = builder(url, heard).post(requestBody(params, fileKey, file)).build();
        ObjectCallback callback = new ObjectCallback(null, null, listener, cls);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files) {
        Request request = builder(url, heard).post(requestBody(params, fileKey, files)).build();
        StringCallback callback = new StringCallback(null, null, null);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls) {
        Request request = builder(url, heard).post(requestBody(params, fileKey, files)).build();
        ObjectCallback callback = new ObjectCallback(null, null, null, cls);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, ResponseStringListener listener) {
        Request request = builder(url, heard).post(requestBody(params, fileKey, files)).build();
        StringCallback callback = new StringCallback(null, null, listener);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls, ResponseObjectListener listener) {
        Request request = builder(url, heard).post(requestBody(params, fileKey, files)).build();
        ObjectCallback callback = new ObjectCallback(null, null, listener, cls);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
    }

    @Override
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap) {
        Request request = builder(url, heard).post(requestBody(params, fileMap)).build();
        StringCallback callback = new StringCallback(null, null, null);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls) {
        Request request = builder(url, heard).post(requestBody(params, fileMap)).build();
        ObjectCallback callback = new ObjectCallback(null, null, null, cls);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
        return callback.get();
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, ResponseStringListener listener) {
        Request request = builder(url, heard).post(requestBody(params, fileMap)).build();
        StringCallback callback = new StringCallback(null, null, listener);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
    }

    @Override
    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls, ResponseObjectListener listener) {
        Request request = builder(url, heard).post(requestBody(params, fileMap)).build();
        ObjectCallback callback = new ObjectCallback(null, null, listener, cls);
        Call call = okHttp.newCall(request);
        call.enqueue(callback);
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

    private ResponseString execute(Request request) {
        ResponseString rs = new ResponseString();
        try {
            response2string(new OkHttpClient().newCall(request).execute(), rs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private void execute(Request request, final ResponseStringListener listener) {
        LogUtil.i(TAG, "执行网络请求");
        PoolManager.runUiThread(new Runnable() {
            @Override
            public void run() {
                if (listener != null)
                    listener.start();
            }
        });
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            WeakReference<ResponseStringListener> listenerWeakReference = new WeakReference<>(listener);

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i(TAG, "onFailure");
                PoolManager.runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listenerWeakReference.get() != null)
                            listenerWeakReference.get().failure();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                LogUtil.i(TAG, "onResponse");
                final ResponseString rs = new ResponseString();
                response2string(response, rs);
                PoolManager.runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listenerWeakReference.get() != null) {
                            listenerWeakReference.get().success(rs);
                        }
                    }
                });
            }
        });
    }

    private ResponseObject execute(Request request, Class cls) {
        ResponseObject ro = new ResponseObject();
        try {
            response2object(new OkHttpClient().newCall(request).execute(), ro, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ro;
    }

    private void execute(Request request, final Class cls, final ResponseObjectListener listener) {
        PoolManager.runUiThread(new Runnable() {
            @Override
            public void run() {
                if (listener != null)
                    listener.start();
            }
        });
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PoolManager.runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null)
                            listener.failure();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final ResponseObject ro = new ResponseObject();
                response2object(response, ro, cls);
                PoolManager.runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null)
                            listener.success(ro);
                    }
                });
            }
        });
    }

    private RequestBody requestBody(String raw, String type) {
        MediaType mt = MediaType.parse(String.format("application/%s; charset=utf-8", type));
        return RequestBody.create(mt, raw);
    }

    private RequestBody requestBody(Map<String, String> params, String fileKey, File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM).addFormDataPart(fileKey, file.getName(), RequestBody.create(MediaType.parse("file/*"), file));
        if (params != null && params.size() > 0)
            for (String key : params.keySet())
                builder.addFormDataPart(key, params.get(key));
        return builder.build();
    }

    private RequestBody requestBody(Map<String, String> params, String fileKey, List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (params != null && params.size() > 0)
            for (String key : params.keySet())
                builder.addFormDataPart(key, params.get(key));
        if (files != null && files.size() > 0)
            for (File file : files)
                builder.addFormDataPart(fileKey, file.getName(), RequestBody.create(MediaType.parse("file/*"), file));
        return builder.build();
    }

    private RequestBody requestBody(Map<String, String> params, Map<String, File> fileMap) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (params != null && params.size() > 0)
            for (String key : params.keySet())
                builder.addFormDataPart(key, params.get(key));
        if (fileMap != null && fileMap.size() > 0)
            for (String key : fileMap.keySet())
                builder.addFormDataPart(key, fileMap.get(key).getName(), RequestBody.create(MediaType.parse("file/*"), fileMap.get(key)));
        return builder.build();
    }

    private RequestBody requestBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            for (String key : params.keySet())
                builder.add(key, params.get(key));
        }
        return builder.build();
    }

    private void response2string(Response response, ResponseString rs) {
        if (!response.isSuccessful())
            return;
        rs.code = response.code();
        try {
            rs.response = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int size = response.headers().size();
        if (size <= 0)
            return;
        Map<String, String> heard = new HashMap<>();
        for (int i = 0; i < size; i++) {
            heard.put(response.headers().name(i), response.headers().value(i));
        }
        rs.heard = heard;
    }

    private <T> void response2object(Response response, ResponseObject ro, Class<T> cls) {
        if (!response.isSuccessful())
            return;
        ro.code = response.code();
        ro.response = Json.parasJson(response.body().byteStream(), cls);
        int size = response.headers().size();
        if (size <= 0)
            return;
        Map<String, String> heard = new HashMap<>();
        for (int i = 0; i < size; i++) {
            heard.put(response.headers().name(i), response.headers().value(i));
        }
        ro.heard = heard;
    }

    private Request.Builder builder(String url, Map<String, String> heard) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (heard != null && heard.size() > 0) {
            for (String key : heard.keySet())
                builder.addHeader(key, heard.get(key));
        }
        return builder;
    }
}
