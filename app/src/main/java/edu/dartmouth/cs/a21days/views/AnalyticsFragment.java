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

import edu.dartmouth.cs.a21days.R;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);

        // get all habits from database


        /*************** this section is hard coded stuff to test UI ******************************/

        // testing the bar graph
        BarChart barChart = (BarChart) view.findViewById(R.id.analytics_chart);
        // add fake data to chart
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(21, 3)); // num days in streak, num of streaks of these days
        entries.add(new BarEntry(25, 2));
        entries.add(new BarEntry(5, 10));
        entries.add(new BarEntry(10, 5));
        BarDataSet dataSet = new BarDataSet(entries, "Streaks Summary");
        dataSet.setColor(Color.RED);
        // dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setBarBorderWidth(0.9f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        /*YAxis yAxis = barChart.getAxisLeft();
        yAxis.setTextColor(Color.RED);
        yAxis.setAxisMinimum(0f); // start at zero
        yAxis.setAxisMaximum(12f); // the axis maximum is 12 */



        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.invalidate(); // refresh

        // testing the comment
        TextView encourageComment = (TextView) view.findViewById(R.id.analytics_comment_textview);
        encourageComment.setText(R.string.analytics_default_comment);


        /****************** end hard coded section ************************************************/

        return view;
    }
}
