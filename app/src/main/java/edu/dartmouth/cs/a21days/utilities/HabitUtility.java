package edu.dartmouth.cs.a21days.utilities;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;

import java.util.Calendar;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.models.SerialLatLng;

/**
 * Helper class to translate or discern data for a {@link Habit} object
 */
public class HabitUtility {
    /**
     * Get String representation of priority index
     * @param i the priority index
     * @return a human readable string that the priority index represents
     */
    @Nullable
    public static String getPriorityString(Context context, int i) {
        String[] priorityArray = context.getResources().getStringArray(R.array.priority_index);

        // Getting string from index
        if (i < priorityArray.length) {
            return priorityArray[i];
        } else {
            return null;
        }
    }

    /**
     * Converts {@link SerialLatLng} to a {@link Location} object
     * @param serialLatLng SerialLatLng object to convert
     * @return returns a location object
     */
    public static Location latLngToLocation(SerialLatLng serialLatLng) {
        // Initializing location object
        Location location = new Location(LocationManager.GPS_PROVIDER);

        // Set lat and long coordinates
        location.setLatitude(serialLatLng.getLatitude());
        location.setLongitude(serialLatLng.getLongitude());

        return location;
    }

    /**
     * Converts {@link Location} object to {@link SerialLatLng} object
     * @param location the {@link Location} object to convert
     * @return the {@link SerialLatLng} converted from Location
     */
    public static SerialLatLng locationToLatLng(Location location) {
        SerialLatLng serialLatLng = new SerialLatLng();

        // Set lat and long coordinates
        serialLatLng.setLongitude(location.getLongitude());
        serialLatLng.setLatitude(location.getLatitude());

        return serialLatLng;
    }

    /**
     * get the hash of a user's ID
     * @param userId The user's UID
     * @return A hash of the user's UID that will contain no special characters
     */
    public static String hashId(String userId) {
        return ((Integer) userId.hashCode()).toString();
    }

    /**
     * Retrieves offset from current day for a specific day in the future
     * ex: if today is monday, then wednesday is 2 days converted to ms
     * @param targetDay The target day
     * @return The offset in milliseconds
     */
    public static long daysOffset(int targetDay) {
        // Getting current day
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        // convert calendar.day to Globals.day int
        currentDay = calendarDayToGlobalDay(currentDay);

        int daysOffset;

        // Retrieving the difference in days
        if (currentDay > targetDay) {
            daysOffset = (7 - currentDay) + targetDay;
        } else {
            daysOffset = targetDay - currentDay;
        }

        // convert to ms
        return daysOffset * 86400000L;
    }

    /**
     * Converts day enumerators to a boolean array
     * @param days The day(s) to put into the array from enums in {@link Globals}
     * @return an array of boolean values
     */
    public static boolean[] daysToBoolArray(Integer... days) {
        boolean[] boolDays = new boolean[7];

        // Bounds checking for array and setting bit flags to true in array
        for (Integer day : days) {
            if (day > 0 && day < 7) {
                boolDays[day] = true;
            }
        }

        return boolDays;
    }

    /**
     * Converts a day enum from {@link Calendar} to an enum from {@link Globals}
     * @param day The day from {@link Calendar}
     * @return a day enumerator from {@link Globals}
     */
    public static int calendarDayToGlobalDay(int day) {
        // convert calendar.day to Globals.day int
        switch (day) {
            case Calendar.SUNDAY:
                day = Globals.SUNDAY;
                break;
            case Calendar.MONDAY:
                day = Globals.MONDAY;
                break;
            case Calendar.TUESDAY:
                day = Globals.TUESDAY;
                break;
            case Calendar.WEDNESDAY:
                day = Globals.WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                day = Globals.THURSDAY;
                break;
            case Calendar.FRIDAY:
                day = Globals.FRIDAY;
                break;
            case Calendar.SATURDAY:
                day = Globals.SATURDAY;
                break;
        }

        return day;
    }

    /**
     * Converts a boolean array to an integer array
     * @param booleanArray The boolean array to convert
     * @return returns an integer array
     */
    public static int[] booleanToIntArray(boolean[] booleanArray) {
        int[] array = new int[booleanArray.length];

        for (int i = 0; i < booleanArray.length; i++) {
            if (booleanArray[i]) {
                array[i] = 1;
            } else {
                array[i] = 0;
            }
        }

        return array;
    }

    /**
     * Converts a integer array to a boolean array
     * @param integerArray The integer array to convert
     * @return returns a boolean array
     */
    public static boolean[] intToBooleanArray(int[] integerArray) {
        boolean[] array = new boolean[integerArray.length];

        // 1 corresponds to true
        for (int i = 0; i < integerArray.length; i++) {
            array[i] = integerArray[i] == 1;
        }

        return array;
    }
}
