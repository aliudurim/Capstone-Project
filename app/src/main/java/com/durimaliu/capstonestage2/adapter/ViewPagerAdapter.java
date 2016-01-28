package com.durimaliu.capstonestage2.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.durimaliu.capstonestage2.R;

/**
 * Created by DurimAliu on 30/12/15.
 */
public class ViewPagerAdapter extends PagerAdapter {
    Context ctx;

    ImageView imgPreview;

    int[] arrayImage = {R.mipmap.fblogin_one
            , R.mipmap.fblogin_two
            , R.mipmap.fblogin_three
            , R.mipmap.fblogin_four
            , R.mipmap.fblogin_five};


    public ViewPagerAdapter(Context _ctx) {
        this.ctx = _ctx;
    }

    @Override
    public Object instantiateItem(ViewGroup viewi, final int position) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.view_pager_cell, viewi, false);

        imgPreview = (ImageView) view.findViewById(R.id.imgPreview);

        imgPreview.setImageResource(arrayImage[position]);

        viewi.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return arrayImage.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }
}
