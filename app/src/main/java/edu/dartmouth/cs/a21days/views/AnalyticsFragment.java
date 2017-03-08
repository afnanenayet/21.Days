package edu.dartmouth.cs.a21days.views;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // database helper instance
    private HabitDataSource dbHelper;
    public static ArrayList<Habit> habitList;

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



    private BarChart barChart;
    private TextView numHabitsCompletedView;
    private TextView numHabitsOngoingView;
    private TextView longestStreakNameView;
    private TextView longestStreakNumView;
    private TextView mostNeglectedNameView;
    private TextView mostNeglectedDaysView;
    private TextView encourageComment;

    // constructor
    public AnalyticsFragment() {
       // empty, required
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AnalyticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalyticsFragment newInstance() {
        AnalyticsFragment fragment = new AnalyticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dbHelper = HabitDataSource.getInstance("example");
        habitList = dbHelper.getAll();
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);

        barChart = (BarChart) view.findViewById(R.id.analytics_chart);

        // get the TextViews
        numHabitsCompletedView =
                (TextView) view.findViewById(R.id.analytics_completed_habits_num_textview);
        numHabitsOngoingView =
                (TextView) view.findViewById(R.id.analytics_ongoing_habits_num_textview);
        longestStreakNameView =
                (TextView) view.findViewById(R.id.analytics_longest_streak_habit_name_textview);
        longestStreakNumView =
                (TextView) view.findViewById(R.id.analytics_longest_streak_num_textview);
        mostNeglectedNameView =
                (TextView) view.findViewById(R.id.analytics_neglected_habit_name_textview);
        mostNeglectedDaysView =
                (TextView) view.findViewById(R.id.analytics_neglected_days_textview);
        encourageComment =
                (TextView) view.findViewById(R.id.analytics_comment_textview);

        //Setup the fragment view with data
        UpdateAnalyticView();


        return view;
    }

    public void UpdateAnalyticView() {

        //Initialize the data
        numHabitsOngoing = 0;
        longestStreakHabitNum = 0;
        mostNeglectedHabitNum = 0;
        numHabitsCompleted = 0;

        // create hashmap
        HashMap<Integer, Integer> streakMap = new HashMap<Integer, Integer>();

        // go through each habit
        for (int i = 0; i<habitList.size();i++) {
            Habit habit = habitList.get(i);
            // get the streak of the habit
            int habitStreak = habit.getStreak();
            // put this streak into the streak, frequency map as 0 frequency, for now
            streakMap.put(habitStreak, 0);

            // check if streak is longest
            if (habitStreak >= longestStreakHabitNum) {
                longestStreakHabitNum = habitStreak;
                longestStreakHabitName = habit.getName();
                Log.d("TTAG", "UpdateAnalyticView: longeststreak" + longestStreakHabitName + longestStreakHabitNum);
            }
            // check if streak is over 21 days
            if (habitStreak >= 21) {
                numHabitsCompleted++;
            }
            else {
                numHabitsOngoing++;
            }

            //Calculate the days interval between last day and today
            if (habit.getStreak()!=0) {
                //Make sure that timestamp exists

                String timestamp = String.valueOf(habit.getTimeStamp());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                Date d = null;
                try {
                    d = formatter.parse(timestamp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar lastday = Calendar.getInstance();
                lastday.setTime(d);
                Calendar today = Calendar.getInstance();
                Log.d("TTAG", "UpdateAnalyticView: " + timestamp + today.getTime().toString());

                long diff = today.getTimeInMillis() - lastday.getTimeInMillis(); //result in millis
                int days = (int) (diff / (24 * 60 * 60 * 1000));
                Log.d("TTAG", habit.getName() + "UpdateAnalyticView: " + days + " Days");

                //Get the most Neglected habit
                if (days >= mostNeglectedHabitNum) {
                    mostNeglectedHabitNum = days;
                    mostNeglectedHabitName = habit.getName();
                }
            }
        }
        Log.d("TTAG", "UpdateAnalyticView: Outside loop");

        // go through each habit again to construct the map
        for (Habit habit:habitList) {
            // get the streak
            int habitStreak = habit.getStreak();
            // get frequency of this streak from the map
            int freq = streakMap.get(habitStreak);
            streakMap.put(habitStreak, freq + 1);
        }


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
        numHabitsCompletedView.setText(Integer.toString(numHabitsCompleted));

        // set text for num habits ongoing
        numHabitsOngoingView.setText(Integer.toString(numHabitsOngoing));

        // set text for longest streak habit
        longestStreakNameView.setText(longestStreakHabitName);
        longestStreakNumView.setText(Integer.toString(longestStreakHabitNum));

        // set text for most neglected habit
        mostNeglectedDaysView.setText(Integer.toString(mostNeglectedHabitNum));
        mostNeglectedNameView.setText(mostNeglectedHabitName);

        // testing the comment
        encourageComment.setText(R.string.analytics_default_comment);

    }

}
