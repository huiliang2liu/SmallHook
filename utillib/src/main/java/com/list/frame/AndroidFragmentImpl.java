package com.list.frame;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;

import com.iface.IFragment;

import java.util.List;


/**
 * com.list.frame
 * 2019/1/2 15:22
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public  class AndroidFragmentImpl implements IFragment {
    private FragmentManager fragmentManager;
    private int table = 0;
    private List<? extends Fragment> fragments;

    public AndroidFragmentImpl(Activity activity, List<? extends Fragment> fragments, int groupId) {
        // TODO Auto-generated constructor stub
        this(activity.getFragmentManager(), fragments, groupId);
    }

    public AndroidFragmentImpl(Fragment fragment, List<? extends Fragment> fragments, int groupId) {
        // TODO Auto-generated constructor stub
        this(Build.VERSION.SDK_INT >= 17 ? fragment.getChildFragmentManager() : fragment.getFragmentManager(), fragments, groupId);
    }

    public AndroidFragmentImpl(FragmentManager fragmentManager, List<? extends Fragment> fragments, int groupId) {
        // TODO Auto-generated constructor stub
        this.fragmentManager = fragmentManager;
        this.fragments = fragments;
        if (fragments != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            for (Fragment baseAndroidFragment : fragments) {
                transaction.add(groupId, baseAndroidFragment);
            }
            transaction.commit();
        }
    }

    @Override
    public void setTable(int table) {
        // TODO Auto-generated method stub
        if (fragments == null || fragments.size() == 0)
            return;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (table > 0) {
            Fragment baseFragmentTab = fragments.get(table);
            transaction.hide(baseFragmentTab);
            baseFragmentTab.onPause();
        }
        if (fragments.size() > table) {
            this.table = table;
            Fragment baseFragmentTab = fragments.get(table);
            transaction.show(baseFragmentTab);
            if (isSave())
                transaction.addToBackStack(null);
            transaction.commit();
            baseFragmentTab.onResume();
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        setTable(table);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (table != -1) {
            Fragment baseFragmentTab = fragments.get(table);
            transaction.hide(baseFragmentTab);
            baseFragmentTab.onPause();
            transaction.commit();
        }
    }

    protected boolean isSave() {
        // TODO Auto-generated method stub
        return false;
    }
}
