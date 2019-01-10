package com.list.adapter;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.animation.ViewEmbellish;
import com.utils.AnimatorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * com.witget
 * 2019/1/2 15:02
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    private List<T> objects;

    public BaseAdapter(AdapterView adapterView) {
        // TODO Auto-generated constructor stub
        objects = new ArrayList<T>();
        adapterView.setAdapter(this);
    }

    public void addItem(T t) {
        if (t == null)
            return;
        objects.add(t);
        notifyDataSetChanged();
    }

    public void addItem(List<T> ts) {
        if (ts == null || ts.size() <= 0)
            return;
        objects.addAll(ts);
        notifyDataSetChanged();
    }

    public void remove(T t) {
        if (t == null)
            return;
        if (objects.remove(t))
            notifyDataSetChanged();
    }

    public void remove(List<T> ts) {
        if (ts == null || ts.size() <= 0)
            return;
        if (objects.removeAll(ts))
            notifyDataSetChanged();
    }

    public void clean() {
        objects.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return super.getViewTypeCount();
    }

    @Override
    public T getItem(int arg0) {
        // TODO Auto-generated method stub
        return objects.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return getItem(arg0).hashCode();
    }

    @Override
    public View getView(int position, View converView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        int itemType = getItemViewType(position);
        if (converView == null)
            converView = getView(itemType);
        if (converView == null)
            return null;
        BaseTag<T> baseTag = (BaseTag<T>) converView.getTag();
        if (baseTag == null)
            baseTag = getViewHolder(itemType);
        baseTag.setView(converView);
        baseTag.setContext(objects.get(position));
        getAnimation(position, converView);
        return converView;
    }

    public abstract BaseTag<T> getViewHolder(int itemType);

    public abstract View getView(int itemType);

    public ObjectAnimator getAnimation(int position, View view) {
        ObjectAnimator animation = AnimatorUtil.translationY(new ViewEmbellish(view), position % 2 == 0 ? -1000 : 1000, 0, 300);
        return animation;
    }

    public static abstract class BaseTag<T> {
        protected View view;

        private void setView(View view) {
            // TODO Auto-generated constructor stub
            this.view = view;
            bindView();
        }

        public View findViewById(int viewId) {
            // TODO Auto-generated method stub
            if (view == null)
                return null;
            return view.findViewById(viewId);
        }

        public abstract void bindView();

        public abstract void setContext(T t);
    }
}