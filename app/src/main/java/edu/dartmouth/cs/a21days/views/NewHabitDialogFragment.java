package edu.dartmouth.cs.a21days.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.MatrixCursor;
import android.location.Location;
import android.location.LocationManager;
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

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.nex3z.togglebuttongroup.ToggleButtonGroup;

import java.util.ArrayList;

import edu.dartmouth.cs.a21days.R;
import edu.dartmouth.cs.a21days.controllers.AddToDBThread;
import edu.dartmouth.cs.a21days.controllers.HabitDataSource;
import edu.dartmouth.cs.a21days.models.Habit;
import edu.dartmouth.cs.a21days.utilities.HabitUtility;

/**
 * Dialog for creating a new habit from the habit list.
 * <p>
 * Created by Steven on 3/1/17.
 */

public class NewHabitDialogFragment extends DialogFragment {
    // list of habit categories
    private ArrayList<String> categoryList = new ArrayList<String>();
    private SimpleCursorAdapter mAdapter;
    // tag for debugging
    private static final String TAG = "NewHabitDialogFragment";
    // habit instance that is created
    private Habit mHabit;
    // location of habit
    private Location mLocation;
    // db helper to save habit with
    private HabitDataSource dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Temporary code to add categories to categorylist
        categoryList.add("Life");
        categoryList.add("Fitness");
        categoryList.add("Productivity");
        categoryList.add("Academic");
        categoryList.add("Social");
        categoryList.add("Health");

        mHabit = new Habit();
        dbHelper = HabitDataSource.getInstance("example");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        // build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Create a New Habit");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_new_habit, null);
        builder.setView(view);

        // Populate the priority spinner with options "Set Priority", "Low", "Medium", "High"
        final Spinner prioritySpinner = (Spinner) view.findViewById(R.id.priority_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        // Call helper function to set up search view
        setUpSearchView(view);

        // Call helper function to set up switches
        setUpSwitches(view);

        // Set up autocomplete fragment
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                mLocation = new Location(LocationManager.GPS_PROVIDER);
                mLocation.setLatitude(place.getLatLng().latitude);
                mLocation.setLongitude(place.getLatLng().longitude);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        // Set save and cancel buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setHabitInfo(view);
                AddToDBThread add = new AddToDBThread(mHabit);
                add.run();
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

    public void setHabitInfo(View view) {
        // spinner to choose habit priority
        Spinner prioritySpinner = (Spinner) view.findViewById(R.id.priority_spinner);
        // search for habit category
        SearchView categoryView = (SearchView) view.findViewById(R.id.category_search);
        // pick habit time
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker_habit);
        // enable location services
        Switch enableLocation = (Switch) view.findViewById(R.id.location_requirement);
        // pick whether all day habit
        Switch enableAllDay = (Switch) view.findViewById(R.id.all_day_switch);
        // give habit name
        EditText habitName = (EditText) view.findViewById(R.id.habit_name_input);
        ToggleButtonGroup toggleButtons = (ToggleButtonGroup)
                view.findViewById(R.id.multi_selection_group);
        Log.i(TAG, "setHabitInfo: " + toggleButtons.getCheckedPositions());

        // save inputted info
        mHabit.setPriority(prioritySpinner.getSelectedItemPosition());
        mHabit.setCategory(String.valueOf(categoryView.getQuery()));
        mHabit.setName(String.valueOf(habitName.getText()));
        ArrayList<Integer> frequency = new ArrayList<>();
        frequency.addAll(toggleButtons.getCheckedPositions());
        mHabit.setFrequency(frequency);
        if (!enableAllDay.isChecked()) {
            String time = String.valueOf(timePicker.getHour())
                    + String.valueOf(timePicker.getMinute());
            Log.i(TAG, "setHabitInfo:  " + time);
            mHabit.setTime(Integer.parseInt(time));
        }

        if (enableLocation.isChecked() && mLocation != null) {
            mHabit.setHasLocation(true);
            mHabit.setLocation(HabitUtility.locationToLatLng(mLocation));
        }
        else {
            mHabit.setHasLocation(false);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Remove the PlaceAutocompleteFragment if when closing the dialog fragment
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        if (autocompleteFragment != null) {
            getFragmentManager().beginTransaction()
                    .remove(autocompleteFragment)
                    .commit();
        }

    }

    // set up switch to show time picker as needed
    private void setUpSwitches(final View view) {
        // Set up switch to show and hide time picker view
        Switch enableAllDay = (Switch) view.findViewById(R.id.all_day_switch);
        enableAllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker_habit);
                if (compoundButton.isChecked()) {
                    timePicker.setVisibility(View.GONE);
                } else {
                    timePicker.setVisibility(View.VISIBLE);
                }
            }
        });


        // Hid autocompleteView and set up switch to show and hide it
        final View autocompleteView = (View) view.findViewById(R.id.place_autocomplete_fragment);
        autocompleteView.setVisibility(View.GONE);

        Switch enableLocation = (Switch) view.findViewById(R.id.location_requirement);
        enableLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    autocompleteView.setVisibility(View.GONE);
                } else {
                    autocompleteView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // set up search for categories
    private void setUpSearchView(View view) {

        // Initialize cursor adapter for categories search
        final String[] from = new String[]{"habitCategories"};
        final int[] to = new int[]{android.R.id.text1};
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
                try {
                    displayInSearchBar = categoryList.get(Integer.
                            valueOf(mAdapter.getCursor().getString(position)));
                } catch (Exception e) {
                    displayInSearchBar = mAdapter.getCursor().getString(position);
                }

                // If it is already an option, populate the search bar
                if (categoryList.contains(displayInSearchBar)) {
                    searchView.setQuery(displayInSearchBar, true);
                }
                // Otherwise, the user clicked "Create New Category"
                else {
                    // Populate the search bar with their input and add it to the category list
                    searchView.setQuery(userInput, true);
                    categoryList.add(userInput);
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
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "habitCategories"});
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[]{i, categoryList.get(i)});
        }
        c.addRow(new Object[]{categoryList.size() + 1, "Create New Category"});
        mAdapter.changeCursor(c);
    }

}
