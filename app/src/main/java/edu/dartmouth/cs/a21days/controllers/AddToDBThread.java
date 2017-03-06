package edu.dartmouth.cs.a21days.controllers;

import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Created by Steven on 3/6/17.
 */

public class AddToDBThread extends Thread {

    private HabitDataSource dbHelper;
    private Habit habit;

    public AddToDBThread(Habit habit){
        this.habit = habit;
        dbHelper = HabitDataSource.getInstance("example");
    }

    @Override
    public void run(){
        habit.setId(dbHelper.put(habit));
    }
}
