package com.durimaliu.capstonestage2.activity;

import android.app.Activity;
import android.os.Bundle;

import com.durimaliu.capstonestage2.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 1/27/16.
 */
public class AboutUs extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnBack)
    public void finishClass() {
        onBackPressed();
    }
}
