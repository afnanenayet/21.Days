package edu.dartmouth.cs.a21days.models;

import android.icu.util.Calendar;
import android.location.Location;
import java.util.ArrayList;

/**
 * Created by aenayet on 2/24/17.
 */

/**
 * Data model for a habit + relevant properties
 */
public class Habit {
    private String name;
    private Location location;
    private ArrayList<Calendar> frequency;
    private int streak;
    private int priority;
    private String category;

    public Habit() {}

    /************************************** Getters and setters *********************************/

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public ArrayList<Calendar> getFrequency() {
        return frequency;
    }

    public void setFrequency(ArrayList<Calendar> frequency) {
        this.frequency = frequency;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
