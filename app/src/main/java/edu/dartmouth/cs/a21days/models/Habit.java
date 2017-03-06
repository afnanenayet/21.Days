package edu.dartmouth.cs.a21days.models;

import android.icu.util.Calendar;

import java.util.ArrayList;

/**
 * Data model for a habit + relevant properties.
 *
 * Created by aenayet on 2/24/17.
 */
public class Habit {
    // user ID
    private String id;
    // habit name
    private String name;
    // habit location
    private SerialLatLng location;
    // habit frequency
    private ArrayList<Calendar> frequency;
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

    // get habit category
    public String getCategory() {
        return category;
    }

    // set habit category
    public void setCategory(String category) {
        this.category = category;
    }

    // get habit priority
    public int getPriority() {
        return priority;
    }

    // set habit priority
    public void setPriority(int priority) {
        this.priority = priority;
    }

    // get habit streak
    public int getStreak() {
        return streak;
    }

    // set habit streak
    public void setStreak(int streak) {
        this.streak = streak;
    }

    // get habit frequency
    public ArrayList<Calendar> getFrequency() {
        return frequency;
    }

    // set habit frequency
    public void setFrequency(ArrayList<Calendar> frequency) {
        this.frequency = frequency;
    }

    // get habit location
    public SerialLatLng getLocation() {
        return location;
    }

    // set habit location
    public void setLocation(SerialLatLng serialLatLng) {
        this.location = serialLatLng;
    }

    // set habit name
    public String getName() {
        return name;
    }

    // get habit name
    public void setName(String name) {
        this.name = name;
    }

    // get id
    public String getId() {
        return id;
    }

    // set id
    public void setId(String id) {
        this.id = id;
    }
}
