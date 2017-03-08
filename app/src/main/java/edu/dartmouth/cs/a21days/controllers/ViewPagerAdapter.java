package edu.dartmouth.cs.a21days.controllers;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Adapter for fragments in the Main Activity.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    // list of fragments
    private ArrayList<Fragment> fragments;

    // Tab enumerations
    public static final int HABITS = 0;
    public static final int ANALYTICS = 1;
    public static final int SETTINGS = 2;


    // constructor
    public ViewPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragmentList){
        super(fragmentManager);
        this.fragments = fragmentList;
    }

    // get tab position
    public Fragment getItem(int pos){
        return fragments.get(pos);
    }

    // get number of tabs
    public int getCount(){
        return fragments.size();
    }

}

