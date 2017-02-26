package edu.dartmouth.cs.a21days.controllers;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    // Tab positions and names
    public static final int HABITS = 0;
    public static final int ANALYTICS = 1;
    public static final int SETTINGS = 2;


    // View Pager Adapter constructor
    public ViewPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragmentList){
        super(fragmentManager);
        this.fragments = fragmentList;
    }

    // Function for getting tab position
    public Fragment getItem(int pos){
        return fragments.get(pos);
    }

    // Function for getting number of tabs
    public int getCount(){
        return fragments.size();
    }

}

