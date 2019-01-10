package com.http.http.response;

import com.http.http.request.Request;
import com.utils.StreamUtil;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * threadPool com.xh.http.response 2018 2018-4-27 下午7:20:37 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class Response {
    private int code;
    private String error;
    protected Request request;
    private long len;
    protected Map<String, List<String>> responseHead;
    private InputStream is;

    public InputStream getInputStream() {
        return is;
    }

    public String getString() {
        return StreamUtil.stream2string(is);
    }

    public String getString(String charset) {
        return StreamUtil.stream2string(is, charset);
    }

    public void setInputStream(InputStream is) {
        this.is = is;
    }

    public void setResponseHead(Map<String, List<String>> responseHead) {
        this.responseHead = responseHead;
    }

    public Map<String, String> heard() {
        if (responseHead == null || responseHead.size() <= 0)
            return Collections.emptyMap();
        Map<String, String> heard = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : responseHead.entrySet()) {
            String key = entry.getKey();
            List<String> list = entry.getValue();
            if (list == null || list.size() <= 0)
                continue;
            heard.put(entry.getKey(), list.get(0));
        }
        return heard;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
