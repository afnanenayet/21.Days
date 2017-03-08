package edu.dartmouth.cs.a21days.utilities;

/**
 * Created by aenayet on 3/8/17.
 */

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import edu.dartmouth.cs.a21days.controllers.AddToDBThread;
import edu.dartmouth.cs.a21days.controllers.GoogleFitController;
import edu.dartmouth.cs.a21days.controllers.HabitDataSource;
import edu.dartmouth.cs.a21days.controllers.HabitListviewAdapter;
import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Task to check if habit has been completed with completion criteria via Google Fit
 */
public class GoogleFitCompletionTask extends AsyncTask<Void, Void, Integer> {
    private static final String DEBUG_TAG = "GoogleFitCompletionTask";
    private ArrayList<Habit> habitArrayList;

    public GoogleFitCompletionTask() {
        Log.d(DEBUG_TAG, "Class init");
    }

    /**
     * Check habits to see if they have been completed via Google Fit and update database
     * accordingly
     */
    @Override
    protected Integer doInBackground(Void... params) {
        setHabitCompleted();
        return null;
    }

    // Refresh data in UI
    protected void onPostExecute(Integer params) {
        Log.d(DEBUG_TAG, "Updating UI with new data");
        updateUiData();
    }

    /**
     * Checking to see if a habit has been completed using data from Google Fit's API
     * If it has, setting habit to completed
     */
    public void setHabitCompleted() {
        Log.d(DEBUG_TAG, "Checking to see if habits are complete");
        GoogleFitController.UpdateData updateData = new GoogleFitController.UpdateData();
        updateData.start();

        try {
            updateData.join();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(DEBUG_TAG, "Failed to join thread");
        }

        Map map = GoogleFitController.resultMap;

        HabitDataSource dbHelper = HabitDataSource.getInstance();
        ArrayList<Habit> habits = dbHelper.getAll();

        // Cycling through each habit and checking to see if they have been completed
        for (Habit habit : habits) {
            // Getting current time
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            int cdate = Integer.valueOf(df.format(c.getTime()));

            // Checking to see if habit is eligible for check in
            if (map.size() != 0 && cdate != habit.getTimeStamp()) {
                boolean checkin = false;

                switch (habit.getGoogleFitType()) {
                    case Globals.DISTANCE_STRING:
                        Float distance = (Float) map.get(GoogleFitController.DISTANCE);
                        checkin = (distance >= habit.getGoogleFitValue());
                        break;
                    case Globals.STEPS_STRING:
                        Integer steps = (Integer) map.get(GoogleFitController.STEPS);
                        checkin = (steps >= habit.getGoogleFitValue());
                        break;
                }

                // If Google Fit data indicates that habit has been completed
                if (checkin) {
                    Log.d(DEBUG_TAG, "Habit is complete");

                    habit.setStreak(habit.getStreak() + 1);
                    habit.setTimeStamp(cdate);

                    // Updating in database
                    new AddToDBThread(habit).run();
                }
            }
        }

        habitArrayList = habits;
    }

    /**
     * Updates UI for data
     */
    public void updateUiData() {
        HabitListviewAdapter.getInstance().updateData(habitArrayList);
    }
}
