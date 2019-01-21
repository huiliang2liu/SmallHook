package com.login;

/**
 * com.login
 * 2019/1/21 15:58
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public interface LoginListener {
    //登录回调

    /**
     * 2019/1/21 14:57
     * annotation：成功登录回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public void success(String result);

    /**
     * 2019/1/21 14:57
     * annotation：失败登录回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public void error();

    /**
     * 2019/1/21 14:57
     * annotation：取消登录回调
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    public void cancel();
}
