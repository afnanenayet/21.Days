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
import android.graphics.Color;
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

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.HabitDataSource;
import edu.dartmouth.cs.a21days.controllers.TrackingService;
import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.HabitUtility;

import static com.facebook.GraphRequest.TAG;

/**
 * Created by Steven on 3/2/17.
 */

public class HabitDetailsFragment extends DialogFragment implements IconRoundCornerProgressBar.OnIconClickListener {

    private static final int PERMISSION_REQUEST_CODE = 0;
    Habit mHabit;
    private TextView HabitName;
    private TextView Category;
    private TextView Priority;
    private TextView Completed;
    private TextView Left;
    private TextView Location;
    private IconRoundCornerProgressBar progress;
    private Intent mService;
    private String[] RequestString = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private boolean locationcheckin = false;
    private boolean enablecheckin = false;

    //Get the current location and compare it with the setting location
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            Log.d("TTAG", "onReceive: ");
            if (mHabit.getLocation()!=null)
                CheckLocation((Location)i.getParcelableExtra(TrackingService.KEY_LOCATION));
        }
    };

    private int position;
    private HabitDataSource dbHelper;

    public HabitDetailsFragment(){
        mHabit = dbHelper.getAll().get(0);
    }
    public HabitDetailsFragment(Habit habit) {
        mHabit = habit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt("Position");
        dbHelper = HabitDataSource.getInstance("example");
    }


    @Override
    public void onResume() {
        super.onResume();
        if (locationcheckin == true) {
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
        if (locationcheckin == true){
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
                // Delete the habit from teh data source
                ArrayList<Habit> habits = dbHelper.getAll();
                dbHelper.delete(habits.get(position).getId());
                dismiss();
            }
        });

        checkPermission();

        if (mHabit.getLocation()!=null)
            locationcheckin = true;

        HabitName = (TextView)view.findViewById(R.id.details_habit_name);
        Category = (TextView)view.findViewById(R.id.details_category_textview);
        Completed = (TextView)view.findViewById(R.id.detail_days_completed);
        Left = (TextView)view.findViewById(R.id.detail_days_left);
        Priority = (TextView)view.findViewById(R.id.details_priority_textview);
        Location = (TextView)view.findViewById(R.id.details_location_textview);
        progress = (IconRoundCornerProgressBar) view.findViewById(R.id.detail_progress);

        progress.setProgressColor(Color.parseColor("#56d2c2"));
        progress.setProgressBackgroundColor(Color.parseColor("#757575"));
        progress.setIconBackgroundColor(Color.parseColor("#38c0ae"));
        progress.setMax(21);
        progress.setIconImageResource(R.drawable.check_icon);
        progress.setOnIconClickListener(this);

        SetupFragment();




        return builder.create();
    }

    private void SetupFragment() {
        HabitName.setText(mHabit.getName());
        Category.setText(mHabit.getCategory());
        Priority.setText(HabitUtility.getPriorityString(getContext(),mHabit.getPriority()));
        if (mHabit.getStreak()<=21)
            Left.setText(String.valueOf(21 - mHabit.getStreak())+" Days");
        else
            Left.setText("0 Days");
        Completed.setText(String.valueOf(mHabit.getStreak())+" Days");

        progress.setMax(21);
        progress.setProgress(mHabit.getStreak());

    }


    //To check whether it's at the right location to check-in
    private void CheckLocation(Location CurrentLocation) {

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
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), RequestString, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:{
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), RequestString, PERMISSION_REQUEST_CODE);
                }
                //All the request permissions must be granted or the app cannot work!
                //Keep asking the user if any request is denied
            }
        }

    }

    //Check-in Button Click listener
    @Override
    public void onIconClick() {
        mHabit.setStreak(mHabit.getStreak()+1);
        SetupFragment();
        Toast.makeText(getActivity(), "Congratulations! Check-in successful!", Toast.LENGTH_SHORT).show();
    }
}
