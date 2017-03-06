package edu.dartmouth.cs.a21days.utilities;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.models.SerialLatLng;

/**
 * Created by aenayet on 2/26/17.
 */

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

        // Setting properties
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

        // Setting properties
        serialLatLng.setLongitude(location.getLongitude());
        serialLatLng.setLatitude(location.getLatitude());

        return serialLatLng;
    }
}
