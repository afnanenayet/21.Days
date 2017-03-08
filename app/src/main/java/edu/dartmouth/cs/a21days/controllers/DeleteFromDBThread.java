package edu.dartmouth.cs.a21days.controllers;

import android.util.Log;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.NotificationJob;

/**
 * Thread for deleting habits from the database.
 */
public class DeleteFromDBThread extends Thread {
    // debugging tag
    private String TAG = "DeleteFromDBThread";

    // db helper instance
    private HabitDataSource dbHelper;
    // habit instance
    private int position;

    // constructor
    public DeleteFromDBThread(int position){
        dbHelper = HabitDataSource.getInstance("example");
        this.position = position;
    }


    @Override
    public void run(){
        // get all habits from database
        ArrayList<Habit> habits = dbHelper.getAll();
        // Cancel notification
        NotificationJob.cancelJob(habits.get(position).getId());

        Log.i(TAG, "run: notification deleted");

        // delete the habit
        dbHelper.delete(habits.get(position).getId());

    }
}
