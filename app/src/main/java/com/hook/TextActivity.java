package com.hook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thread.PoolManager;


/**
 * com.hook
 * 2018/9/27 18:04
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class TextActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_layout);
        PoolManager.runUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("test", "test");
                setResult(RESULT_OK, intent);
                finish();
            }
        }, 1000);

    }
}
