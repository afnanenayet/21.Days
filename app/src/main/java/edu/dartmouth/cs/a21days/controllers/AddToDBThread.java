package edu.dartmouth.cs.a21days.controllers;

import android.util.Log;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.HabitUtility;
import edu.dartmouth.cs.a21days.utilities.NotificationJob;

/**
 * Thread for adding habits to the database.
 */
public class AddToDBThread extends Thread {
    private String TAG = "AddToDBThread";
    // DB helper instance
    private HabitDataSource dbHelper;
    // habit instance
    private Habit habit;

    // constructor
    public AddToDBThread(Habit habit) {
        this.habit = habit;
        dbHelper = HabitDataSource.getInstance();
    }

    @Override
    public void run() {
        // Get all habits
        ArrayList<Habit> habitList = dbHelper.getAll();
        ArrayList<String> habitNames = new ArrayList<>();

        // Make sure that habit isn't already in database
        for (Habit habit : habitList) {
            habitNames.add(habit.getId());
        }
        // If habit does exist, add the updated version and remove the old version
        if (habitNames.contains(habit.getId())) {
            String id = habit.getId();
            habit.setId(dbHelper.put(habit));
            dbHelper.delete(id);
        }
        // Otherwise habit doesn't exist, so we add it
        else {
            habit.setId(dbHelper.put(habit));
            // Check if habit has a time set
            if (habit.getTime() != -1) {
                boolean[] days;

                // If no days are selected, assume habit should be done every day
                if (habit.getFrequency().size() == 0) {
                    days = new boolean[]{true, true, true, true, true, true, true};
                }
                // Otherwise get the specified days
                else {
                    ArrayList<Integer> daysArrayList = habit.getFrequency();
                    days = HabitUtility.daysToBoolArray(daysArrayList);
                }
                // get hour and min of when habit should be completed
                int habitMin = habit.getTime() % 100;
                int habitHour = habit.getTime() / 100;

                Log.i(TAG, "run: " + habit.getTime());
                Log.i(TAG, "run: " + habitMin + ", " + habitHour);

                // Schedule notification
                NotificationJob.scheduleJob(
                        habit.getId(),
                        habit.getName(),
                        "This reminder is brought to you by 21.Days",
                        habitHour,
                        habitMin,
                        days

                );
            }
        }
    }
}
