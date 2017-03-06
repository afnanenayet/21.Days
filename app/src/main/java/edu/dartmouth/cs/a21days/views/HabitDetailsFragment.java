package edu.dartmouth.cs.a21days.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.HabitDataSource;
import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Created by Steven on 3/2/17.
 */

public class HabitDetailsFragment extends DialogFragment {

    private int position;
    private HabitDataSource dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("Position");
        dbHelper = HabitDataSource.getInstance("example");

        //        PieChart pieChart = (PieChart)findViewById(R.id.detail_pie_chart);
//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("Days completed");
//        labels.add("Days left");
//        ArrayList<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(8f, 1));
//        entries.add(new PieEntry(6f, 2));
//        PieDataSet mDataset = new PieDataSet(entries,"Number of Days");
//        PieData pieData = new PieData(mDataset);
//        pieData.setDataSet(mDataset);



    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_habit_detail, null);
        builder.setView(view);

        // Set on click listener for delete button
        Button deleteHabit = (Button) view.findViewById(R.id.delete_habit);
        deleteHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the habit from teh data source
                ArrayList<Habit> habits = dbHelper.getAll();
                dbHelper.delete(habits.get(position).getId());
                dismiss();
            }
        });

        return builder.create();
    }

}
