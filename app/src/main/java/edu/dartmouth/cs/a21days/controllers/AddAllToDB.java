package edu.dartmouth.cs.a21days.controllers;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Created by Steven on 3/6/17.
 */

public class AddAllToDB extends Thread {

    HabitDataSource dbHelper;

    AddAllToDB(){
        dbHelper = HabitDataSource.getInstance("example");

    }

    @Override
    public void run(){
        ArrayList<Habit> habits = dbHelper.getAll();
        ArrayList<String> habitsNames = new ArrayList<>();
        // Only add habit to db if it
        for (Habit habit : habits) {
            if (!habitsNames.contains(habit.getName())) {
                habit.setId(dbHelper.put(habit));
            }
        }
    }
}
