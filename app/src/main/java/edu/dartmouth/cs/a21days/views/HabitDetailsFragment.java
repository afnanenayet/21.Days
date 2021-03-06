package edu.dartmouth.cs.a21days.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.AddToDBThread;
import edu.dartmouth.cs.a21days.controllers.DeleteFromDBThread;
import edu.dartmouth.cs.a21days.controllers.HabitDataSource;
import edu.dartmouth.cs.a21days.controllers.TrackingService;
import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.Globals;
import edu.dartmouth.cs.a21days.utilities.HabitUtility;
import ng.max.slideview.SlideView;

import static com.facebook.GraphRequest.TAG;

/**
 * Show details of an individual habit
 */

public class HabitDetailsFragment extends DialogFragment {
    // database helper instance
    private HabitDataSource dbHelper;
    // the habit to display
    private Habit mHabit;
    // Geocoder to decode the address info
    private Geocoder geocoder;
    // TextView of habit name
    private TextView HabitName;
    // TextView of category
    private TextView Category;
    // TextView of priority
    private TextView Priority;
    // TextView of days completed
    private TextView Completed;
    // TextView of days left
    private TextView Left;
    // TextView of location
    private TextView Location;
    // Intent to start the tracking service
    private Intent mService;

    //Indicator of whether the location check in is required
    private boolean locationcheckin = false;
    //Indicator of whether check in is allowed, set to true by default
    private boolean enablecheckin = true;

    //Positon of the habit in the habit list
    private int position;

    //Get the current location and compare it with the setting location
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            if (mHabit.getLocation() != null)
                CheckLocation((Location) i.getParcelableExtra(TrackingService.KEY_LOCATION));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get habit
        position = getArguments().getInt("Position");
        mHabit = getHabitFromDB(position);
    }

    // Retrieve habit from database using thread
    private Habit getHabitFromDB(final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbHelper = HabitDataSource.getInstance(Globals.userId);
                ArrayList<Habit> habits = dbHelper.getAll();
                mHabit = habits.get(position);
            }
        }).start();
        return mHabit;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (locationcheckin) {
            mService = new Intent(getActivity(), TrackingService.class);
            getActivity().startService(mService);
            Log.d(TAG, "onResume: TrackingService Started");

            IntentFilter mFilter = new IntentFilter(TrackingService.TRACKING_ACTION);
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, mFilter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationcheckin) {
            getActivity().stopService(mService);
            Log.d(TAG, "onPause: Stop Service");
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_habit_detail, null);
        builder.setView(view);

        // Set on click listener for delete button
        Button deleteHabit = (Button) view.findViewById(R.id.delete_habit);
        deleteHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteFromDBThread delete = new DeleteFromDBThread(position);
                delete.run();
                dismiss();

            }
        });

        if (mHabit.isHasLocation()) {
            locationcheckin = true;
            enablecheckin = false;
        }

        // get the TextViews
        HabitName = (TextView) view.findViewById(R.id.details_habit_name);
        Category = (TextView) view.findViewById(R.id.details_category_textview);
        Completed = (TextView) view.findViewById(R.id.detail_days_completed);
        Left = (TextView) view.findViewById(R.id.detail_days_left);
        Priority = (TextView) view.findViewById(R.id.details_priority_textview);
        Location = (TextView) view.findViewById(R.id.details_location_textview);

        // set up slider for checking in to habit
        SlideView slide = (SlideView) view.findViewById(R.id.slideView);
        slide.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                //Get the current date and store it to habit
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                int cdate = Integer.valueOf(df.format(c.getTime()));

                // if can check in
                if ((cdate != mHabit.getTimeStamp()) && enablecheckin) {
                    mHabit.setStreak(mHabit.getStreak() + 1);
                    mHabit.setTimeStamp(cdate);
                    SetupFragment();
                    Toast.makeText(getActivity(), "Congratulations! Check-in successful!", Toast.LENGTH_SHORT).show();
                }
                // otherwise can't check in
                else {
                    if (!enablecheckin)
                        Toast.makeText(getActivity(), "Check-in failed: Location incorrect",
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Check-in failed: You have already checked in today",
                                Toast.LENGTH_SHORT).show();

                }
                // add updated habit to database
                AddToDBThread add = new AddToDBThread(mHabit);
                add.run();
            }

        });

        // set up UI elements of the fragment
        SetupFragment();

        return builder.create();
    }

    // Update the fragment view with data
    private void SetupFragment() {
        //Update habit info
        HabitName.setText(mHabit.getName());
        Category.setText(mHabit.getCategory());
        Priority.setText(HabitUtility.getPriorityString(getContext(), mHabit.getPriority()));

        if (mHabit.getStreak() <= 21)
            Left.setText(String.valueOf(21 - mHabit.getStreak()) + " Days");
        else
            Left.setText("0 Days");
        Completed.setText(String.valueOf(mHabit.getStreak()) + " Days");

        //If the location is right, decode the address
        if (locationcheckin) {
            Location mlocation = HabitUtility.latLngToLocation(mHabit.getLocation());
            try {
                geocoder = new Geocoder(getContext());
                String address = (geocoder.getFromLocation(mlocation.getLatitude(), mlocation.getLongitude(), 1))
                        .get(0).getAddressLine(0);
                Location.setText(address);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Location.setText("No location check-in is required");
        }

    }


    //To check whether it's at the right location to check-in
    private void CheckLocation(Location CurrentLocation) {
        // get location
        Location HabitLocation = HabitUtility.latLngToLocation(mHabit.getLocation());
        //If the current location is within 400 meters by the setting location,
        //Set the check-in ability as true
        if (HabitLocation.distanceTo(CurrentLocation) < 400) {
            EnableCheckin();
            Log.d(TAG, "CheckLocation: Allow Check-in");
        }

    }

    //To enable the check-in button
    private void EnableCheckin() {
        enablecheckin = true;
    }

}
