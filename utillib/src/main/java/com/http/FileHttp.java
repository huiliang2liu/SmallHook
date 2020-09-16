package com.http;

import com.http.listen.ResponseObjectListener;
import com.http.listen.ResponseStringListener;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * com.http
 * 2018/10/18 14:47
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public interface FileHttp {
    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file);

    public ResponseString file(String url, Map<String, String> params, String fileKey, File file);

    public ResponseString file(String url, String fileKey, File file);

    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls);

    public ResponseObject file(String url, Map<String, String> params, String fileKey, File file, Class cls);

    public ResponseObject file(String url, String fileKey, File file, Class cls);

    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, ResponseStringListener listener);

    public void fileAsyn(String url, Map<String, String> params, String fileKey, File file, ResponseStringListener listener);

    public void fileAsyn(String url, String fileKey, File file, ResponseStringListener listener);

    public void fileAsyn(String url, String fileKey, File file);

    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, File file, Class cls, ResponseObjectListener listener);

    public void fileAsyn(String url, Map<String, String> params, String fileKey, File file, Class cls, ResponseObjectListener listener);

    public void fileAsyn(String url, String fileKey, File file, Class cls, ResponseObjectListener listener);

    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files);

    public ResponseString file(String url, Map<String, String> params, String fileKey, List<File> files);

    public ResponseString file(String url, String fileKey, List<File> files);

    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls);

    public ResponseObject file(String url, Map<String, String> params, String fileKey, List<File> files, Class cls);

    public ResponseObject file(String url, String fileKey, List<File> files, Class cls);

    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, ResponseStringListener listener);

    public void fileAsyn(String url, Map<String, String> params, String fileKey, List<File> files, ResponseStringListener listener);

    public void fileAsyn(String url, String fileKey, List<File> files, ResponseStringListener listener);

    public void fileAsyn(String url, String fileKey, List<File> files);

    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, String fileKey, List<File> files, Class cls, ResponseObjectListener listener);

    public void fileAsyn(String url, Map<String, String> params, String fileKey, List<File> files, Class cls, ResponseObjectListener listener);

    public void fileAsyn(String url, String fileKey, List<File> files, Class cls, ResponseObjectListener listener);

    public ResponseString file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap);

    public ResponseString file(String url, Map<String, String> params, Map<String, File> fileMap);

    public ResponseString file(String url, Map<String, File> fileMap);

    public ResponseObject file(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls);

    public ResponseObject file(String url, Map<String, String> params, Map<String, File> fileMap, Class cls);

    public ResponseObject file(String url, Map<String, File> fileMap, Class cls);

    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, ResponseStringListener listener);

    public void fileAsyn(String url, Map<String, String> params, Map<String, File> fileMap, ResponseStringListener listener);

    public void fileAsyn(String url, Map<String, File> fileMap, ResponseStringListener listener);

    public void fileAsyn(String url, Map<String, File> fileMap);

    public void fileAsyn(String url, Map<String, String> heard, Map<String, String> params, Map<String, File> fileMap, Class cls, ResponseObjectListener listener);

    public void fileAsyn(String url, Map<String, String> params, Map<String, File> fileMap, Class cls, ResponseObjectListener listener);

    public void fileAsyn(String url, Map<String, File> fileMap, Class cls, ResponseObjectListener listener);

    class Build {
        public FileHttp build() {
            return (FileHttp) Proxy.newProxyInstance(FileHttp.class.getClassLoader(), new Class[]{FileHttp.class}, new InvocationHandler() {
                FileHttp http = new FileHttpImpl();

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(http, args);
                }
            });
        }
    }
}
