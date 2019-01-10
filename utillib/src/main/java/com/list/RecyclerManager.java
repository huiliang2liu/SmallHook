package com.list;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * com.list
 * 2019/1/2 16:13
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class RecyclerManager {
    public static GridLayoutManager grid(Context context, RecyclerView recyclerView, int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        return gridLayoutManager;
    }
    public static LinearLayoutManager verticalList(Context context, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        return linearLayoutManager;
    }
    public static LinearLayoutManager horizontalList(Context context, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        return linearLayoutManager;
    }
    public static StaggeredGridLayoutManager staggered(Context context, RecyclerView recyclerView,int spanCount) {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(manager);
        return manager;
    }
}
