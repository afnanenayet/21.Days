package edu.dartmouth.cs.a21days.controllers;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import edu.dartmouth.cs.a21days.models.Habit;

/**
 * Helper class for Firebase realtime database.
 *
 * Created by aenayet on 3/5/17.
 */
public class HabitDataSource {
    // database instance
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    // instance of this class
    private static HabitDataSource instance = null;
    private HashMap<String, Habit> habitMap;

    public static final String INVALID_USER = "INVALID";
    private static final String DEBUG_TAG = "HabitDataSource";
    public String userId;

    // Read and update list of habits
    private ChildEventListener childListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(DEBUG_TAG, "Habit added for user");

            Habit newHabit = dataSnapshot.getValue(Habit.class);
            habitMap.put(newHabit.getId(), newHabit);

            // Update UI to reflect this change
            updateAdapter();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.d(DEBUG_TAG, "Habit modified for user");

            Habit habit = dataSnapshot.getValue(Habit.class);
            habitMap.put(habit.getId(), habit);

            // Update UI to reflect this change
            updateAdapter();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.d(DEBUG_TAG, "Habit removed for user");

            Habit habit = dataSnapshot.getValue(Habit.class);
            habitMap.remove(habit.getId());

            // Update UI to reflect this change
            updateAdapter();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            Log.d(DEBUG_TAG, "Child was moved");

            // Update UI to reflect this change
            updateAdapter();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(DEBUG_TAG, "Database change was cancelled" + databaseError.getMessage());
        }
    };

    /**
     * Public constructor for database
     * @param userId the ID of the user
     */
    private HabitDataSource(String userId) {
        mDatabase = FirebaseDatabase.getInstance();
        setUser(userId);
    }

    /**
     * Sets the database to reflect the
     * @param userId the ID string for the user currently using the app. If the user is offline,
     *               use the string "INVALID"
     */
    public void setUser(@Nullable String userId) {
        Log.d(DEBUG_TAG, "Setting user");

        // Setting a new user means we are looking at a different set of habits
        // thus reset the running list of all entries
        habitMap = new HashMap<String, Habit>();

        // If user is null, generate a space in the database for them with an autogenerated key
        if (userId == null) {
            DatabaseReference databaseReference = mDatabase.getReference();
            DatabaseReference offlineUser = databaseReference.push();
            String key = offlineUser.getKey();
            this.userId = key;
        } else {
            // Getting firebase space from a user ID reference
            this.userId = userId;
            mReference = mDatabase.getReference(userId);
            mReference.addChildEventListener(childListener);
        }
    }

    /**
     * Gets the singleton instance of the HabitDataSource and switches userId
     * @param userId The identifier string for the user
     * @return An instance of the HabitDataSource
     */
    public static HabitDataSource getInstance(String userId) {

        // If class hasn't been initialized, then initialize class
        if (instance == null) {
            instance = new HabitDataSource(userId);
        }

        // Return current instance
        return instance;
    }

    /**
     * Get instance of {@link HabitDataSource}
     * @return instance of HabitDataSource
     */
    public static HabitDataSource getInstance() {
        return instance;
    }

    /**
     * Puts an object into the database
     * @param value the value to put into the database
     * @return a string corresponding to the ID of the entry
     */
    public String put(Habit value) {
        Log.d(DEBUG_TAG, "Adding habit to database");

        DatabaseReference newHabitReference = mReference.push();
        String id = newHabitReference.getKey();
        value.setId(id);
        newHabitReference.setValue(value);
        return id;
    }

    /**
     * Deletes an entry
     * @param id the ID of the habit to delete
     */
    public void delete(String id) {
        Log.d(DEBUG_TAG, "Deleting habit from database");

        DatabaseReference habitReference = mReference.child(id);
        habitReference.removeValue();
    }

    /**
     * Gets a particular object specified by id
     * @param id the ID of the object
     * @return an object that corresponds to the query results
     */
    public Habit get(String id) {
        Log.d(DEBUG_TAG, "Getting habit " + id + " from database");
        return habitMap.get(id);
    }

    /**
     * Gets all objects for a reference location in the database
     * @return a list of objects
     */
    public ArrayList<Habit> getAll() {
        Log.d(DEBUG_TAG, "Retrieving all habits");

        return new ArrayList<>(habitMap.values());
    }

    /**
     * Updates listviewadapter with current data
     */
    private void updateAdapter() {
        HabitListviewAdapter adapter = HabitListviewAdapter.getInstance();

        // If the adapter has been initialized, update visible data
        if (adapter != null) {
            adapter.updateData(this.getAll());
        }
    }
}
