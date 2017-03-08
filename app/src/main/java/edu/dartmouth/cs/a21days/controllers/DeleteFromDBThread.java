package edu.dartmouth.cs.a21days.controllers;

import android.util.Log;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.NotificationJob;

/**
 * thread for deleting habits from database
 */
public class DeleteFromDBThread extends Thread {
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
        // Delete the habit from the data source

        ArrayList<Habit> habits = dbHelper.getAll();
        // Cancel notification
        NotificationJob.cancelJob(habits.get(position).getId());
        Log.i(TAG, "run: notification deleted");
        dbHelper.delete(habits.get(position).getId());

    }
}
