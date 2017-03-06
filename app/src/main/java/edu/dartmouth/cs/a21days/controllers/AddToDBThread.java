package edu.dartmouth.cs.a21days.controllers;

import java.util.ArrayList;

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
        ArrayList<Habit> habitList = dbHelper.getAll();
        ArrayList<String> habitNames = new ArrayList<>();
        for (Habit habit:habitList){
            habitNames.add(habit.getName());
        }
        if (habitNames.contains(habit.getName())){
            String id = habit.getId();
            habit.setId(dbHelper.put(habit));
            dbHelper.delete(id);
        }
        else {
            habit.setId(dbHelper.put(habit));
        }

    }
}
