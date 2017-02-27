package edu.dartmouth.cs.a21days.views;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.fanrunqi.waveprogress.WaveProgressView;
import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.HabitListviewAdapter;
import edu.dartmouth.cs.a21days.models.Habit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link HabitsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitsListFragment extends Fragment {
    //@BindView(R.id.habits_recycler_view) RecyclerView mRecyclerView;

    public HabitsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
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
        //ButterKnife.bind(mView);
        RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.habits_recycler_view);

        // Initializing test data
        Habit habit1 = new Habit();
        habit1.setName("20 push ups");
        habit1.setPriority(1);
        habit1.setCategory("Fitness");
        habit1.setStreak(10);

        Habit habit2 = new Habit();
        habit2.setName("Meditate");
        habit2.setPriority(3);
        habit2.setCategory("Wellness");
        habit2.setStreak(2);

        Habit habit3 = new Habit();
        habit3.setName("Make an app");
        habit3.setPriority(0);
        habit3.setCategory("Life");
        habit3.setStreak(20);

        // Setting up adapter with test data
        ArrayList<Habit> list = new ArrayList<>();
        list.add(habit1);
        list.add(habit2);
        list.add(habit3);

        // Initializing recyclerview
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        HabitListviewAdapter adapter = new HabitListviewAdapter(list);
        /// mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.habits_recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        return mView;
    }

}
