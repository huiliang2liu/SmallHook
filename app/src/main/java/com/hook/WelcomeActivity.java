package com.hook;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.RemoveListener;
import com.image.ImageLoad;
import com.net.NetCallback;
import com.reflect.ClassManager;
import com.reflect.FieldManager;
import com.result.Result;
import com.sava.AndroidFileUtil;
import com.svg.vector.VectorPars;
import com.thread.PoolManager;
import com.utils.ApkUtil;
import com.utils.ContentManager;
import com.utils.LogUtil;
import com.utils.StreamUtil;
import com.view.LayoutInflater;
import com.xml.ProvinceManager;

import java.io.File;
import java.io.IOException;


/**
 * com.hook
 * 2018/10/12 12:00
 * instructions：测试不输入密码
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class WelcomeActivity extends Activity implements NetCallback, RemoveListener {
    private final static String TAG = "WelcomeActivity";
    private Result result;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(this).inflate(R.layout.activity_welcome, null));
        ContentManager.getManager().registerNetCallback(this);
//        startService(new Intent(this, ServiceA.class));
        TextView tv = findViewById(R.id.signer);
        tv.setText("sha1:" + ApkUtil.sHA1(this) + "\nhash:" + ApkUtil.getHash(this));
        imageView = findViewById(R.id.image_view);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                VectorPars svgPars = VectorPars.init();
                try {
                    imageView.setImageDrawable(svgPars.parasString("M63,0.1A22.6,22.4 0,0 0,42.1 14.2,17.3 17.3,0 0,0 30.9,10.2 17.3,17.3 0,0 0,13.7 25.8,8.8 8.8,0 0,0 8.7,24.2 8.8,8.8 0,0 0,0 32h99a7.9,7.9 0,0 0,0 -0.6,7.9 7.9,0 0,0 -7.9,-7.9 7.9,7.9 0,0 0,-5.8 2.6,22.6 22.4,0 0,0 0.3,-3.6A22.6,22.4 0,0 0,63 0.1Z").createDrawable(imageView));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        countdownView = findViewById(R.id.progress);
//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                countdownView.setProgress(pro);
//                pro++;
//                if (pro < 101)
//                    sendEmptyMessageDelayed(0, 1000);
//            }
//        };
//        handler.sendEmptyMessage(0);
        ContentManager.getManager().registerRemoveListener(this);
        final ImageLoad imageLoad = new ImageLoad.Build().context(this).buidle();
        LogUtil.e(TAG, imageLoad.getClass().getName());
        PoolManager.longTime(new Runnable() {
            @Override
            public void run() {

                try {
                    File file = AndroidFileUtil.getDatabasePath(WelcomeActivity.this, "test");
                    StreamUtil.stream2file(getAssets().open("TvBlackAD_demo.apk"), new File(file, "TvBlackAD_demo.apk"));
                    ContentManager.getManager().load(file);
                    ContentManager.getManager().loadSo("ijkffmpeg", "ijksdl", "ijkplayer");
                    FieldManager.setField(null, FieldManager.field(ClassManager.forName("tv.danmaku.ijk.media.player.IjkMediaPlayer"), "mIsLibLoaded"), true);
                    ContentManager.getManager().paras(getAssets().open("activity.xml"));
                    ProvinceManager.manager(WelcomeActivity.this);
//                    PoolManager.runUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            new TextFloatWindow(WelcomeActivity.this).show();
//                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//                        }
//                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onAvailable() {
        LogUtil.w(TAG, "网络连接");
    }

    @Override
    public void onLost() {
        LogUtil.w(TAG, "网络断开");
    }

    @Override
    public void remove(String packageName) {
        LogUtil.w(TAG, "删除了 " + packageName);
    }
}
