package com.hook;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.RemoveListener;
import com.iface.ISVG;
import com.image.ImageLoad;
import com.net.NetCallback;
import com.reflect.ClassManager;
import com.reflect.FieldManager;
import com.result.Result;
import com.sava.AndroidFileUtil;
import com.svg.SVGPars;
import com.svg.vector.VectorPars;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
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
        LogUtil.e(TAG,ApkUtil.sHA1(this));
        imageView = findViewById(R.id.image_view);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                SVGPars svgPars = SVGPars.init();

                try {
                    ISVG isvg=svgPars.paras(getResources(),R.raw.flag_usa);
                    imageView.setImageDrawable(isvg.createDrawable(imageView));
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
//        PoolManager.longTime(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    File file = AndroidFileUtil.getDatabasePath(WelcomeActivity.this, "test");
//                    StreamUtil.stream2file(getAssets().open("TvBlackAD_demo.apk"), new File(file, "TvBlackAD_demo.apk"));
//                    ContentManager.getManager().load(file);
//                    ContentManager.getManager().loadSo("ijkffmpeg", "ijksdl", "ijkplayer");
//                    FieldManager.setField(null, FieldManager.field(ClassManager.forName("tv.danmaku.ijk.media.player.IjkMediaPlayer"), "mIsLibLoaded"), true);
//                    ContentManager.getManager().paras(getAssets().open("activity.xml"));
//                    ProvinceManager.manager(WelcomeActivity.this);
//                    PoolManager.runUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            new TextFloatWindow(WelcomeActivity.this).show();
//                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
        //先判断是否安装微信APP,按照微信的说法，目前移动应用上微信登录只提供原生的登录方式，需要用户安装微信客户端才能配合使用。
        if (!isWXAppInstalledAndSupported(this,((MyApplication)getApplication()).mWxApi)) {
            return;
        }
        LogUtil.e(TAG,"已经安装了微信");
        wxLogin();
    }
    // 提示檢測微信端
    private static boolean isWXAppInstalledAndSupported(Context context,
                                                        IWXAPI api) {
        boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled();
        if (!sIsWXAppInstalledAndSupported) {
            LogUtil.e(TAG,"您还未安装微信客户端");
        }

        return sIsWXAppInstalledAndSupported;
    }
    //微信登录
    public void wxLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        //像微信发送请求
        MyApplication.mWxApi.sendReq(req);
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
