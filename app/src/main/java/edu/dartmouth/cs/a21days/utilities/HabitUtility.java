package edu.dartmouth.cs.a21days.utilities;

import android.content.Context;
import android.support.annotation.Nullable;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.models.Habit;

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
}
