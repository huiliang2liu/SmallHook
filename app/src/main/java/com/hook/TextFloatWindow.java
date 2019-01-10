package com.hook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;

import com.utils.ContentManager;
import com.utils.LogUtil;
import com.window.FloatWindow;


/**
 * com.hook
 * 2018/10/12 17:40
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class TextFloatWindow extends FloatWindow {
    public TextFloatWindow(Context context) {
        super(context);
        setContentView(R.layout.float_window);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = ContentManager.getManager().getActivity();
                if (activity != null) {
                    Intent intent = new Intent(mContext, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                }

            }
        });
    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        LogUtil.e("TextFloatWindow","keyCode="+keyCode);
//        Activity activity = ContentManager.getManager().getActivity();
//        if (activity != null)
//            return activity.dispatchKeyEvent(event);
//        return super.onKey(v, keyCode, event);
//    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        Activity activity = ContentManager.getManager().getActivity();
        if (activity != null)
            return activity.dispatchKeyEvent(event);
        else
            LogUtil.e("activity is null");
        return super.dispatchKeyEvent(event);
    }

}
