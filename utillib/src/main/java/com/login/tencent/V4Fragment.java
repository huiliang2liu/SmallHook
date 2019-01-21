package com.login.tencent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.login.Listener;
import com.login.iface.ILogin;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


/**
 * com.util.result
 * 2018/9/27 17:47
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class V4Fragment extends Fragment implements IUiListener, ILogin {
    private static final String TAG = "TencentLogin";


    private Listener mLoginListener;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Activity.RESULT_OK) {
                Tencent.handleResultData(data, this);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


//登录回调

    /**
     * 2019/1/21 14:57
     * annotation：成功登录回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    @Override
    public void onComplete(Object o) {
        if (mLoginListener != null)
            mLoginListener.success(o.toString());
    }

    /**
     * 2019/1/21 14:57
     * annotation：失败登录回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    @Override
    public void onError(UiError uiError) {
        if (mLoginListener != null)
            mLoginListener.error();
    }

    /**
     * 2019/1/21 14:57
     * annotation：取消登录回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    @Override
    public void onCancel() {
        if (mLoginListener != null)
            mLoginListener.cancel();
    }

    @Override
    public void login(Listener loginListener) {
        this.mLoginListener = loginListener;
        TencentManager.getTencentManager().mTencent.login(this, "all", this);
    }
    public void shareImage(String appName,String imageUrl,Listener listener) {
        this.mLoginListener=listener;
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        TencentManager.getTencentManager().mTencent.shareToQQ(getActivity(), params, this);
    }
    public void share(String title,String appName,String imageUrl,String targetUrl,String summary) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  appName);
        TencentManager.getTencentManager().mTencent.shareToQQ(getActivity(), params, this);
    }
}
