package com.durimaliu.capstonestage2.adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.activity.DetailGoogleMap;
import com.durimaliu.capstonestage2.activity.DetailOfTrip;
import com.durimaliu.capstonestage2.customclass.CircleTransform;
import com.durimaliu.capstonestage2.object.GetTripById;
import com.durimaliu.capstonestage2.object.Participant;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DurimAliu on 03/01/16.
 */
public class DetailTripRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Context ctx;
    Activity activity;

    GetTripById getTripById = new GetTripById();

    public void setGetTripById(GetTripById getTripById) {
        this.getTripById = getTripById;
        notifyDataSetChanged();
    }


    public DetailTripRecyclerViewAdapter(Context _ctx, Activity activity) {
        ctx = _ctx;
        this.activity = activity;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            //inflate your layout and pass it to view holder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_trip_header, parent, false);
            return new HeaderHolder(v);
        } else if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_trip_item, parent, false);
            return new ItemHolder(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) {
            //cast holder to VHItem and set data
            Picasso.with(ctx).load(getTripById.getCreator().getProfilePic())
                    .transform(new CircleTransform())
                    .into(((HeaderHolder) holder)
                            .imgProfileDetail);

            ((HeaderHolder) holder).txtProfileCreator.setText("" + getTripById.getCreator().getName());
            ((HeaderHolder) holder).txtNameOfTripCreator.setText("" + getTripById.getName());
            ((HeaderHolder) holder).txtTypeCreator.setText("" + getTripById.getType());


            ((HeaderHolder) holder).rlJoinTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailOfTrip.detailOfTrip.joinOrUnJoinedTrip(getTripById.getJoined(), String.valueOf(getTripById.getId()));
                }
            });

            if (getTripById.getJoined()) {
                ((HeaderHolder) holder).txtJoinTrip.setText("JOINED");
            } else {
                ((HeaderHolder) holder).txtJoinTrip.setText("JOIN");
            }

            if (getTripById.getParticipants().size() > 0) {
                ((HeaderHolder) holder).rlJoinedFriends.setVisibility(View.VISIBLE);
            } else {
                ((HeaderHolder) holder).rlJoinedFriends.setVisibility(View.GONE);
            }

            ((HeaderHolder) holder).rlBottomBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(activity, DetailGoogleMap.class);

                    System.out.println("aa22aa: " + getTripById.getLocationLang());

                    Bundle b = new Bundle();
                    b.putDouble("location_lang", getTripById.getLocationLang());
                    b.putDouble("location_lat", getTripById.getLocationLat());
                    b.putDouble("pickup_lang", getTripById.getPickupLang());
                    b.putDouble("pickup_lat", getTripById.getPickupLat());

                    intent.putExtras(b);
                    activity.startActivity(intent);
                }
            });

        } else if (holder instanceof ItemHolder) {
            //cast holder to VHHeader and set data for header.

            Participant participant = getItem(position);

            Picasso.with(ctx).load(participant.getProfilePic())
                    .transform(new CircleTransform())
                    .into(((ItemHolder) holder).imgProfileCell);

            ((ItemHolder) holder).txtNamePeopleJoined.setText("" + participant.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (getTripById.getParticipants() != null)
            return getTripById.getParticipants().size() + 1;
        else
            return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Participant getItem(int position) {
        return getTripById.getParticipants().get(position - 1);
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgProfileCell)
        ImageView imgProfileCell;

        @Bind(R.id.txtNamePeopleJoined)
        TextView txtNamePeopleJoined;

        public ItemHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgProfileDetail)
        ImageView imgProfileDetail;

        @Bind(R.id.rlHeaderDetailTrip)
        RelativeLayout rlHeaderDetailTrip;

        @Bind(R.id.txtProfileCreator)
        TextView txtProfileCreator;

        @Bind(R.id.txtNameOfTripCreator)
        TextView txtNameOfTripCreator;

        @Bind(R.id.txtTypeCreator)
        TextView txtTypeCreator;

        @Bind(R.id.rlJoinTrip)
        RelativeLayout rlJoinTrip;

        @Bind(R.id.txtJoinTrip)
        TextView txtJoinTrip;

        @Bind(R.id.rlJoinedFriends)
        RelativeLayout rlJoinedFriends;

        @Bind(R.id.rlBottomBar)
        RelativeLayout rlBottomBar;


        public HeaderHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            rlHeaderDetailTrip.setLayoutParams(new RelativeLayout.LayoutParams(Utilitys.screenWidth, Utilitys.screenHeight - Utilitys.dpToPx(60)));
        }
    }

}

