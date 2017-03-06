package edu.dartmouth.cs.a21days.views;

/**
 * Based on code from https://gist.github.com/nickaknudson/5024416
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimePreference extends DialogPreference {
    // default hour, minute, and time value
    private int mHour = 0;
    private int mMinute = 0;
    private final String DEFAULT_VALUE = "00:00";

    private TimePicker picker = null;

    // constructor
    public TimePreference(Context context) {
        this(context, null);
    }

    // get hour of day
    public static int getHour(String time) {
        String[] pieces = time.split(":");
        return Integer.parseInt(pieces[0]);
    }

    // get minute of day
    public static int getMinute(String time) {
        String[] pieces = time.split(":");
        return Integer.parseInt(pieces[1]);
    }

    // overrided constructor
    public TimePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // override constructor
    public TimePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    // set time of day
    public void setTime(int hour, int minute) {
        mHour = hour;
        mMinute = minute;
        String time = toTime(mHour, mMinute);
        persistString(time);
        notifyDependencyChange(shouldDisableDependents());
        notifyChanged();
    }

    // convert hour and minute int values to Time object
    public String toTime(int hour, int minute) {
        return String.valueOf(hour) + ":" + String.valueOf(minute);
    }

    // give a summary
    public void updateSummary() {
        String time = String.valueOf(mHour) + ":" + String.valueOf(mMinute);
        setSummary(time24to12(time));
    }

    // when dialog is created
    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        return picker;
    }

    // when dialog is bound
    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        picker.setCurrentHour(mHour);
        picker.setCurrentMinute(mMinute);
    }

    // when dialog is closed
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            int currHour = picker.getCurrentHour();
            int currMinute = picker.getCurrentMinute();

            if (!callChangeListener(toTime(currHour, currMinute))) {
                return;
            }

            // persist
            setTime(currHour, currMinute);
            updateSummary();
        }
    }

    // get the default time value
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    // set a default time value
    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        String time = null;

        if (restorePersistedValue) {
            if (defaultValue == null) {
                time = getPersistedString(DEFAULT_VALUE);
            } else {
                time = getPersistedString(DEFAULT_VALUE);
            }
        } else {
            time = defaultValue.toString();
        }

        int currHour = getHour(time);
        int currMinute = getMinute(time);
        // need to persist here for default value to work
        setTime(currHour, currMinute);
        updateSummary();
    }

    // convert a string time to Date object
    public static Date toDate(String inTime) {
        try {
            DateFormat inTimeFormat = new SimpleDateFormat("HH:mm", Locale.US);
            return inTimeFormat.parse(inTime);
        } catch (ParseException e) {
            return null;
        }
    }

    // convert 24 hour clock to 12 hour
    public static String time24to12(String inTime) {
        Date inDate = toDate(inTime);
        if (inDate != null) {
            DateFormat outTimeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            return outTimeFormat.format(inDate);
        } else {
            return inTime;
        }
    }
}
