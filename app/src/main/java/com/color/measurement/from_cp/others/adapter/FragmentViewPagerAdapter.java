package com.color.measurement.from_cp.others.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by wpc on 2016/11/8.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> mFragments;
    ArrayList<String> titles;


    public FragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments=new ArrayList<>();
        titles=new ArrayList<>();
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addItem(Fragment fragment, String title) {
        mFragments.add(fragment);
        titles.add(title);
    }
}
