package com.login.weixin;

import android.content.Context;

import com.login.Listener;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.utils.LogUtil;

/**
 * com.login.weixin
 * 2019/1/21 11:41
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class WeiXinManager  {
    private static final String TAG = "WeiXinManager";
    private static final String TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    private static final String USER_INFO_URL = "https://api.weixin.qq.com//sns/userinfo?access_token=%s&openid=%s";
    public static WeiXinManager mWeiXinManager;
    IWXAPI mWxApi;
    private Context mContext;
    private String mAppId;
    private String mAppSecret;
    protected Listener mLoginListener;

    public static WeiXinManager init(Context context, String appId, String appSecret) {
        if (mWeiXinManager == null)
            synchronized (TAG) {
                if (mWeiXinManager == null)
                    mWeiXinManager = new WeiXinManager(context, appId, appSecret);
            }
        return mWeiXinManager;
    }

    public static WeiXinManager getWeiXinManager() {
        return mWeiXinManager;
    }

    private WeiXinManager(Context context, String appId, String appSecret) {
        mContext = context.getApplicationContext();
        mAppId = appId;
        mAppSecret = appSecret;
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(mContext, mAppId, false);
        // 将该app注册到微信
        mWxApi.registerApp(mAppId);
    }

    /**
     * 2019/1/21 11:48
     * annotation：发起登录请求
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public void login(Listener loginListener) {
        LogUtil.e(TAG, "发起登录请求");
        mLoginListener=loginListener;
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        mWxApi.sendReq(req);
    }

    /**
     * 2019/1/21 12:42
     * annotation：获取获取token的url
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    protected String getAccessTokenUrl(String code) {
        return String.format(TOKEN_URL, mAppId, mAppSecret, code);
    }

    /**
     * 获取用户信息的url
     *
     * @param access_token
     * @param openid
     * @param unionid
     */
    protected String getWXUserInfoUrl(final String access_token, final String openid, final String unionid) {
        return String.format(USER_INFO_URL, access_token, openid);
    }

}
