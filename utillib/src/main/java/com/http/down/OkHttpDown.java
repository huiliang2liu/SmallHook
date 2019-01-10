package com.http.down;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * com.http.down
 * 2018/11/1 17:32
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
class OkHttpDown {

    OkHttpDown(final DownEntity downEntity, final Listener listener, String url) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.addHeader("Range", "bytes=" + downEntity.start + "-" + downEntity.end);
        final Request request = builder.build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                listener.success(response.body().byteStream(), downEntity);
            }
        });
    }
}
