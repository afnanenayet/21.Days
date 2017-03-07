package edu.dartmouth.cs.a21days.controllers;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.fanrunqi.waveprogress.WaveProgressView;
import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.Globals;
import edu.dartmouth.cs.a21days.utilities.HabitUtility;
import edu.dartmouth.cs.a21days.views.HabitDetailsFragment;

/**
 * ListView Adapter for recycler view that displays habit information.
 */
public class HabitListviewAdapter extends RecyclerView.Adapter<HabitListviewAdapter
        .HabitViewHolder> {
    // Holds all the habit data for view
    private ArrayList<Habit> mHabitList;
    private Context mContext;
    private static final String TAG = "HabitListviewAdapter";
    private static HabitListviewAdapter instance = null;

    /**
     * Constructor
     *
     * @param context        The context of the activity
     * @param habitArrayList A list of habit objects that we want to display
     */
    public HabitListviewAdapter(Context context, ArrayList<Habit> habitArrayList) {
        this.mContext = context;
        this.mHabitList = habitArrayList;
    }

    /**
     * Creates {@link HabitViewHolder} for class
     *
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
     * Updates the list and refreshes the listview
     * @param list The new list of data
     */
    public void updateData(ArrayList<Habit> list) {
        mHabitList.clear();
        mHabitList = list;
        notifyDataSetChanged();
    }

    /**
     * Turn the {@link HabitListviewAdapter} into a singleton
     * @return an instance of the recycler view adapter
     */
    public static HabitListviewAdapter getInstance(Context context, ArrayList<Habit> list) {
        if (instance == null) {
            instance = new HabitListviewAdapter(context, list);
        }

        return instance;
    }

    /**
     * Turn the {@link HabitListviewAdapter} into a singleton
     * @return an instance of the recycler view adapter
     */
    public static HabitListviewAdapter setContext(Context context) {
        if (instance != null) {
            instance.mContext = context;
        }

        return instance;
    }

    /**
     * Get instance of recycler view adapter
     * @return a possibly null instance
     */
    @Nullable
    public static HabitListviewAdapter getInstance() {
        return instance;
    }

    /**
     * Replace the contents of a view
     *
     * @param holder   The holder for that view
     * @param position the position to fill contents with
     */
    @Override
    public void onBindViewHolder(HabitViewHolder holder, final int position) {
        // Load contents for that position
        // TODO put in all data when applicable
        final Habit habit = mHabitList.get(holder.getAdapterPosition());

        holder.habitNameTv.setText(habit.getName());
        holder.habitPriorityTv.setText(HabitUtility
                .getPriorityString(mContext, habit.getPriority()));
        holder.habitCategoryTv.setText(habit.getCategory());
        holder.habitCurrentStreakTv.setText(habit.getStreak() + " out of 21 days complete");
        holder.progressView.setMaxProgress(21);
        holder.progressView.setCurrent(habit.getStreak(), Integer.toString(habit.getStreak()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                HabitDetailsFragment dialogFragment = new HabitDetailsFragment();
                FragmentManager manager = ((Activity) mContext)
                        .getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt(Globals.POSITION_TAG, position);
                dialogFragment.setArguments(bundle);

                FragmentTransaction transaction = manager.beginTransaction();

                if (!((Activity) mContext).isDestroyed()) {
                    transaction.add(dialogFragment, Globals.POPUP_DIALOG_TAG);
                    transaction.commitAllowingStateLoss();
                }

                return false;
            }
        });
    }

    /**
     * Calculates number of habit items that are being displayed
     *
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
    static class HabitViewHolder extends RecyclerView.ViewHolder {
        // Initializing/binding views
        @BindView(R.id.habit_name)
        TextView habitNameTv;
        @BindView(R.id.habit_current_streak)
        TextView habitCurrentStreakTv;
        @BindView(R.id.habit_priority)
        TextView habitPriorityTv;
        @BindView(R.id.habit_category)
        TextView habitCategoryTv;
        @BindView(R.id.habit_frequency)
        TextView habitFrequency;
        @BindView(R.id.habit_location)
        TextView habitLocation;
        WaveProgressView progressView;

        HabitViewHolder(View itemView) {
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
