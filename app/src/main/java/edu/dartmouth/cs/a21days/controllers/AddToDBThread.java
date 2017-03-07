package edu.dartmouth.cs.a21days.controllers;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;

/**
 * thread for adding habits to database
 */
public class AddToDBThread extends Thread {
    // DB helper instance
    private HabitDataSource dbHelper;
    // habit instance
    private Habit habit;

    // constructor
    public AddToDBThread(Habit habit){
        this.habit = habit;
        dbHelper = HabitDataSource.getInstance();
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
