package com.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.hook.PackageContext;
import com.utils.ContentManager;
import com.utils.LogUtil;


/**
 * com.dialog
 * 2018/10/19 16:30
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class BaseDialog extends Dialog {
    private final static String TAG = "BaseDialog";
    protected Window mWindow;
    protected WindowManager.LayoutParams mParams;
    protected Context mContext;
    protected boolean show;

    public BaseDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mWindow = getWindow();
        mParams = mWindow.getAttributes();
        mContext = context.getApplicationContext();
    }

    public BaseDialog(Context context, String packageName) {
        this(new PackageContext(context, ContentManager.getManager().name2resources(packageName)));
    }

    public void setGravity(int gravity) {
        mParams.gravity = gravity;
        mWindow.setAttributes(mParams);
    }

    public void setWidth(int width) {
        mParams.width = width;
        mWindow.setAttributes(mParams);
    }

    public void setHeight(int height) {
        mParams.height = height;
        mWindow.setAttributes(mParams);
    }

    @Override
    public void show() {
        LogUtil.e(TAG, "show");
        if (show)
            return;
        super.show();
//        mParams.dimAmount = 0f;
//        mParams.alpha = 1.0f;
//        mWindow.setAttributes(mParams);
        show = true;
    }

    @Override
    public void dismiss() {
        if (!show)
            return;
        super.dismiss();
        show = false;
    }
}
