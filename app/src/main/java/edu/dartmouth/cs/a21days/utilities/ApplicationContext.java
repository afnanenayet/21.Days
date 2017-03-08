package edu.dartmouth.cs.a21days.utilities;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Provides method to access application context from utility classes
 */
public class ApplicationContext extends Application {
    private static Context mContext;
    private static String DEBUG_TAG = "ApplicationContext";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(DEBUG_TAG, "Application started");
        ApplicationContext.mContext = getApplicationContext();
    }

    /**
     * Getter for application context
     * @return Context for application
     */
    public static Context getContext() {
        return ApplicationContext.mContext;
    }

    /**
     * Setter for application context
     * @param context application context
     */
    public static void setContext(Context context) {
        mContext = context;
    }

}
