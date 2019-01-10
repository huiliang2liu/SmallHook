package com.hook;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.RemoveListener;
import com.image.ImageLoad;
import com.net.NetCallback;
import com.reflect.ClassManager;
import com.reflect.FieldManager;
import com.result.Result;
import com.sava.AndroidFileUtil;
import com.thread.Handler;
import com.thread.PoolManager;
import com.utils.ApkUtil;
import com.utils.ContentManager;
import com.utils.LogUtil;
import com.utils.StreamUtil;
import com.view.FloatView;
import com.view.LayoutInflater;
import com.witget.CountdownView;
import com.witget.FullScreenView;
import com.xml.ProvinceManager;

import java.io.File;
import java.io.IOException;


/**
 * com.hook
 * 2018/10/12 12:00
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class WelcomeActivity extends Activity implements NetCallback, RemoveListener {
    private final static String TAG = "WelcomeActivity";
    private FullScreenView mFullScreenView;
    private ImageView view;
    private View view1;
    private View view2;
    private Result result;
    private CountdownView countdownView;
    private int pro = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(this).inflate(R.layout.activity_welcome, null));
        ContentManager.getManager().registerNetCallback(this);
//        startService(new Intent(this, ServiceA.class));
        TextView tv = findViewById(R.id.signer);
        tv.setText("sha1:" + ApkUtil.sHA1(this) + "\nhash:" + ApkUtil.getHash(this));
        view = findViewById(R.id.text);
        view1 = findViewById(R.id.text1);
        view2 = findViewById(R.id.text2);
        countdownView = findViewById(R.id.progress);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                countdownView.setProgress(pro);
                pro++;
                if (pro < 101)
                    sendEmptyMessageDelayed(0, 1000);
            }
        };
        handler.sendEmptyMessage(0);
        mFullScreenView = new FullScreenView(new FloatView(this));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFullScreenView.enter(view);
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFullScreenView.enter(view1);
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFullScreenView.enter(view2);
            }
        });
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
        imageLoad.load(view, "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1541041501&di=d045ddcde5e5732ddd43dc86c5b63ce5&src=http://img17.3lian.com/d/file/201703/07/fc03b55448ee1e068c7132007c80abc2.jpg");
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
