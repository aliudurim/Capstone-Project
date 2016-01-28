package com.durimaliu.capstonestage2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.adapter.DetailTripRecyclerViewAdapter;
import com.durimaliu.capstonestage2.adapter.FeedRecyclerViewAdapter;
import com.durimaliu.capstonestage2.customclass.CircleTransform;
import com.durimaliu.capstonestage2.object.GetTripById;
import com.durimaliu.capstonestage2.object.ResponseTrip;
import com.durimaliu.capstonestage2.object.UserToken;
import com.durimaliu.capstonestage2.request.RequestCallBack;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by macbook on 1/7/16.
 */
public class DetailOfTrip extends Activity {

    @Bind(R.id.imgBackgroundTrip)
    ImageView imgBackgroundTrip;

    @Bind(R.id.detailRecyclerView)
    RecyclerView detailRecyclerView;

    DetailTripRecyclerViewAdapter detailTripRecyclerViewAdapter;

    LinearLayoutManager mLayoutManager;


    RequestCallBack reqCall;
    Retrofit retrofit;


    String id = "";

    public static DetailOfTrip detailOfTrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_of_trip);
        ButterKnife.bind(this);
        detailOfTrip = this;

        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");

        setImageBackroundTrip(type);

        setDetailRecyclerViewAdapter();

        getTripById(id);
    }

    private void setDetailRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        detailRecyclerView.setLayoutManager(mLayoutManager);
        detailTripRecyclerViewAdapter = new DetailTripRecyclerViewAdapter(getApplicationContext(), this);
        detailRecyclerView.setHasFixedSize(true);
        detailRecyclerView.setAdapter(detailTripRecyclerViewAdapter);
    }


    private void getTripById(String id) {
        Call<GetTripById> getTripByIdCall = reqCall.getTripById(id, appPreferences.getUserToken().getToken());

        getTripByIdCall.enqueue(new Callback<GetTripById>() {
            @Override
            public void onResponse(Response<GetTripById> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {
                        detailTripRecyclerViewAdapter.setGetTripById(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void joinOrUnJoinedTrip(boolean type, final String id) {
        Call<ResponseTrip> joinOrUnJoinedTrip = null;
        if (type) {
            joinOrUnJoinedTrip = reqCall.unJoinedTrip(id, appPreferences.getUserToken().getToken());
        } else {
            joinOrUnJoinedTrip = reqCall.joinedTrip(id, appPreferences.getUserToken().getToken());
        }

        joinOrUnJoinedTrip.enqueue(new Callback<ResponseTrip>() {
            @Override
            public void onResponse(Response<ResponseTrip> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        getTripById(id);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    @OnClick(R.id.btnBackDetailTrip)
    public void finishDetailClass() {
        finish();
    }

    private void setImageBackroundTrip(String type) {
        if (type.equalsIgnoreCase("skiing")) {
            imgBackgroundTrip.setImageResource(R.mipmap.photo_one);
        } else if (type.equalsIgnoreCase("hiking")) {
            imgBackgroundTrip.setImageResource(R.mipmap.photo_two);
        } else if (type.equalsIgnoreCase("brezovica")) {
            imgBackgroundTrip.setImageResource(R.mipmap.photo_three);
        }
    }
}
