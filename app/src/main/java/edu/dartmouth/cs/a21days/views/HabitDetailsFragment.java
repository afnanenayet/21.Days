package edu.dartmouth.cs.a21days.views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import edu.dartmouth.cs.a21days.utilities.HabitUtility;
import ng.max.slideview.SlideView;

import static com.facebook.GraphRequest.TAG;

/**
 * View the details of an individual habit
 */

public class HabitDetailsFragment extends DialogFragment {
    // default request code
    private static final int PERMISSION_REQUEST_CODE = 0;
    // habit to view
    Habit mHabit;
    Geocoder geocoder;

    // TextViews to populate with habit information
    private TextView HabitName;
    private TextView Category;
    private TextView Priority;
    private TextView Completed;
    private TextView Left;
    private TextView Location;
    private Intent mService;

    // permission request string
    private String[] RequestString = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    // whether location check in is enabled
    private boolean locationcheckin = false;
    // whether can check in or not
    private boolean enablecheckin = true;

    //Get the current location and compare it with the setting location
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            if (mHabit.getLocation()!=null)
                CheckLocation((Location)i.getParcelableExtra(TrackingService.KEY_LOCATION));
        }
    };

    // position of the habit
    private int position;
    // instance of database helper
    private HabitDataSource dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get positionof habit and habit instance
        position = getArguments().getInt("Position");
        mHabit = getHabitFromDB(position);

    }

    // Retrieve habit from database using thread
    private Habit getHabitFromDB(final int position){
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbHelper = HabitDataSource.getInstance("example");
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
        if (locationcheckin){
            getActivity().stopService(mService);
            Log.d(TAG, "onPause: Stop Service");
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        // build the dialog
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

        // check for location permissions
        checkPermission();

        // if habit has a location, update booleans as needed
        if (mHabit.isHasLocation()) {
            locationcheckin = true;
            enablecheckin = false;
        }

        // get the TextViews
        HabitName = (TextView)view.findViewById(R.id.details_habit_name);
        Category = (TextView)view.findViewById(R.id.details_category_textview);
        Completed = (TextView)view.findViewById(R.id.detail_days_completed);
        Left = (TextView)view.findViewById(R.id.detail_days_left);
        Priority = (TextView)view.findViewById(R.id.details_priority_textview);
        Location = (TextView)view.findViewById(R.id.details_location_textview);

        // slider used to check in with
        SlideView slide = (SlideView) view.findViewById(R.id.slideView);
        slide.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                    //Get the current date and store it to habit
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
                    int cdate = Integer.valueOf(df.format(c.getTime()));

                    // if allowed to check in for habit, check in
                    if ((cdate!=mHabit.getTimeStamp()) && enablecheckin) {
                        mHabit.setStreak(mHabit.getStreak() + 1);
                        mHabit.setTimeStamp(cdate);
                        SetupFragment();
                        Toast.makeText(getActivity(), "Congratulations! Check-in successful!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // if cannot check in, let user know
                        if (!enablecheckin)
                            Toast.makeText(getActivity(), "Check-in failed: Location incorrect",
                                    Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "Check-in failed: You have already checked in today",
                                    Toast.LENGTH_SHORT).show();

                    }
                    // add habit to db with newly updated info
                    AddToDBThread add = new AddToDBThread(mHabit);
                    add.run();
                }

        });

        // set up the fragment
        SetupFragment();

        return builder.create();
    }

    private void SetupFragment() {
        // set text values for the TextViews
        HabitName.setText(mHabit.getName());
        Category.setText(mHabit.getCategory());
        Priority.setText(HabitUtility.getPriorityString(getContext(),mHabit.getPriority()));
        if (mHabit.getStreak()<=21)
            Left.setText(String.valueOf(21 - mHabit.getStreak())+" Days");
        else
            Left.setText("0 Days");
        Completed.setText(String.valueOf(mHabit.getStreak())+" Days");

        // if location check in is enabled, try to get the location
        if (locationcheckin){
            Location mlocation = HabitUtility.latLngToLocation(mHabit.getLocation());
            try {
                geocoder = new Geocoder(getContext());
                String address = (geocoder.getFromLocation(mlocation.getLatitude(),mlocation.getLongitude(),1))
                        .get(0).getAddressLine(0);
                Location.setText(address);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Location.setText("No location check-in is required");
        }

    }


    //To check whether it's at the right location to check-in
    private void CheckLocation(Location CurrentLocation) {
        // get the location
        Location HabitLocation = HabitUtility.latLngToLocation(mHabit.getLocation());
        //If the current location is within 100 meters by the setting location,
        //Set the check-in ability as true
        if (HabitLocation.distanceTo(CurrentLocation)<100){
            EnableCheckin();
            Log.d(TAG, "CheckLocation: Allow Check-in");
        }

    }

    //To enable the check-in button
    private void EnableCheckin() {
        enablecheckin = true;
    }

    //To check location permission
    private void checkPermission() {
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), RequestString, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:{
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            RequestString, PERMISSION_REQUEST_CODE);
                }
                //All the request permissions must be granted or the app cannot work!
                //Keep asking the user if any request is denied
            }
        }

    }
}
