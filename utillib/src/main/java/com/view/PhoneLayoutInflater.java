package com.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author xh E-mail:825378291@qq.com
 * @version 创建时间：2017-4-13 上午9:54:49
 */
@SuppressLint("NewApi")
class PhoneLayoutInflater extends LayoutInflater {
    private final static String TAG="PhoneLayoutInflater";
    private final static int DESIGN_WIDTH = 1080;
    private final static int DESIGN_HEIGHT = 1920;
    private int width;
    private int height;
    private float factorWidth;
    private float factorHeight;

    {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        factorWidth = width * 1.0f / DESIGN_WIDTH;
        factorHeight = height * 1.0f / DESIGN_HEIGHT;
    }

    // 内置View类型的前缀，如TextView的完整路径是android.widget.TextView
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
    };
    private static final int[] COLOR = {
            Color.rgb(0x99, 0x54, 0x1d), Color.rgb(0xf0, 0x0a, 0x06), Color.rgb(0x11, 0x39, 0x65)
    };

    /**
     * Instead of instantiating directly, you should retrieve an instance
     * through {@link Context#getSystemService}
     *
     * @param context The Context in which in which to find resources and other
     *                application-specific things.
     * @see Context#getSystemService
     */
    public PhoneLayoutInflater(Context context) {
        super(context);
    }

    protected PhoneLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    /**
     * Override onCreateView to instantiate names that correspond to the
     * widgets known to the Widget factory. If we don't find a match,
     * call through to our super class.
     */
    @Override
    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        // 在View名字的前面添加前缀来构造View的完整路径，例如，类名为TextView，
        // 那么TextView的完整路径是android.widget.TextView
//        LogUtil.e(TAG,"onCreateView name="+name);
        for (String prefix : sClassPrefixList) {
            try {
                View view = createView(name, prefix, attrs);
                if (view != null) {
                    return view;
                }
            } catch (ClassNotFoundException e) {
                // In this case we want to let the base class take a crack
                // at it.
            }
        }

        return super.onCreateView(name, attrs);
    }

    public LayoutInflater cloneInContext(Context newContext) {
        return new PhoneLayoutInflater(this, newContext);
    }

    @Override
    protected void setLayoutParams(ViewGroup.LayoutParams params) {
//        LogUtil.e(TAG,"setLayoutParams");
        super.setLayoutParams(params);
        if (params.width != ViewGroup.LayoutParams.MATCH_PARENT && params.width != ViewGroup.LayoutParams.FILL_PARENT && params.width != ViewGroup.LayoutParams.WRAP_CONTENT)
            params.width *= factorWidth;
        if (params.height != ViewGroup.LayoutParams.MATCH_PARENT && params.height != ViewGroup.LayoutParams.FILL_PARENT && params.height != ViewGroup.LayoutParams.WRAP_CONTENT)
            params.height *= factorHeight;
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) params;
            mlp.leftMargin *= factorWidth;
            mlp.rightMargin *= factorWidth;
            mlp.topMargin *= factorHeight;
            mlp.bottomMargin *= factorHeight;
        }
        if (params instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) params;
//            lp.getRules()
        }
    }

    @Override
    protected void setView(View view) {
        super.setView(view);
        view.setPadding((int) (view.getPaddingLeft() * factorWidth), (int) (view.getPaddingTop() * factorHeight), (int) (view.getPaddingRight() * factorWidth), (int) (view.getPaddingBottom() * factorHeight));
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextSize(tv.getTextSize() * (factorWidth > factorHeight ? factorHeight : factorWidth));
        }
    }
}
