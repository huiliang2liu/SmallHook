package com.http;

import android.content.Context;

import com.http.listen.ResponseObjectListener;
import com.http.listen.ResponseStringListener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * com.http
 * 2018/10/17 15:35
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public interface Http {
    public ResponseString get(RequestEntity entity);

    public ResponseObject getObject(RequestEntity entity);

    public ResponseString post(RequestEntity entity);

    public ResponseObject postObject(RequestEntity entity);

    public void getAsyn(RequestEntity entity);

    public void getObjectAsyn(RequestEntity entity);

    public void postAsyn(RequestEntity entity);

    public void postObjectAsyn(RequestEntity entity);

    public void cancle(Object tag);



    class Build {
        private Context context;

        public Build context(Context context) {
            this.context = context;
            return this;
        }

        public Http build() {
            if (context == null)
                throw new NullPointerException("context is null");
            return (Http) Proxy.newProxyInstance(Http.class.getClassLoader(), new Class[]{Http.class}, new InvocationHandler() {
                Http http = new HttpImpl(context);

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(http, args);
                }
            });
        }
    }

    class RequestEntity {
        public String url;
        public Map<String, String> heard;
        public Map<String, String> params;
        public String raw;
        public String type = "json";
        public Class cls;
        public ResponseObjectListener objectListener;
        public ResponseStringListener stringListener;
        public Object tag = toString();

        public RequestEntity url(String url) {
            this.url = url;
            return this;
        }

        public RequestEntity tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public RequestEntity heard(Map<String, String> heard) {
            this.heard = heard;
            return this;
        }

        public RequestEntity params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public RequestEntity raw(String raw) {
            this.raw = raw;
            return this;
        }

        public RequestEntity type(String type) {
            this.type = type;
            return this;
        }

        public RequestEntity cls(Class cls) {
            this.cls = cls;
            return this;
        }

        public RequestEntity objectListener(ResponseObjectListener objectListener) {
            this.objectListener = objectListener;
            return this;
        }

        public RequestEntity stringListener(ResponseStringListener stringListener) {
            this.stringListener = stringListener;
            return this;
        }
    }
}
