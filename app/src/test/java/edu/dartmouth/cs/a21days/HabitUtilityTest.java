package edu.dartmouth.cs.a21days;

import android.content.Context;

import org.junit.Test;

import edu.dartmouth.cs.a21days.utilities.HabitUtility;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by aenayet on 2/28/17.
 */

/**
 * Unit testing for {@link edu.dartmouth.cs.a21days.utilities.HabitUtility} class
 */
@RunWith(MockitoJUnitRunner.class)
public class HabitUtilityTest {
    @Mock
    Context mockContext;

    /**
     * Tests to ensure that the priority strings being returned by the utility class are correctly
     * corresponding with the resource
     * @throws Exception on error
     */
    @Test
    public void intToPriorityString() throws Exception {
        mockContext.getResources().finishPreloading();
        String[] priorityStrings = mockContext.getResources().getStringArray(R.array.priority_index);

        // Checking each string
        for (int i = 0; i < priorityStrings.length; i++) {
            assertThat(HabitUtility.getPriorityString(mockContext, i), is(priorityStrings[i]));
        }
    }
}
