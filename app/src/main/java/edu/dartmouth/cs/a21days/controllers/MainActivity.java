package edu.dartmouth.cs.a21days.controllers;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.evernote.android.job.JobManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.Globals;
import edu.dartmouth.cs.a21days.utilities.NotificationJobCreator;
import edu.dartmouth.cs.a21days.utilities.PermissionsListener;
import edu.dartmouth.cs.a21days.views.AnalyticsFragment;
import edu.dartmouth.cs.a21days.views.HabitsListFragment;
import edu.dartmouth.cs.a21days.views.SettingsFragment;

/**
 * The Main controller for the application. Utilizes a bottom navigation bar.
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    // bottom navigation bar
    private BottomNavigationView mBottomNavigationView;
    // list of fragments attached to main activity
    private ArrayList<Fragment> mFragments;
    // ViewPager and adapter
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPageAdapter;
    public HabitListviewAdapter adapter;

    // tag for debugging use
    private static final String DEBUG_TAG = "MainActivity";

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

        // ViewPage adapter so we can swipe through tabs with the bottom layout
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

        PermissionsListener permissionsListener = new PermissionsListener(this);

        // TODO implement permissions when necessary
        /* Dexter.withActivity(this)
                .withPermissions()
                .withListener(permissionsListener)
                .check(); */

        // Setting up binding for Dexter to request permissions
        Dexter.withActivity(this).continueRequestingPendingPermissions(permissionsListener);

        // Initialize Google Fit connection
        // connectToGoogleFit(); // todo get Fitness APIs

        // Initializing database with user
        HabitDataSource.getInstance(Globals.userId);

        // Initializing bindings for evernote job creator library
        JobManager.create(this).addJobCreator(new NotificationJobCreator());

        // Listview adapter needs new context on every orientation change because it outlives
        // MainActivity
        HabitsListFragment.getInstance().refreshAdapter(this);
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

    /**
     * Shows rationale dialog in order to request permissions
     */
    public void showPermissionRationale(final PermissionToken token) {
        new AlertDialog.Builder(this).setTitle(R.string.permission_rationale_title)
                .setMessage(R.string.permission_rationale_message)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        token.cancelPermissionRequest();
                    }
                })
                .show();
    }

    /**
     * Connects to Google Fit
     */
    private void connectToGoogleFit() {
        GoogleFitController fitController = new GoogleFitController(this);
        fitController.buildFitnessClient();
    }
}
