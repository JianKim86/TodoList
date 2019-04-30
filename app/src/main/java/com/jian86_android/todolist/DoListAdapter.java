package com.jian86_android.todolist;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DoListAdapter extends FragmentPagerAdapter {
    Fragment[] frags = new Fragment[3];
    String[] title = new String[]{"TODO","SHOPPING","CALENDAR"};
    public DoListAdapter(FragmentManager fm) {
        super(fm);
        frags[0] = new Page1Frag_Todo();
        frags[1] = new Page2Frag_Shopping();
        frags[2] = new Page3Frag_Calendar();
    }//constructor

    @Override
    public Fragment getItem(int position) {
        return frags[position];
    }//getItem

    @Override
    public int getCount() {
        return frags.length;
    }//getCount

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}//DoListAdapter
