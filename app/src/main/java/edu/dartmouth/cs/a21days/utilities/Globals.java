package edu.dartmouth.cs.a21days.utilities;

/**
 * Stores various global variables and constants for this app.
 */
public class Globals {

    /**
     * Private constructor to prevent class initialization
     */
    private Globals() { }

    public static String userId;

    // Global tags
    public static final String POSITION_TAG = "Position";
    public static final String POPUP_DIALOG_TAG = "Habit details";

    // enums for days to repeat (with job scheduler)
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    public static final String NOTIFICATION_TITLE_KEY = "notification_title_key";
    public static final String NOTIFICATION_MESSAGE_KEY = "notification_message_key";
    public static final long dayInMs = 86400000L;
    public static final String NOTIFICATION_HOUR_KEY = "notification_hour_key";
    public static final String NOTIFICATION_MINUTE_KEY = "notification_minute_key";
    public static final String NOTIFICATION_DAY_ARRAY_KEY = "notification_day_array_key";
    public static final String NOTIFICATION_HABIT_ID = "notification_habit_id";
}
