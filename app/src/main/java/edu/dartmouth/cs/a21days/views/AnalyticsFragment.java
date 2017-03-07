package edu.dartmouth.cs.a21days.views;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.HabitDataSource;
import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Use the {@link AnalyticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalyticsFragment extends Fragment {
    // database helper instance
    private HabitDataSource dbHelper;

    // map of length of streak to number of that streak
    private HashMap<Integer, Integer> streakMap;
    // number of habits completed
    private int numHabitsCompleted = 0;
    // number of habits ongoing
    private int numHabitsOngoing = 0;
    // name of longest ongoing habit
    private String longestStreakHabitName;
    // streak of longest ongoing habit
    private int longestStreakHabitNum = 0;
    // name of most neglected habit
    private String mostNeglectedHabitName;
    // days since most neglected habit checked off
    private int mostNeglectedHabitNum = 0;

    // constructor
    public AnalyticsFragment() {
        // empty, required
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AnalyticsFragment.
     */
    public static AnalyticsFragment newInstance() {
        AnalyticsFragment fragment = new AnalyticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);

        // get all habits from database
        dbHelper = HabitDataSource.getInstance("example");
        ArrayList<Habit> habitList = dbHelper.getAll();

        // create hashmap
        streakMap = new HashMap<Integer, Integer>();

        // go through each habit
        for (Habit habit:habitList) {
            // get the streak of the habit
            int habitStreak = habit.getStreak();
            // put this streak into the streak, frequency map as 0 frequency, for now
            streakMap.put(habitStreak, 0);

            // check if streak is longest
            if (habitStreak >= longestStreakHabitNum) {
                longestStreakHabitNum = habitStreak;
                longestStreakHabitName = habit.getName();
            }
            // check if streak is over 21 days
            if (habitStreak >= 21) {
                numHabitsCompleted++;
            }
            else {
                numHabitsOngoing++;
            }
            // TODO: get most neglected

        }

        // go through each habit again to construct the map
        for (Habit habit:habitList) {
            // get the streak
            int habitStreak = habit.getStreak();
            // get frequency of this streak from the map
            int freq = streakMap.get(habitStreak);
            streakMap.put(habitStreak, freq + 1);
        }


        // add a bar graph
        BarChart barChart = (BarChart) view.findViewById(R.id.analytics_chart);
        // add data from map to entries list
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        // iterate through the map
        for (Map.Entry<Integer, Integer> entry:streakMap.entrySet()) {
            entries.add(new BarEntry(entry.getKey(), entry.getValue()));
        }
        // create dataset for bar graph
        BarDataSet dataSet = new BarDataSet(entries, "Streaks Summary");
        dataSet.setColor(Color.RED);
        dataSet.setBarBorderWidth(0.9f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.invalidate(); // refresh

        // set text for num habits completed
        TextView numHabitsCompletedView =
                (TextView) view.findViewById(R.id.analytics_completed_habits_num_textview);
        numHabitsCompletedView.setText(Integer.toString(numHabitsCompleted));

        // set text for num habits ongoing
        TextView numHabitsOngoingView =
                (TextView) view.findViewById(R.id.analytics_ongoing_habits_num_textview);
        numHabitsOngoingView.setText(Integer.toString(numHabitsOngoing));

        // set text for longest streak habit
        TextView longestStreakNameView =
                (TextView) view.findViewById(R.id.analytics_longest_streak_habit_name_textview);
        longestStreakNameView.setText(longestStreakHabitName);
        TextView longestStreakNumView =
                (TextView) view.findViewById(R.id.analytics_longest_streak_num_textview);
        longestStreakNumView.setText(Integer.toString(longestStreakHabitNum));

        // TODO: set text for most neglected

        // testing the comment
        TextView encourageComment = (TextView) view.findViewById(R.id.analytics_comment_textview);
        encourageComment.setText(R.string.analytics_default_comment);


        /****************** end hard coded section ************************************************/

        return view;
    }
}
