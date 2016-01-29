package com.durimaliu.capstonestage2.activity;

import android.app.Application;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by macbook on 1/27/16.
 */
public class Ntrip extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        appPreferences.init(this);
    }

    // The following line should be changed to include the correct property id.
    private static final String PROPERTY_ID = "UA-73074448-1";

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     * <p/>
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public Ntrip() {
        super();
    }

    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(PROPERTY_ID);

            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
}
