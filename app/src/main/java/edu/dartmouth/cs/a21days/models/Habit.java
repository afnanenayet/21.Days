package edu.dartmouth.cs.a21days.models;

import android.icu.util.Calendar;

import java.util.ArrayList;

/**
 * Data model for a habit + relevant properties.
 *
 * Created by aenayet on 2/24/17.
 */
public class Habit {
    // habit id
    private String id;
    // habit name
    private String name;
    // habit location
    private SerialLatLng location;
    // time of day to do habit
    private int time;
    // habit frequency
    private ArrayList<Integer> frequency;
    // habit streak
    private int streak;
    // habit priority
    private int priority;
    // habit category
    private String category;

    // constructor
    public Habit() {
        // empty
    }


    /************************************** Getters and setters *********************************/

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

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

    public ArrayList<Integer> getFrequency() {
        return frequency;
    }

    public void setFrequency(ArrayList<Integer> frequency) {
        this.frequency = frequency;
    }

    public SerialLatLng getLocation() {
        return location;
    }

    public void setLocation(SerialLatLng serialLatLng) {
        this.location = serialLatLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
