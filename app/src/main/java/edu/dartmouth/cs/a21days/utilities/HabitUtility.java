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
        switch (currentDay) {
            case Calendar.SUNDAY:
                currentDay = Globals.SUNDAY;
                break;
            case Calendar.MONDAY:
                currentDay = Globals.MONDAY;
                break;
            case Calendar.TUESDAY:
                currentDay = Globals.TUESDAY;
                break;
            case Calendar.WEDNESDAY:
                currentDay = Globals.WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                currentDay = Globals.THURSDAY;
                break;
            case Calendar.FRIDAY:
                currentDay = Globals.FRIDAY;
                break;
            case Calendar.SATURDAY:
                currentDay = Globals.SATURDAY;
                break;
        }

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
}
