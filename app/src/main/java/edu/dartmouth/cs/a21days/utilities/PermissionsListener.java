package edu.dartmouth.cs.a21days.utilities;

import android.app.Activity;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import edu.dartmouth.cs.a21days.controllers.MainActivity;

/**
 * Created by aenayet on 3/1/17.
 */

/**
 * A class that implements listeners for {@link PermissionRequestUtility} and posts feedback
 */
public class PermissionsListener implements MultiplePermissionsListener {
    private MainActivity mActivity;

    /**
     * Class constructor
     * @param activity The activity that responses will be displayed to
     */
    public PermissionsListener(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {

    }

    /**
     * Show permission rationale if system asks
     */
    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                   PermissionToken token) {
        mActivity.showPermissionRationale(token);
    }
}
