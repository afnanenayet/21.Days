package edu.dartmouth.cs.a21days.controllers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.ViewPagerAdapter;
import edu.dartmouth.cs.a21days.views.AnalyticsFragment;
import edu.dartmouth.cs.a21days.views.HabitsListFragment;
import edu.dartmouth.cs.a21days.views.SettingsFragment;

/**
 * The Main controller for the application. Utilizes a bottom navigation bar
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private BottomNavigationView mBottomNavigationView;
    private ArrayList<Fragment> mFragments;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up bottom navigation view
        mBottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        // Making list of fragments to swipe through
        mFragments = new ArrayList<>();
        mFragments.add(new HabitsListFragment());
        mFragments.add(new AnalyticsFragment());
        mFragments.add(new SettingsFragment());

        // Viewpage adapter so we can swipe through tabs with the bottom layout
        mViewPageAdapter = new ViewPagerAdapter(getFragmentManager(), mFragments);
        mViewPager.setAdapter(mViewPageAdapter);

        // Allows us to set the selected item in bottompagelistener
        mViewPager.addOnPageChangeListener(this);

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


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Empty function required to implement OnPageChangedListener
    }

    // Set bottom bar nav item to be selected when viewpager is swiped/selected
    @Override
    public void onPageSelected(int position) {
        Menu menu = mBottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // Empty function required to implement OnPageChangedListener
    }
}
