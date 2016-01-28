package com.durimaliu.capstonestage2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.durimaliu.capstonestage2.fragment.Discovery_Fragment;
import com.durimaliu.capstonestage2.fragment.Feed_Fragment;
import com.durimaliu.capstonestage2.fragment.Profile_Fragment;

/**
 * Created by DurimAliu on 03/01/16.
 */
public class FragmentHolderAdapter extends FragmentStatePagerAdapter {

    private static int NUM_ITEMS = 3;
    Feed_Fragment feed_fragment;
    Discovery_Fragment discovery_fragment;
    Profile_Fragment profile_fragment;

    public FragmentHolderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                feed_fragment = new Feed_Fragment();
                return feed_fragment;
            case 1:
                discovery_fragment = new Discovery_Fragment();
                return discovery_fragment;
            case 2:
                profile_fragment = new Profile_Fragment();
                return profile_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
