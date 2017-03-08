package edu.dartmouth.cs.a21days.controllers;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.Globals;
import edu.dartmouth.cs.a21days.utilities.HabitUtility;
import edu.dartmouth.cs.a21days.utilities.NotificationJob;

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
            if (habit.getTime() != -1) {
                boolean[] days;

                // If no days are selected, assume every day)
                if (habit.getFrequency().size() == 0){
                    days = new boolean[] {true, true, true, true, true, true, true};
                } else {
                    ArrayList<Integer> daysArrayList = habit.getFrequency();
                    days = HabitUtility.daysToBoolArray(daysArrayList);
                }
                int habitMin = habit.getTime() % 100;
                int habitHour = habit.getTime() / 100;

                // Schedule notification
                NotificationJob.scheduleJob(
                        habit.getId(),
                        habit.getName(),
                        "This reminder is brought to you by 21.Days",
                        habitHour,
                        habitMin,
                        days

                );
            }
        }
    }
}
