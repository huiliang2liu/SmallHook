package com.http.down;

import android.content.Context;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * com.http.down
 * 2018/11/1 17:45
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
class DownFileImpl implements DownFile {
    private Context context;
    ExecutorService executorService = Executors.newFixedThreadPool(5);

    DownFileImpl(Context context) {
        this.context = context;
    }

    @Override
    public Down down(String url, int threads, File file) {
        return new DownImpl(url, threads, context, executorService, file);
    }

    @Override
    public Down down(String url, File file) {
        return down(url, 3, file);
    }
}
