package edu.dartmouth.cs.a21days.controllers;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Created by Steven on 3/6/17.
 */

// Thread for adding habits to database
public class AddToDBThread extends Thread {

    private HabitDataSource dbHelper;
    private Habit habit;

    public AddToDBThread(Habit habit){
        this.habit = habit;
        dbHelper = HabitDataSource.getInstance("example");
    }

    @Override
    public void run(){
        // Get all habits
        ArrayList<Habit> habitList = dbHelper.getAll();
        ArrayList<String> habitNames = new ArrayList<>();
        // Make sure that habit isn't already in database
        for (Habit habit:habitList){
            habitNames.add(habit.getId());
        }
        // If it does, add the updated version and remove the old version
        if (habitNames.contains(habit.getId())){
            String id = habit.getId();
            habit.setId(dbHelper.put(habit));
            dbHelper.delete(id);
        }
        // Otherwise, just add it
        else {
            habit.setId(dbHelper.put(habit));
        }

    }
}
