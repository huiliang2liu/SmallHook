package com.login.tencent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.login.Listener;
import com.login.iface.ILogin;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.utils.LogUtil;

import java.util.ArrayList;


/**
 * com.util.result
 * 2018/9/27 17:47
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class TencentImpl implements IUiListener, ILogin {
    private static final String TAG = "TencentLogin";
    private Listener mLoginListener;
    private Activity mActivity;
    private String mName;

    public TencentImpl(Activity activity) {
        mActivity = activity;
        try {
            PackageManager packageManager = activity.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
            mName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
        } catch (Exception e) {
            LogUtil.e(TAG,"获取应用名出错了");
        }
    }

    public void activityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Activity.RESULT_OK) {
                com.tencent.tauth.Tencent.handleResultData(data, this);
            }
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
        TencentManager.getTencentManager().mTencent.login(mActivity, "all", this);
    }

    public void shareImage( String imageUrl, Listener listener) {
        this.mLoginListener = listener;
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mName);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        TencentManager.getTencentManager().mTencent.shareToQQ(mActivity, params, this);
    }

    public void share(String title, String imageUrl, String targetUrl, String summary, Listener listener) {
        mLoginListener = listener;
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mName);
        TencentManager.getTencentManager().mTencent.shareToQQ(mActivity, params, this);
    }

    public void shareImageToQzone(String title, String targetUrl, ArrayList<String> images, Listener listener) {
        mLoginListener = listener;
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, images);
        TencentManager.getTencentManager().mTencent.shareToQzone(mActivity, params, this);
    }

    public void shareToQzone(String title, String summary, String targetUrl, ArrayList<String> images, Listener listener) {
        mLoginListener = listener;
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, images);
        TencentManager.getTencentManager().mTencent.shareToQzone(mActivity, params, this);
    }

    private void publishImageToQzone(String summary, ArrayList<String> images, Listener listener) {
        mLoginListener = listener;
        Bundle params = new Bundle();
        params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, images);
        TencentManager.getTencentManager().mTencent.publishToQzone(mActivity, params, this);
    }

    private void publishVideoToQzone(String summary, String videoUrl, Listener listener) {
        mLoginListener = listener;
        Bundle params = new Bundle();
        params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, videoUrl);
        TencentManager.getTencentManager().mTencent.publishToQzone(mActivity, params, this);
    }
}
