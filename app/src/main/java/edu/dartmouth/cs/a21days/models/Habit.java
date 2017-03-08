package edu.dartmouth.cs.a21days.models;

import java.util.ArrayList;

/**
 * Data model for a habit and its relevant properties.
 */
public class Habit {
    // Habit id for database
    private String id;

    // User friendly habit name
    private String name;

    // Location where habit can be checked in
    private SerialLatLng location;

    // Time of day to do habit
    private int time;

    // Which days the habit is repeated
    private ArrayList<Integer> frequency;

    // How many days in a row the habit has been checked in
    private int streak;

    // Habit priority
    private int priority;

    // Habit category
    private String category;

    // Whether the habit is using a geofence
    private boolean hasLocation;

    // The last time the habit was checked in
    private int timeStamp;

    // Whether the habit is using Google Fit
    private boolean hasGoogleFit;

    // The goal for Google Fit
    private int googleFitValue;

    // What type of Google Fit data is used; distance of steps
    private String googleFitType;

    // Need empty constructor for Firebase serialization
    public Habit() { }


    /************************************** Getters and setters *********************************/

    // get Google Fit value
    public int getGoogleFitValue() {
        return googleFitValue;
    }

    // set Google Fit value
    public void setGoogleFitValue(int googleFitValue) {
        this.googleFitValue = googleFitValue;
    }

    // get Google Fit type
    public String getGoogleFitType() {
        return googleFitType;
    }

    // set Google Fit type
    public void setGoogleFitType(String googleFitType) {
        this.googleFitType = googleFitType;
    }

    // return whether using location
    public boolean isHasLocation() {
        return hasLocation;
    }

    // set whether using location
    public void setHasLocation(boolean hasLocation) {
        this.hasLocation = hasLocation;
    }

    // get timestamp
    public int getTimeStamp() {
        return timeStamp;
    }

    // set timestamp
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

    // return whether using Google Fit
    public boolean isHasGoogleFit() {
        return hasGoogleFit;
    }

    // set whether using Google Fit
    public void setHasGoogleFit(boolean hasGoogleFit) {
        this.hasGoogleFit = hasGoogleFit;
    }
}
