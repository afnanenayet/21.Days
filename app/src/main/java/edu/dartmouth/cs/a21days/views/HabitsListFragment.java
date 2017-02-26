package edu.dartmouth.cs.a21days.views;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.fanrunqi.waveprogress.WaveProgressView;
import edu.dartmouth.cs.a21days.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link HabitsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HabitsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabitsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitsListFragment newInstance(String param1, String param2) {
        HabitsListFragment fragment = new HabitsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        View mView = inflater.inflate(R.layout.fragment_habits_list, container, false);
        ListView mListView = (ListView) mView.findViewById(R.id.habits_list_view);

        WaveProgressView waveProgressbar = (WaveProgressView) mView.findViewById(R.id.waveProgressbar);
        waveProgressbar.setCurrent(10, "10/21"); // 77, "788M/1024M"
        waveProgressbar.setMaxProgress(21);
        waveProgressbar.setText("#FFFF00",10);
        waveProgressbar.setWaveColor("#5b9ef4"); //"#5b9ef4"

        // waveProgressbar.setWave(float mWaveHight,float mWaveWidth);
        waveProgressbar.setmWaveSpeed(10);//The larger the value, the slower the vibration

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View v, final int position, long id) {

            }
        });

        return mView;
    }

}
