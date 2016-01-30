package com.durimaliu.capstonestage2.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.durimaliu.capstonestage2.R;

/**
 * Created by macbook on 1/30/16.
 */
public class MyWidgetIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        updateWidgetPictureAndButtonListener(context, intent.getExtras().getString("title"), intent.getExtras().getString("type"));
    }

    private void updateWidgetPictureAndButtonListener(Context context, String title, String type) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_screen);

        remoteViews.setTextViewText(R.id.txtNameOfTripWidget, title);
        remoteViews.setTextViewText(R.id.txtTypeWidget, type);

        MyWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
    }
}
