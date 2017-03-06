package edu.dartmouth.cs.a21days.controllers;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;

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
        dbHelper = HabitDataSource.getInstance("example");
        this.position = position;
    }


    @Override
    public void run(){
        // Delete the habit from the data source
        ArrayList<Habit> habits = dbHelper.getAll();
        dbHelper.delete(habits.get(position).getId());
    }
}
