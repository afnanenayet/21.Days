package edu.dartmouth.cs.a21days.views;

import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.dartmouth.cs.a21days.R;

public class NewHabit extends AppCompatActivity {
    private static final String TAG = "NewHabit";

    private static final String[] SUGGESTIONS = {
            "Life", "Fitness", "Productivity",
            "Academic", "Social", "Health",
    };
    private SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);


        Spinner spinner = (Spinner) findViewById(R.id.priority_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        final String[] from = new String[] {"habitCategories"};
        final int[] to = new int[] {android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        final SearchView searchView = (SearchView) findViewById(R.id.category_search);
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setIconifiedByDefault(false);
        // Getting selected (clicked) item suggestion
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Log.i(TAG, "onSuggestionClick: " + mAdapter.getCursor().getString(position));
                String text;
                try{
                    text = SUGGESTIONS[Integer.valueOf(mAdapter.getCursor().getString(position))];
                }catch (Exception e){
                    text = mAdapter.getCursor().getString(position);
                }

                searchView.setQuery(text, true);
                return true;

            }
            @Override
            public boolean onSuggestionSelect(int position) {
                Log.i(TAG, "onSuggestionSelect: " + position);
                return true;
            }
        });
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

    // You must implements your logic to get data using OrmLite
    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "habitCategories" });
        for (int i=0; i<SUGGESTIONS.length; i++) {
            if (SUGGESTIONS[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[] {i, SUGGESTIONS[i]});
        }
        mAdapter.changeCursor(c);
    }
}
