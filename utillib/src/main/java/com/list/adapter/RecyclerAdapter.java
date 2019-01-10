package com.list.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * com.list.adapter
 * 2019/1/2 15:49
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter {
    private final static String TAG = "AbsRecyclerAdapter";
    private String mPackageName;
    protected Activity mActivity;
    private OnItemClickListener mItemClicklistener;
    private OnItemLongClickListener mItemLongClickListener;
    private T[] list;

    public void setOnItemClicklistener(OnItemClickListener mItemClicklistener) {
        this.mItemClicklistener = mItemClicklistener;
    }

    public void setOnItemLongClickListener(
            OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public RecyclerAdapter(Activity activity, T[] list) {
        if (list == null || list.length <= 0)
            throw new RuntimeException("you list is null or size=0");
        if (activity == null)
            throw new RuntimeException("activity is null");
        mActivity = activity;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    @NonNull
    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        return getHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                       final int position) {
        AbsViewHolder myViewHolder = (AbsViewHolder) holder;
        myViewHolder.instantiate(list[position], position);
        if (position == 1)
            myViewHolder.mView.setSelected(true);
        myViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClicklistener != null)
                    mItemClicklistener.onItemClick(v, position);
            }
        });
        myViewHolder.mView
                .setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mItemLongClickListener != null)
                            mItemLongClickListener.onItemLongClick(v, position);
                        return true;
                    }
                });
    }


    protected abstract AbsViewHolder<T> getHolder(ViewGroup parent, int viewType);

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public static abstract class AbsViewHolder<T> extends RecyclerView.ViewHolder {
        protected View mView;
        protected Activity mActivity;

        public AbsViewHolder(View itemView, Activity activity) {
            super(itemView);
            mView = itemView;
            mActivity = activity;
            initView();
        }


        protected abstract void initView();

        public abstract void instantiate(T t, int position);
    }
}
