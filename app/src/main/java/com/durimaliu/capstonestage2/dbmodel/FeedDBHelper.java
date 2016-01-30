package com.durimaliu.capstonestage2.dbmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.durimaliu.capstonestage2.object.GetAllTrip;

/**
 * Created by macbook on 1/30/16.
 */
public class FeedDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Scores.db";
    private static final int DATABASE_VERSION = 1;

    public FeedDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CreateScoresTable = "CREATE TABLE IF NOT EXISTS " + GetAllTrip.TRIP_TABLE + " ("
                + GetAllTrip.id_ + " INTEGER PRIMARY KEY,"
                + GetAllTrip.name_ + " TEXT NOT NULL,"
                + GetAllTrip.type_ + " TEXT NOT NULL,"
                + GetAllTrip.description_ + " TEXT NOT NULL,"
                + GetAllTrip.startDate_ + " TEXT NOT NULL,"
                + GetAllTrip.location_lang_ + " REAL NOT NULL,"
                + GetAllTrip.location_lat_ + " REAL NOT NULL,"
                + GetAllTrip.pickup_lang_ + " REAL NOT NULL,"
                + GetAllTrip.pickup_lat_ + " REAL NOT NULL,"
                + GetAllTrip.createdBy_ + " INTEGER NOT NULL,"
                + GetAllTrip.createdAt_ + " TEXT NOT NULL,"
                + GetAllTrip.updated_at_ + " TEXT NOT NULL,"
                + " UNIQUE (" + GetAllTrip.id_ + ") ON CONFLICT REPLACE"
                + " );";
        db.execSQL(CreateScoresTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Remove old values when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + GetAllTrip.TRIP_TABLE);
    }
}