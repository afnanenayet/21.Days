package edu.dartmouth.cs.a21days.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import cn.fanrunqi.waveprogress.WaveProgressView;
import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.R;

/**
 * Created by aenayet on 2/26/17.
 */

/**
 * Listview Adapter for recycler view that displays habit information
 */
public class HabitListviewAdapter extends RecyclerView.Adapter<HabitListviewAdapter
        .HabitViewHolder> {
    // Holds all the habit data for view
    private ArrayList<Habit> mHabitList;

    /**
     * Constructor
     * @param habitArrayList A list of habit objects that we want to display
     */
    public HabitListviewAdapter(ArrayList<Habit> habitArrayList) {
        this.mHabitList = habitArrayList;
    }

    /**
     * Creates {@link HabitViewHolder} for class
     * @return An instance of {@link HabitViewHolder} with internal views bound
     * to class
     */
    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Getting parent view for Habit list view item
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_item_view, parent, false);

        return new HabitViewHolder(linearLayout);
    }

    /**
     * Replace the contents of a view
     * @param holder The holder for that view
     * @param position the position to fill contents with
     */
    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position) {
        // Load contents for that position
        // TODO debug replace with real data
        holder.habitNameTv.setText(Integer.toString(position));
        holder.progressView.setMaxProgress(100);
        holder.progressView.setCurrent(position, "TEST DEBUG");
    }

    /**
     * Calculates number of habit items that are being displayed
     * @return The number of habit items currently being rendered in the {@link RecyclerView}
     */
    @Override
    public int getItemCount() {
        return mHabitList.size();
    }

    /**
     * View holder for {@link Habit} object to feed into ListView
     * Provides a reference to the view for each data item
     */
    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        public TextView habitNameTv;
        public TextView habitCurrentStreakTv;
        public TextView habitPriorityTv;
        public TextView habitCategoryTv;
        public TextView habitFrequency;
        public TextView habitLocation;
        public WaveProgressView progressView;

        public HabitViewHolder(View itemView) {
            super(itemView);

            // Setting up static references to internal textviews
            LinearLayout parentLinearLayout = (LinearLayout) itemView;
            LinearLayout internalLinearLayout = (LinearLayout)
                    itemView.findViewById(R.id.habits_list_view);

            // Binding internal views
            habitNameTv = (TextView) internalLinearLayout.findViewById(R.id.habit_name);
            habitCurrentStreakTv = (TextView) internalLinearLayout
                    .findViewById(R.id.habit_current_streak);
            habitPriorityTv = (TextView) internalLinearLayout.findViewById(R.id.habit_priority);
            habitCategoryTv = (TextView) internalLinearLayout.findViewById(R.id.habit_category);
            habitFrequency = (TextView) internalLinearLayout.findViewById(R.id.habit_frequency);
            habitLocation = (TextView) internalLinearLayout.findViewById(R.id.habit_location);
            progressView = (WaveProgressView) parentLinearLayout
                    .findViewById(R.id.habit_progress_bar);
        }
    }
}
