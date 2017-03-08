package edu.dartmouth.cs.a21days.views;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.HabitListviewAdapter;
import edu.dartmouth.cs.a21days.models.Habit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link HabitsListFragment#getInstance()} factory method to
 * create an instance of this fragment.
 */
public class HabitsListFragment extends Fragment {
    // tag for debugging
    private static String TAG = "HabitsListFragment";
    // instance of this fragment
    private static HabitsListFragment instance = null;
    // ListView adapter
    HabitListviewAdapter adapter;

    // constructor
    public HabitsListFragment() {
        //empty, required
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static HabitsListFragment getInstance() {
        if (instance == null) {
            instance = new HabitsListFragment();
        }

        return instance;
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

        // Initializing empty arraylist for adapter in main activity so context is not lost
        // on state change
        ArrayList<Habit> dataList = new ArrayList<>();
        adapter = HabitListviewAdapter.getInstance(getActivity(), dataList);

        // Initializing RecyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

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

    /**
     * Re-initialize the context for the data adapter
     */
    public void refreshAdapter(Context context) {
        HabitListviewAdapter.setContext(context);
    }

}
