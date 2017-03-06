package edu.dartmouth.cs.a21days.controllers;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Created by Steven on 3/6/17.
 */


// Thread for deleting habits from the database
public class DeleteFromDBThread extends Thread {

    private HabitDataSource dbHelper;
    private int position;

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
