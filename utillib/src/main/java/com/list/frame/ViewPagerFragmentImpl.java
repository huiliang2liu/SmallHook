package com.list.frame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.iface.IFragment;
import com.list.ViewPagerListener;

import java.util.List;

/**
 * com.list.frame
 * 2019/1/2 15:26
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public  class ViewPagerFragmentImpl implements IFragment,
        ViewPager.OnPageChangeListener {
    private List<? extends Fragment> fragments;
    private ViewPager viewPager;
    private int table = 0;
    private ViewPagerListener viewPagerListener;

    public ViewPagerFragmentImpl(List<? extends Fragment> fragments,
                                 ViewPager viewPager, FragmentPagerAdapter adapter) {
        this(fragments, viewPager, adapter, null);
    }

    public ViewPagerFragmentImpl(List<? extends Fragment> fragments,
                                 ViewPager viewPager, FragmentPagerAdapter adapter,
                                 ViewPagerListener viewPagerListener) {
        // TODO Auto-generated constructor stub
        this.fragments = fragments;
        this.viewPager = viewPager;
        this.viewPagerListener = viewPagerListener;
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        if (viewPager instanceof com.witget.ViewPager) {
            com.witget.ViewPager v = (com.witget.ViewPager) viewPager;
            v.setScanScroll(!isTable());
        }
        viewPager.setCurrentItem(table);
    }

    @Override
    public void setTable(int table) {
        // TODO Auto-generated method stub
        this.table = table;
        viewPager.setCurrentItem(table);
    }

    /**
     * 提前加载数量
     *
     * @return
     */
    public void setOffscreenPageLimit(int offscreenPageLimit) {
        if (viewPager != null)
            viewPager.setOffscreenPageLimit(offscreenPageLimit);// 设置预加载
    }

    /**
     * 设置是否可以滑动
     *
     * @return
     */
    public boolean isTable() {
        return false;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        if (fragments == null)
            return;
        viewPager.setCurrentItem(table);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        if (fragments == null)
            return;
        fragments.get(table).onPause();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        if (fragments == null)
            return;
        fragments.get(table).onPause();
        int odlTable = table;
        table = arg0;
        fragments.get(table).onResume();
        if (viewPagerListener != null)
            viewPagerListener.change(odlTable, table);
    }

}
