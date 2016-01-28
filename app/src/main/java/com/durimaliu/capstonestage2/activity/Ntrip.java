package com.durimaliu.capstonestage2.activity;

import android.app.Application;

import com.durimaliu.capstonestage2.utilitys.appPreferences;

/**
 * Created by macbook on 1/27/16.
 */
public class Ntrip extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        appPreferences.init(this);
    }
}
