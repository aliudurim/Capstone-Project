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
import com.durimaliu.capstonestage2.object.AllTypes;
import com.durimaliu.capstonestage2.object.GetAllTrip;
import com.durimaliu.capstonestage2.utilitys.Utilitys;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DurimAliu on 03/01/16.
 */
public class MenuTripAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public interface SelectType {
        void onSelect(String type);
    }

    public SelectType selectType;

    Context ctx;

    public void setArrayList(List<AllTypes> arrayList) {
        this.arrayList = arrayList;
    }

    List<AllTypes> arrayList = new ArrayList<>();

    public MenuTripAdapter(Context _ctx) {
        ctx = _ctx;
    }

    public class RecycleHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txtNameOfType)
        TextView txtNameOfType;

        @Bind(R.id.rlCellMenuTrip)
        RelativeLayout rlCellMenuTrip;

        public RecycleHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_trip_cell, parent, false);
        return new RecycleHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((RecycleHolder) holder).txtNameOfType.setText(arrayList.get(position).getType());
        ((RecycleHolder) holder).rlCellMenuTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType.onSelect(arrayList.get(position).getType());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}

