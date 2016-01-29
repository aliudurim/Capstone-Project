package com.durimaliu.capstonestage2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public void setGetAllTripList(List<GetAllTrip> getAllTripList) {
        this.getAllTripList = getAllTripList;
        notifyDataSetChanged();
    }

    List<GetAllTrip> getAllTripList = new ArrayList<>();


    Context ctx;

    public ProfileRecyclerViewAdapter(Context _ctx) {
        ctx = _ctx;
    }

    public class RecycleHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rlCellProfile)
        RelativeLayout rlCellProfile;

        @Bind(R.id.txtTitleOfTip)
        TextView txtTitleOfTip;

        @Bind(R.id.txtTypeOfTrip)
        TextView txtTypeOfTrip;

        public RecycleHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            rlCellProfile.setLayoutParams(new RelativeLayout.LayoutParams(Utilitys.screenWidth, (int) (Utilitys.screenHeight * 0.2312f)));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_cell, parent, false);
        return new RecycleHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((RecycleHolder) holder).rlCellProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, DetailOfTrip.class);
                intent.putExtra("type", "brezovica");
                intent.putExtra("id", getAllTripList.get(position).getId() + "");
                ctx.startActivity(intent);
            }
        });
        ((RecycleHolder) holder).txtTitleOfTip.setText("" + getAllTripList.get(position).getName());
        ((RecycleHolder) holder).txtTypeOfTrip.setText("" + getAllTripList.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return getAllTripList.size();
    }


}

