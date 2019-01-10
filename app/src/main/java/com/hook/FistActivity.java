package com.hook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thread.PoolManager;


/**
 * com.hook
 * 2018/10/8 16:14
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class FistActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fist_layout);
        PoolManager.runUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FistActivity.this, TextActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
