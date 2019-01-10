package com.http.http.task;

import android.os.Build;

import com.http.http.response.Response;
import com.http.http.util.Method;
import com.utils.LogUtil;
import com.utils.StreamUtil;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;


/**
 * threadPool com.xh.http 2018 2018-4-27 下午5:58:56 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class InputStreamTask extends ATask {
    private final static String TAG = "InputStreamTask";

    @Override
    public Response connection() {
        LogUtil.i(TAG, "开始网络请求");
        // TODO Auto-generated method stub
        Response response = new Response();
        if (mRequest == null) {
            response.setCode(500);
            response.setError("request is null");
        }
        response.setRequest(mRequest);
        try {
            Method method = mRequest.getMethod();
            String path = mRequest.getPath();
            HttpURLConnection connection = (HttpURLConnection) new URL(path)
                    .openConnection();
            if (path.startsWith("https")) {
                HttpsURLConnection https = (HttpsURLConnection) connection;
                HostnameVerifier verifier = mRequest.getVerifier();
                SSLSocketFactory factory = mRequest.getFactory();
                if (verifier != null)
                    https.setHostnameVerifier(verifier);
                if (factory != null)
                    https.setSSLSocketFactory(factory);
            }
            connection.setConnectTimeout(mRequest.getConnectTime());
            connection.setReadTimeout(mRequest.getReadTimeOut());
            connection.setInstanceFollowRedirects(mRequest.isRedirect());
            connection.setRequestMethod(method.getMethod());
            connection.setUseCaches(mRequest.isCaches());
            connection.setDoInput(true);
            if (method == Method.GET)
                connection.setDoOutput(false);
            else {
                connection.setDoOutput(true);
            }
            Map<String, String> head = mRequest.getHead();
            if (head != null && head.size() > 0) {
                Iterator<Entry<String, String>> iterator = head.entrySet()
                        .iterator();
                while (iterator.hasNext()) {
                    Entry<String, String> entry = iterator.next();
                    connection.setRequestProperty(entry.getKey(),
                            entry.getValue());
                }
            }
//            if (Build.VERSION.SDK_INT >= 24)
//                response.setLen(connection.getContentLengthLong());
//            else
//                response.setLen(connection.getContentLength());
            connection.connect();
            if (method == Method.POST) {
                OutputStream os = connection.getOutputStream();
                if (os != null) {
                    mRequest.report(os);
                    os.flush();
                    try {
                        os.close();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
            int code = connection.getResponseCode();
            response.setCode(code);
            response.setResponseHead(connection.getHeaderFields());
            if (code < 400) {
                if (Build.VERSION.SDK_INT >= 24)
                    response.setLen(connection.getContentLengthLong());
                else
                    response.setLen(connection.getContentLength());
                response.setInputStream(connection.getInputStream());
            } else {
                response.setError(StreamUtil.stream2string(
                        connection.getErrorStream(), mRequest.getCharset()));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            response.setCode(500);
            response.setError(e == null ? "network request failed" : e
                    .getMessage());
            e.printStackTrace();
        }
        return response;
    }

    protected void putHead() {

    }
}
