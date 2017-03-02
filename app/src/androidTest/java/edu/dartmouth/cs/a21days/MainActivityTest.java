package edu.dartmouth.cs.a21days;

/**
 * Created by aenayet on 2/28/17.
 */

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.test.ActivityInstrumentationTestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.dartmouth.cs.a21days.controllers.MainActivity;
import edu.dartmouth.cs.a21days.utilities.HabitUtility;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test suite for {@link edu.dartmouth.cs.a21days.controllers.MainActivity}
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    // Setting up main activity
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    /**
     * Ensures that Main Activity is able to initailize and draw all items correctly
     * Changes orientation and swipes through view pager(s)
     */
    @Test
    public void testSetup() throws Exception {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());

        // Testing orientation change
        mDevice.setOrientationLeft();
        mDevice.setOrientationRight();
        mDevice.setOrientationNatural();
    }

    /**
     * Tests to ensure that the priority strings being returned by the utility class are correctly
     * corresponding with the resource
     * @throws Exception on error
     */
    /*
    @Test
    public void intToPriorityString() throws Exception {
        Context context = getInstrumentation().getContext();

        String[] priorityStrings = context.getResources().getStringArray(R.array.priority_index);

        // Checking each string
        for (int i = 0; i < priorityStrings.length; i++) {
            assertThat(HabitUtility.getPriorityString(context, i), is(priorityStrings[i]));
        }
    }
    */
}
