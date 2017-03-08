package edu.dartmouth.cs.a21days.utilities;

/**
 * Created by aenayet on 3/8/17.
 */

import edu.dartmouth.cs.a21days.controllers.GoogleFitController;
import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Task to check if habit has been completed with completion criteria via Google Fit
 */
public class GoogleFitCompletionTask {
    GoogleFitCompletionTask() { }

    public void setHabitCompleted(Habit habit) {
        GoogleFitController googleFitController = new GoogleFitController()

        switch(habit.getGoogleFitType()) {
            case Globals.DISTANCE_STRING:
                break;
            case Globals.STEPS_STRING:
                break;
            default:
                break;
        };
    }
}
