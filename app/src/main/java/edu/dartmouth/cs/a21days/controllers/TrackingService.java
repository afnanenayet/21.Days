package edu.dartmouth.cs.a21days.controllers;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Location tracking for accountablity in habits.
 */

public class TrackingService extends Service implements LocationListener {
    public static final String TRACKING_ACTION = "21days.TACKING_ACTION";
    public static final String KEY_LOCATION = "LOCATION";
    // binder
    private final IBinder mBinder = new TrackingBinder();
    // location manager
    LocationManager mLocationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TTAG", "onBind: ");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StartLocationUpdate();
        Log.d("TTAG", "onStartCommand: ");
        // We want the service to continue running until it is explicitly stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    // start location updates
    private void StartLocationUpdate() {
        // set location criteria
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // get provider
        String provider = mLocationManager.getBestProvider(criteria, true);

        // check location permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // get location and keep on getting location updates
        Location location = mLocationManager.getLastKnownLocation(provider);
        onLocationChanged(location);
        mLocationManager.requestLocationUpdates(provider, 500, 0, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        // broadcast intent when location is changed
        Intent onLocationUpdated = new Intent(TRACKING_ACTION);
        onLocationUpdated.putExtra(KEY_LOCATION, location);
        LocalBroadcastManager.getInstance(this).sendBroadcast(onLocationUpdated);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // empty, needed for location listener
    }

    @Override
    public void onProviderEnabled(String provider) {
        // empty, needed for location listener
    }

    @Override
    public void onProviderDisabled(String provider) {
        // empty, needed for location listener
    }

    public class TrackingBinder extends Binder {
        TrackingService getService() {
            // Return instance so clients can call public methods
            return TrackingService.this;
        }
    }
}
