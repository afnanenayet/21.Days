package edu.dartmouth.cs.a21days.controllers;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DailyTotalResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Controller that allows access to Google fit.
 */
public class GoogleFitController {
    public static final String STEPS = "steps_key";
    public static final String DISTANCE = "distance_key";
    // map for results from Google Fit
    public final static ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
    // debugging tag
    private static final String DEBUG_TAG = "GoogleFitController";
    // keep track of client connection status
    private static GoogleApiClient mClient = null;
    private MainActivity mActivity;

    // constructor
    public GoogleFitController(MainActivity mainActivity) {
        mActivity = mainActivity;
    }

    /**
     * Retrieves distance and steps for user
     *
     * @return A hashmap where steps are an {@link Integer} and distance is a {@link Float}
     */
    public static Map<String, Object> getData() {
        return resultMap;
    }

    /**
     * Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     * to connect to Fitness APIs. The scopes included should match the scopes your app needs
     * (see documentation for details). Authentication will occasionally fail intentionally,
     * and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     * can address. Examples of this include the user never having signed in before, or having
     * multiple accounts on the device and needing to specify which account to use, etc.
     */
    public void buildFitnessClient() {
        // check if client exists
        if (mClient == null) {
            // build new client
            mClient = new GoogleApiClient.Builder(mActivity.getApplicationContext())
                    // add API
                    .addApi(Fitness.HISTORY_API)
                    .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                    // add callbacks
                    .addConnectionCallbacks(
                            new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    Log.i(DEBUG_TAG, "Connected!!!");
                                }

                                @Override
                                public void onConnectionSuspended(int i) {
                                    // If your connection to the sensor gets lost at some point,
                                    // you'll be able to determine the reason and react to it here.
                                    if (i == GoogleApiClient.
                                            ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                        Log.i(DEBUG_TAG, "Connection lost.  Cause: Network Lost.");
                                    } else if (i == GoogleApiClient.
                                            ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                        Log.i(DEBUG_TAG,
                                                "Connection lost.  Reason: Service Disconnected");
                                    }
                                }
                            }
                    )
                    .enableAutoManage(mActivity, 0, new GoogleApiClient
                            .OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.i(DEBUG_TAG, "Google Play services connection failed. Cause: " +
                                    result.toString());
                        }
                    })
                    .build();
        }
    }

    interface ResultCallback {
        void onResult();
    }

    /**
     * Thread that populates resultMap;
     */
    public static class UpdateData extends Thread {
        @Override
        public void run() {
            Integer steps;
            Float distance;

            // get result for steps from Google Fit
            PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(mClient,
                    DataType.AGGREGATE_STEP_COUNT_DELTA);
            DailyTotalResult totalResult = result.await(60, TimeUnit.SECONDS);

            if (totalResult.getStatus().isSuccess()) {
                // get number of steps
                DataSet totalSet = totalResult.getTotal();
                steps = totalSet.isEmpty() ? -1 : totalSet.getDataPoints().get(0)
                        .getValue(Field.FIELD_STEPS).asInt();
                resultMap.put(GoogleFitController.STEPS, steps);
            }

            // get result for distance from Google Fit
            PendingResult<DailyTotalResult> distanceResult = Fitness.HistoryApi.readDailyTotal
                    (mClient, DataType.AGGREGATE_DISTANCE_DELTA);

            DailyTotalResult totalDistanceResult = distanceResult.await(60, TimeUnit.SECONDS);
            if (totalDistanceResult.getStatus().isSuccess()) {
                // get distance, in meters
                DataSet totalDistanceSet = totalDistanceResult.getTotal();
                distance = totalDistanceSet.isEmpty() ? -1 :
                        totalDistanceSet.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE)
                                .asFloat();
                resultMap.put(GoogleFitController.DISTANCE, distance);
            }

            Log.i(DEBUG_TAG, "buildFitnessClient: " + resultMap.get(STEPS) + "  " +
                    resultMap.get(DISTANCE));
        }
    }
}
