package com.durimaliu.capstonestage2.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 1/5/16.
 */
public class Settings extends Activity {

    @Bind(R.id.chkState)
    ToggleButton chkState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        ButterKnife.bind(this);

        chkState.setChecked(appPreferences.getBoolForPush());
        chkState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appPreferences.saveBoolForPush(isChecked);
                    chkState.setChecked(isChecked);
                } else {
                    appPreferences.saveBoolForPush(isChecked);
                    chkState.setChecked(isChecked);
                }
            }
        });
    }

    @OnClick(R.id.rlMyAccount)
    public void startAccountClass() {
        startActivity(new Intent(this, MyAccount.class));
    }

    @OnClick(R.id.rlSendFeedBack)
    public void sendFeedBack() {

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "nTrip FeedBack");
        email.putExtra(Intent.EXTRA_TEXT, "Write FeedBack");
        //need this to prompts email client only
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    @OnClick(R.id.rlAboutUs)
    public void openAboutUs() {
        startActivity(new Intent(this, AboutUs.class));
    }


    @OnClick(R.id.btnBack)
    public void finishClass() {
        finish();
    }
}
