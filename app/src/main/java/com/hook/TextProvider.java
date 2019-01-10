package com.hook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.utils.LogUtil;


/**
 * com.hook
 * 2018/10/26 10:07
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class TextProvider extends ContentProvider {
    private final static String TAG = "TextProvider";
    private final static String authority="com.hook.text_provider";
    {
        LogUtil.e(TAG,"构造函数");
    }
    @Override
    public boolean onCreate() {
        LogUtil.e(TAG, "创建");
        return true;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        LogUtil.e(TAG,"attachInfo"+Process.myPid());

        super.attachInfo(context, info);

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        LogUtil.e(TAG,"插入数据");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
