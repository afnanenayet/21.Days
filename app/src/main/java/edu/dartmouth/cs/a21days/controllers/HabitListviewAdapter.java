package edu.dartmouth.cs.a21days.controllers;

import android.content.Context;
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
import butterknife.ButterKnife;
import cn.fanrunqi.waveprogress.WaveProgressView;
import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.utilities.HabitUtility;

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
    private Context mContext;

    /**
     * Constructor
     * @param context The context of the activity
     * @param habitArrayList A list of habit objects that we want to display
     */
    public HabitListviewAdapter(Context context, ArrayList<Habit> habitArrayList) {
        this.mContext = context;
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
        // TODO put in all data when applicable
        Habit habit = mHabitList.get(position);

        holder.habitNameTv.setText(habit.getName());
        holder.habitPriorityTv.setText(HabitUtility
                .getPriorityString(mContext, habit.getPriority()));
        holder.habitCategoryTv.setText(habit.getCategory());
        holder.habitCurrentStreakTv.setText(habit.getStreak() + " out of 21 days complete");
        holder.progressView.setMaxProgress(21);
        holder.progressView.setCurrent(habit.getStreak(), Integer.toString(habit.getStreak()));
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
        @BindView(R.id.habit_name) TextView habitNameTv;
        @BindView(R.id.habit_current_streak) TextView habitCurrentStreakTv;
        @BindView(R.id.habit_priority) TextView habitPriorityTv;
        @BindView(R.id.habit_category) TextView habitCategoryTv;
        @BindView(R.id.habit_frequency) TextView habitFrequency;
        @BindView(R.id.habit_location) TextView habitLocation;
        WaveProgressView progressView;

        public HabitViewHolder(View itemView) {
            super(itemView);

            // Setting up static references to internal textviews
            LinearLayout parentLinearLayout = (LinearLayout) itemView;
            LinearLayout internalLinearLayout = (LinearLayout)
                    itemView.findViewById(R.id.habits_list_view);

            // Binding internal views
            progressView = (WaveProgressView) parentLinearLayout
                    .findViewById(R.id.habit_progress_bar);
            ButterKnife.bind(this, internalLinearLayout);
        }
    }
}
