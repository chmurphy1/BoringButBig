package com.christopherwmurphy.boringbutbigapp.analytics;

import android.app.Application;

import com.christopherwmurphy.boringbutbigapp.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/*  Used the following link as a resource
    https://developers.google.com/analytics/devguides/collection/android/v4/
 */
public class AnalyticsApplication extends Application {

    private static GoogleAnalytics appAnalytics;
    private static Tracker appTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        appAnalytics = GoogleAnalytics.getInstance(this);
    }

    public Tracker getDefaultTracker() {
        if (appTracker == null) {
            synchronized (AnalyticsApplication.class) {
                if(appTracker == null) {
                    appTracker = appAnalytics.newTracker(R.xml.global_tracker);
                }
            }
        }
        return appTracker;
    }
}
