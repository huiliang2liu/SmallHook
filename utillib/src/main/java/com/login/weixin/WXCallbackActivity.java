package com.login.weixin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.http.Http;
import com.http.ResponseString;
import com.http.listen.ResponseStringListener;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.utils.ContentManager;
import com.utils.LogUtil;


/**
 * com.login.weixin
 * 2019/1/21 11:41
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class WXCallbackActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXCallbackActivity";
    private WeiXinManager mWeiXinManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG, "微信请求回调了");
        mWeiXinManager=WeiXinManager.getWeiXinManager();
        mWeiXinManager.mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.e(TAG, "微信请求相应");
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.e(TAG, "请求响应");
        LogUtil.e(TAG, "error_code:---->" + baseResp.errCode);
        int type = baseResp.getType(); //类型：分享还是登录
        Log.e(TAG, "type=" + type);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                refuse();
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                cancel();
                break;
            case BaseResp.ErrCode.ERR_OK:
                success(baseResp);
                break;
            default:
                refuse();
        }
        finish();
    }

    /**
     * 2019/1/21 12:17
     * annotation：拒绝回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    protected void refuse() {
        LogUtil.e(TAG, "用户拒绝授权");
    }

    /**
     * 2019/1/21 12:17
     * annotation：取消回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    protected void cancel() {
        LogUtil.e(TAG, "用户取消");
    }

    /**
     * 2019/1/21 12:18
     * annotation：成功回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    protected void success(BaseResp baseResp) {
        LogUtil.e(TAG, "用户同意");
        if (baseResp instanceof SendAuth.Resp) {
            LogUtil.e(TAG, "获取code");
            SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            String code = ((SendAuth.Resp) baseResp).code;
            LogUtil.e(TAG, "code=" + code);
            Http.RequestEntity entity=new Http.RequestEntity();
            entity.url( mWeiXinManager.getAccessTokenUrl(code));
            entity.stringListener(new ResponseStringListener() {
                @Override
                public void start() {

                }

                @Override
                public void failure() {

                }

                @Override
                public void success(ResponseString response) {
                  String string=response.response;
                }
            });
            ContentManager.getManager().getHttp().getAsyn(entity);
        }
    }
}
