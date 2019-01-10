package com.http.down;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * com.http.down
 * 2018/11/1 17:45
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public interface DownFile {

    public Down down(String url, int threads, File file);

    public Down down(String url, File file);


    class Build {
        private Context context;

        public Build context(@NonNull Context context) {
            this.context = context;
            return this;
        }

        public DownFile build() {
            if (context == null)
                throw new NullPointerException("context is null");
            return (DownFile) Proxy.newProxyInstance(DownFile.class.getClassLoader(), new Class[]{DownFile.class}, new InvocationHandler() {
                DownFile downFile = new DownFileImpl(context);

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(downFile, args);
                }
            });
        }
    }
}
