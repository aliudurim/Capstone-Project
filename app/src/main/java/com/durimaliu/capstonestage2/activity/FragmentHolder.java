package com.durimaliu.capstonestage2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.adapter.FragmentHolderAdapter;
import com.durimaliu.capstonestage2.utilitys.appPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import android.util.Log;

/**
 * Created by DurimAliu on 03/01/16.
 */
public class FragmentHolder extends FragmentActivity {

    @Bind(R.id.fragmentViewPager)
    ViewPager fragmentViewPager;

    FragmentHolderAdapter fragmentHolderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.fragment_holder);
        ButterKnife.bind(this);
        setFragmentHolderAdapter();


    }

    private void setFragmentHolderAdapter() {
        fragmentHolderAdapter = new FragmentHolderAdapter(getSupportFragmentManager());
        fragmentViewPager.setAdapter(fragmentHolderAdapter);
    }


    @OnTouch(R.id.fragmentViewPager)
    boolean onTouch() {
        return true;
    }

    @OnClick(R.id.rlCreateTrip)
    public void createTrip() {
        startActivity(new Intent(FragmentHolder.this, CreateTrip.class));
    }

    @OnClick(R.id.rlFeed)
    public void setPagerToFeedFragment() {
        fragmentViewPager.setCurrentItem(0, false);
    }

    @OnClick(R.id.rlDiscovery)
    public void setPagerToDiscoveryFragment() {
        fragmentViewPager.setCurrentItem(1, false);
    }

    @OnClick(R.id.rlProfile)
    public void setPagerToProfileFragment() {
        fragmentViewPager.setCurrentItem(2, false);
    }

    void getDataGent() {

    }

    void getData() {
        if (appPreferences.getImFromPush()) {
            appPreferences.saveImFromPush(false);
            Intent intent_detail = new Intent(FragmentHolder.this, DetailOfTrip.class);
            String id = appPreferences.getPushMessage();
            intent_detail.putExtra("id", id);
            intent_detail.putExtra("type", "skiing");
            startActivity(intent_detail);
        }
    }
}
