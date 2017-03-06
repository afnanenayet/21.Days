package edu.dartmouth.cs.a21days.controllers;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.Globals;

/**
 * thread for deleting habits from database
 */
public class DeleteFromDBThread extends Thread {
    // db helper instance
    private HabitDataSource dbHelper;
    // habit instance
    private int position;

    // constructor
    public DeleteFromDBThread(int position){
        dbHelper = HabitDataSource.getInstance(Globals.userId);
        this.position = position;
    }


    @Override
    public void run(){
        // Delete the habit from the data source
        ArrayList<Habit> habits = dbHelper.getAll();
        dbHelper.delete(habits.get(position).getId());
    }
}
