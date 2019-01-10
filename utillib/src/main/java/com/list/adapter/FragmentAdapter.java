package com.list.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * com.list
 * 2019/1/2 15:15
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<?extends Fragment> fragments;

    public FragmentAdapter(FragmentActivity activity, List<?extends Fragment> fragments2) {
        // TODO Auto-generated constructor stub
        super(activity.getSupportFragmentManager());
        this.fragments = fragments2;
    }
    public FragmentAdapter(Fragment fragment, List<?extends Fragment> fragments2) {
        // TODO Auto-generated constructor stub
        super(fragment.getChildFragmentManager());
        this.fragments = fragments2;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return fragments.get(arg0);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return fragments == null ? 0 : fragments.size();
    }

}
