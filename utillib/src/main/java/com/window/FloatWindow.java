package com.window;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.dialog.BaseDialog;
import com.utils.LogUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * com.window
 * 2018/10/12 16:20
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class FloatWindow extends BaseDialog {
    private final static String TAG = "FloatWindow";
    public static volatile Set<FloatWindow> windows = new HashSet<>();

    public FloatWindow(Context context) {
        super(context.getApplicationContext());
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mParams.gravity = Gravity.CENTER;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindow.setAttributes(mParams);
    }

    @Override
    public void show() {
        LogUtil.e(TAG, "show");
        if (show)
            return;
        windows.add(this);
        super.show();
        mParams.dimAmount = 0f;
        mParams.alpha = 1.0f;
        mWindow.setAttributes(mParams);

    }

    @Override
    public void dismiss() {
        if (!show)
            return;
        windows.remove(this);
        super.dismiss();
    }
}
