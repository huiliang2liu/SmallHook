package com.hook;

import com.http.http.HttpManage;
import com.http.http.request.FlowRequest;
import com.http.http.response.Response;
import com.keep.alive.KeepAliveManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.utils.ContentManager;
import com.utils.LogUtil;
import com.utils.StreamUtil;
import com.window.FloatWindow;

import java.util.HashSet;
import java.util.Set;

/**
 * com.hook
 * 2018/9/20 19:25
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class MyApplication extends com.hook.Application implements ScreenStateListener {
    private Set<FloatWindow> windows;
    public static IWXAPI mWxApi;

    @Override
    public void onCreate() {
        super.onCreate();
        ContentManager.getManager().setHookActivity("com.hook.TextActivity");
        registerScreenStateListener(this);
        KeepAliveManager.start(this, 10000, AliveService.class.getName());
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, "", false);
        // 将该app注册到微信
        mWxApi.registerApp("");
    }


    @Override
    public void foreground() {
        if (windows != null) {
            for (FloatWindow floatWindow : windows)
                floatWindow.show();
        }
    }

    @Override
    public void background() {
        if (FloatWindow.windows.size() > 0) {
            windows = new HashSet<>(FloatWindow.windows);
            for (FloatWindow floatWindow : windows)
                floatWindow.dismiss();
        }
    }

    @Override
    protected void saveErr(String err) {
        super.saveErr(err);
        new Thread() {
            @Override
            public void run() {
                super.run();
                FlowRequest request = new FlowRequest("7y4bbDeMkt727Kd2sqQYTnCpdebm9Wgw2bUt6offpQGUqdSuRwaqRRuKdNnU s52JEsG8RTAMYdpiOttHb1UvbnhrCdlNwR1r8R7SRoj4cimFWJ7iq827ZsFJ F+UEixGNwE9n6EELtKKFIix7NbrV44ErL+QAye6OPqem2Cd/GPRWCZmfBPgj y60DRdOpCGF8pPljzyiddfW098hn3KbqRq/JLr+1G8I/yLjDUeaXb0PTP/v+ kSnx9fn7DM/Fe7LWHFCUukWN/tzMOFbNdV0be82JoAl8EcQ7DZsa/sPiBru8 7lnMYw0sXjwcvJ348rKZj+LIBT2UCZ4DvsC0t4ya09dLd6WNvorNxAbTOJhZ TyiB1zCR5veC1ufmVWT9c5xVZaUv9lYr/mBw4XPdz4j05n0hllg4FRzKM+4m 0LpDxsvjfLnbLZM4t3vuXK/tUQA7H7veJw/olLmZu4auAysr9GCsIBBcszvk sC994EPwnjmeMSek7o/2UrszFBkUvpJ5DxRI1dgmYI3QlafurVM7Y+GXD3hc I+9xBoGdvV8W5LfOhlRkOR4gfEzSx3NeVBAvspLCUihtKALEILO8njNH8wK+ Q1tH71vkm1q3/yJvaQZdue2yee5MD3751B1jhVeTjd790LaW8ArVQtw9afZk yb8FgCsdkDcDLwBkhKZmNFRENjdvuGkyYIOBcgVk+l4Y3rYqWUY27CGtMpHy JhkRR7BorNMAX8oyM95R3WDMGZYHU+cQl6epFY3hEO+VtpyTMlOBtRnvnjkZ HIK1Tjq+ZU3Ymeloh5VV/PzddXd5bDTIBgXbHkoZo6T/bN1ku9PLehHsqQ8Z tkf2Z7M1mHL/APVKhcdvysLD1f9MXNcypWWUIVO2eMTHnfkFgKaPh7UciUvS /SbN1yrq0hDWV6t66ofrxBX62MfwMG1zd6Zkt4+lC+pwb24U9g==");
                request.setPath("http://ssp-test.tvblack.com:8083/sdk");
                Response response = HttpManage.response(request);
                LogUtil.e(StreamUtil.stream2string(response.getInputStream()));
            }
        }.start();
    }
}
