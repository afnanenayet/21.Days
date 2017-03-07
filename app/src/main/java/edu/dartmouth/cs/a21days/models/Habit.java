package edu.dartmouth.cs.a21days.models;

import java.util.ArrayList;

/**
 * Data model for a habit + relevant properties.
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
    private boolean hasLocation;
    private int timeStamp;


    // constructor
    public Habit() {
        // empty
    }


    /************************************** Getters and setters *********************************/

    public boolean isHasLocation() {
        return hasLocation;
    }

    public void setHasLocation(boolean hasLocation) {
        this.hasLocation = hasLocation;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    // get habit time
    public int getTime() {
        return time;
    }

    // set habit time
    public void setTime(int time) {
        this.time = time;
    }

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
    public ArrayList<Integer> getFrequency() {
        return frequency;
    }

    // set habit frequency
    public void setFrequency(ArrayList<Integer> frequency) {
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

    // get habit name
    public String getName() {
        return name;
    }

    // set habit name
    public void setName(String name) {
        this.name = name;
    }

    // get habit id
    public String getId() {
        return id;
    }

    // set habit id
    public void setId(String id) {
        this.id = id;
    }
}
