package edu.dartmouth.cs.a21days.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.HabitDataSource;
import edu.dartmouth.cs.a21days.controllers.HabitListviewAdapter;
import edu.dartmouth.cs.a21days.models.Habit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link HabitsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitsListFragment extends Fragment {
    // tag for debugging
    private static String TAG = "HabitsListFragment";

    // constructor
    public HabitsListFragment() {
        // empty, required
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static HabitsListFragment newInstance() {
        HabitsListFragment fragment = new HabitsListFragment();
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
        View mView = inflater.inflate(R.layout.fragment_habits_list, container, false);
        RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.habits_recycler_view);

        // Initializing recyclerview
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        // Retrieving habit objects
        ArrayList<Habit> dataList = HabitDataSource.getInstance().getAll();

        HabitListviewAdapter adapter = new HabitListviewAdapter(getContext(), dataList);

        // set up RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        // set up button to create new habit
        FloatingActionButton createHabitButton =
                (FloatingActionButton) mView.findViewById(R.id.fab);
        createHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewHabitDialogFragment dialogFragment = new NewHabitDialogFragment();
                dialogFragment.show(getFragmentManager(), "Test");
            }
        });


        return mView;
    }


}
