package edu.dartmouth.cs.a21days.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.R;

/**
 * Created by Steven on 3/1/17.
 */

public class NewHabitDialogFragment extends DialogFragment {

    private ArrayList<String> catagoryList = new ArrayList<String>();
    private SimpleCursorAdapter mAdapter;
    private static final String TAG = "NewHabitDialogFragment";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Temporary code to add categories to categorylist
        catagoryList.add("Life");
        catagoryList.add("Fitness");
        catagoryList.add("Productivity");
        catagoryList.add("Academic");
        catagoryList.add("Social");
        catagoryList.add("Health");


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Create a New Habit");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_new_habit, null);
        builder.setView(view);

        // Populate the priority spinner with options "Set Priority", "Low", "Medium", "High"
        Spinner spinner = (Spinner) view.findViewById(R.id.priority_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        setUpSearchView(view);

        Switch enableAllDay = (Switch) view.findViewById(R.id.all_day_switch);
        enableAllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker_habit);
                if (compoundButton.isChecked()){
                    timePicker.setVisibility(View.GONE);
                }
                else {
                    timePicker.setVisibility(View.VISIBLE);
                }
            }
        });

        Switch enableLocation = (Switch) view.findViewById(R.id.location_requirement);
        enableLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SearchView locationSearch = (SearchView) view.findViewById(R.id.location_search);
                if (!compoundButton.isChecked()){
                    locationSearch.setVisibility(View.GONE);
                }
                else {
                    locationSearch.setVisibility(View.VISIBLE);
                }
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismissAllowingStateLoss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismissAllowingStateLoss();
            }
        });
        return builder.create();
    }

    private void setUpSearchView(View view){

        // Initialize cursor adapter for categories search
        final String[] from = new String[] {"habitCategories"};
        final int[] to = new int[] {android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        // Find the searchView and set the suggestions adapter
        final SearchView searchView = (SearchView) view.findViewById(R.id.category_search);
        searchView.setSuggestionsAdapter(mAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            // When a suggestion is clicked
            @Override
            public boolean onSuggestionClick(int position) {
                Log.i(TAG, "onSuggestionClick: " + mAdapter.getCursor().getString(position));
                String displayInSearchBar;
                String userInput = String.valueOf(searchView.getQuery());

                // Get the text associated with the selection
                try{
                    displayInSearchBar = catagoryList.get(Integer.valueOf(mAdapter.getCursor().getString(position)));
                }catch (Exception e){
                    displayInSearchBar = mAdapter.getCursor().getString(position);
                }

                // If it is already an option, populate the search bar
                if (catagoryList.contains(displayInSearchBar)){
                    searchView.setQuery(displayInSearchBar, true);
                }
                // Otherwise, the user clicked "Create New Category"
                else {
                    // Populate the search bar with their input and add it to the category list
                    searchView.setQuery(userInput, true);
                    catagoryList.add(userInput);
                }


                return true;
            }
            @Override
            public boolean onSuggestionSelect(int position) {
                Log.i(TAG, "onSuggestionSelect: " + position);
                return true;
            }
        });

        // When the text in the search bar changes, repopulate the search suggestions
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateAdapter(s);
                return false;
            }
        });
    }


    // Helper function to populate the search suggestions
    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "habitCategories" });
        for (int i = 0; i< catagoryList.size(); i++) {
            if (catagoryList.get(i).toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[] {i, catagoryList.get(i)});
        }
        c.addRow(new Object[] {catagoryList.size()+1, "Create New Category"});
        mAdapter.changeCursor(c);
    }

}
