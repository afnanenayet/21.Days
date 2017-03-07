package edu.dartmouth.cs.a21days.controllers;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

/**
 * Controller that allows access to Google fit.
 * <p>
 * Created by aenayet on 3/1/17.
 */
public class GoogleFitController {
    // Lets us keep track of client connection status
    private GoogleApiClient mClient = null;
    private MainActivity mActivity;
    private static final String DEBUG_TAG = "GoogleFitController";

    // constructor
    public GoogleFitController(MainActivity mainActivity) {
        mActivity = mainActivity;
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
                    // .addApi()
                    .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                    .addConnectionCallbacks(
                            new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {
                                    Log.i(DEBUG_TAG, "Connected!!!");
                                    // Now you can make calls to the Fitness APIs.
                                    // findFitnessDataSources();
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
                    .enableAutoManage(mActivity, 0, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.i(DEBUG_TAG, "Google Play services connection failed. Cause: " +
                                    result.toString());
                        }
                    })
                    .build();
        }
    }
}