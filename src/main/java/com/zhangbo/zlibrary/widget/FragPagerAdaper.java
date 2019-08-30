package com.zhangbo.zlibrary.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Author: zhangbo
 * Data：2018/11/29
 * TODO:
 */
public class FragPagerAdaper extends FragmentPagerAdapter {

    List<Fragment> mFragments;
    String[] mTitles;

    public FragPagerAdaper(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public FragPagerAdaper(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments==null?0:mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitles!=null)
            return mTitles[position];

        return super.getPageTitle(position);
    }
}
