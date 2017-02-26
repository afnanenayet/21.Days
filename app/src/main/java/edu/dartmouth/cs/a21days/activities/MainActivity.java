package edu.dartmouth.cs.a21days.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.ViewPagerAdapter;
import edu.dartmouth.cs.a21days.views.AnalyticsFragment;
import edu.dartmouth.cs.a21days.views.HabitsListFragment;
import edu.dartmouth.cs.a21days.views.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private ArrayList<Fragment> mFragments;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mFragments = new ArrayList<Fragment>();

        mFragments.add(new AnalyticsFragment());
        mFragments.add(new HabitsListFragment());
        mFragments.add(new SettingsFragment());


        mViewPageAdapter = new ViewPagerAdapter(getFragmentManager(), mFragments);
        mViewPager.setAdapter(mViewPageAdapter);

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_analytics:
                                mViewPager.setCurrentItem(ViewPagerAdapter.ANALYTICS);
                                break;
                            case R.id.action_habits:
                                mViewPager.setCurrentItem(ViewPagerAdapter.HABITS);
                                break;
                            case R.id.action_settings:
                                mViewPager.setCurrentItem(ViewPagerAdapter.SETTINGS);
                                break;

                        }
                        return true;
                    }
                });
    }


}
