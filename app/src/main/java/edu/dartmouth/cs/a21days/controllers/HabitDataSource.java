package edu.dartmouth.cs.a21days.controllers;

/**
 * Created by aenayet on 2/26/17.
 */

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Gets and sets habits in internal data source
 */
public class HabitDataSource {

    /**
     * Retrieves all habits from internal data store
     * @return a list with all habit entries in data store
     */
    public static ArrayList<Habit> getAllHabits() {
        // todo remove test data and replace w/ prod data

        // Initializing test data
        Habit habit1 = new Habit();
        habit1.setName("20 push ups");
        habit1.setPriority(1);
        habit1.setCategory("Fitness");
        habit1.setStreak(10);

        Habit habit2 = new Habit();
        habit2.setName("Meditate");
        habit2.setPriority(3);
        habit2.setCategory("Wellness");
        habit2.setStreak(2);

        Habit habit3 = new Habit();
        habit3.setName("Make an app");
        habit3.setPriority(0);
        habit3.setCategory("Life");
        habit3.setStreak(20);

        // Setting up adapter with test data
        ArrayList<Habit> list = new ArrayList<>();
        list.add(habit1);
        list.add(habit2);
        list.add(habit3);

        return list;
    }
}
