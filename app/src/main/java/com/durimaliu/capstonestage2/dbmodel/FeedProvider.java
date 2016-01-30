package com.durimaliu.capstonestage2.dbmodel;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.durimaliu.capstonestage2.object.GetAllTrip;

/**
 * Created by macbook on 1/30/16.
 */
public class FeedProvider extends ContentProvider {

    private static FeedDBHelper feedDBHelper;

    private static final int MATCHES_ALL = 102;
    private static final int MATCHES_LAST = 103;

    private UriMatcher muriMatcher = buildUriMatcher();

    private static final SQLiteQueryBuilder ScoreQuery =
            new SQLiteQueryBuilder();

    private static final String TRIP_BY_LAST = GetAllTrip.CONTENT_ITEM_TYPE + "limit 1";

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = GetAllTrip.BASE_CONTENT_URI.toString();
        matcher.addURI(authority, null, MATCHES_ALL);
        matcher.addURI(authority, "id", MATCHES_LAST);
        return matcher;
    }

    private int match_uri(Uri uri) {
        String link = uri.toString();
        {
            if (link.contentEquals(GetAllTrip.BASE_CONTENT_URI.toString())) {
                return MATCHES_ALL;
            } else if (link.contentEquals(GetAllTrip.buildWithLastTrip().toString())) {
                return MATCHES_LAST;
            }
        }
        return -1;
    }

    @Override
    public boolean onCreate() {
        feedDBHelper = new FeedDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        //Log.v(FetchScoreTask.LOG_TAG,uri.getPathSegments().toString());
        int match = match_uri(uri);
        //Log.v(FetchScoreTask.LOG_TAG,SCORES_BY_LEAGUE);
        //Log.v(FetchScoreTask.LOG_TAG,selectionArgs[0]);
        //Log.v(FetchScoreTask.LOG_TAG,String.valueOf(match));
        switch (match) {
            case MATCHES_ALL:
                retCursor = feedDBHelper.getReadableDatabase().query(
                        GetAllTrip.TRIP_TABLE,
                        projection, null, null, null, null, sortOrder);
                break;
            case MATCHES_LAST:
                retCursor = feedDBHelper.getReadableDatabase().query(
                        GetAllTrip.TRIP_TABLE,
                        projection, TRIP_BY_LAST, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = muriMatcher.match(uri);
        switch (match) {
            case MATCHES_ALL:
                return GetAllTrip.CONTENT_TYPE;
            case MATCHES_LAST:
                return GetAllTrip.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri :" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = feedDBHelper.getWritableDatabase();
        //db.delete(DatabaseContract.SCORES_TABLE,null,null);
        //Log.v(FetchScoreTask.LOG_TAG,String.valueOf(muriMatcher.match(uri)));
        switch (match_uri(uri)) {
            case MATCHES_ALL:
                db.beginTransaction();
                int returncount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insertWithOnConflict(GetAllTrip.TRIP_TABLE, null, value,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1) {
                            returncount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returncount;
            default:
                return super.bulkInsert(uri, values);
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
