package com.durimaliu.capstonestage2.widget;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by macbook on 1/30/16.
 */
public class myFetchService extends IntentService {

    public myFetchService() {
        super("myFetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getExtras() != null) {
            Intent service_start = new Intent(myFetchService.this, MyWidgetIntentReceiver.class);
            service_start.putExtra("title", intent.getExtras().getString("title"));
            service_start.putExtra("type", intent.getExtras().getString("type"));
            sendBroadcast(service_start);
        }
    }
}
