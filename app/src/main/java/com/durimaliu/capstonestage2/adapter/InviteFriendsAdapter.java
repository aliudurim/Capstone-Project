package com.durimaliu.capstonestage2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.activity.DetailOfTrip;
import com.durimaliu.capstonestage2.customclass.CircleTransform;
import com.durimaliu.capstonestage2.object.FbFriend;
import com.durimaliu.capstonestage2.object.GetAllTrip;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DurimAliu on 03/01/16.
 */
public class InviteFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context ctx;

    public void setGetFbFriends(List<FbFriend> getFbFriends) {
        this.getFbFriends = getFbFriends;
        notifyDataSetChanged();
    }

    List<FbFriend> getFbFriends = new ArrayList<>();

    public InviteFriendsAdapter(Context _ctx) {
        ctx = _ctx;
    }

    public class RecycleHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgProfileCell)
        ImageView imgProfileCell;

        @Bind(R.id.txtNameFrends)
        TextView txtNameFrends;

        @Bind(R.id.rlInviteCell)
        RelativeLayout rlInviteCell;

        @Bind(R.id.imgInvite)
        ImageButton imgInvite;


        public RecycleHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    public List<FbFriend> getFbFriends() {
        return this.getFbFriends;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_trip_cell, parent, false);
        return new RecycleHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Picasso.with(ctx).load(getFbFriends.get(position).getProfilePic()).transform(new CircleTransform()).into(((RecycleHolder) holder).imgProfileCell);
        ((RecycleHolder) holder).txtNameFrends.setText(getFbFriends.get(position).getName());


        if (getFbFriends.get(position).isSelected()) {
            ((RecycleHolder) holder).imgInvite.setImageResource(R.mipmap.invited);
        } else {
            ((RecycleHolder) holder).imgInvite.setImageResource(R.mipmap.invite);
        }

        ((RecycleHolder) holder).imgInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFbFriends.get(position).isSelected()) {
                    getFbFriends.get(position).isSelected = false;
                } else {
                    getFbFriends.get(position).isSelected = true;
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getFbFriends.size();
    }
}

