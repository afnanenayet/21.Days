package edu.dartmouth.cs.a21days.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import edu.dartmouth.cs.a21days.R;

/**
 * Created by Steven on 3/2/17.
 */

public class HabitDetailsFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

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
        return builder.create();
    }

}
