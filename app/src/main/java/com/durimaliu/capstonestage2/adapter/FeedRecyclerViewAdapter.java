package com.durimaliu.capstonestage2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.activity.DetailOfTrip;
import com.durimaliu.capstonestage2.object.GetAllTrip;
import com.durimaliu.capstonestage2.utilitys.Utilitys;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DurimAliu on 03/01/16.
 */
public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context ctx;

    public void setGetAllTripList(List<GetAllTrip> getAllTripList) {
        this.getAllTripList = getAllTripList;
        notifyDataSetChanged();
    }

    List<GetAllTrip> getAllTripList = new ArrayList<>();

    public FeedRecyclerViewAdapter(Context _ctx) {
        ctx = _ctx;
    }

    public class RecycleHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rlCellFeed)
        RelativeLayout rlCellFeed;

        @Bind(R.id.txtNameOfTrip)
        TextView txtNameOfTrip;

        @Bind(R.id.txtType)
        TextView txtType;

        @Bind(R.id.imgPicTrip)
        ImageView imgPicTrip;

        public RecycleHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            rlCellFeed.setLayoutParams(new RelativeLayout.LayoutParams(Utilitys.screenWidth, (int) (Utilitys.screenHeight * 0.2312f)));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_cell, parent, false);
        return new RecycleHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ((RecycleHolder) holder).txtNameOfTrip.setText(getAllTripList.get(position).getName());
        ((RecycleHolder) holder).txtType.setText(getAllTripList.get(position).getType());

        setPictureType(getAllTripList.get(position).getType(), ((RecycleHolder) holder).imgPicTrip);

        ((RecycleHolder) holder).rlCellFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, DetailOfTrip.class);
                intent.putExtra("id", getAllTripList.get(position).getId() + "");
                intent.putExtra("type", "skiing");
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getAllTripList.size();
    }

    private void setPictureType(String type, ImageView img) {
        if (type.equalsIgnoreCase("Hiking")) {
            img.setImageResource(R.mipmap.pic_two);
        } else if (type.equalsIgnoreCase("Skiing")) {
            img.setImageResource(R.mipmap.pic_one);
        } else {
            img.setImageResource(R.mipmap.pic_three);
        }
    }

}

