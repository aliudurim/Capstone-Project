package com.durimaliu.capstonestage2.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.activity.Ntrip;
import com.durimaliu.capstonestage2.adapter.FeedRecyclerViewAdapter;
import com.durimaliu.capstonestage2.dbmodel.FeedProvider;
import com.durimaliu.capstonestage2.object.GetAllTrip;
import com.durimaliu.capstonestage2.request.RequestCallBack;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.durimaliu.capstonestage2.widget.myFetchService;
import com.google.android.gms.analytics.Tracker;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by DurimAliu on 03/01/16.
 */
public class Feed_Fragment extends Fragment {

//    @Override
//    public void setMenuVisibility(final boolean visible) {
//        super.setMenuVisibility(visible);
//        if (visible) {
//            System.out.println("durim aliuuuuuuuuuuu ndajjdajda");
//        }
//    }

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.feedRecyclerView)
    RecyclerView feedRecyclerView;

    FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    LinearLayoutManager mLayoutManager;


    RequestCallBack reqCall;
    Retrofit retrofit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View gv = inflater.inflate(R.layout.feed_screen, container, false);

        ButterKnife.bind(this, gv);
        setFeedRecyclerViewAdapter();


        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);

        getAllTrip();


        return gv;
    }

    private void update_scores(String title, String type) {
        Intent service_start = new Intent(getActivity(), myFetchService.class);
        service_start.putExtra("title", title);
        service_start.putExtra("type", type);
        getActivity().startService(service_start);
    }

    private void setFeedRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        feedRecyclerView.setLayoutManager(mLayoutManager);
        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter(getActivity());
        feedRecyclerView.setHasFixedSize(true);
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);
    }

    private void getAllTrip() {


        Call<List<GetAllTrip>> getAllTripCall = reqCall.getAllTrip(appPreferences.getUserToken().getToken());

        progressBar.setVisibility(View.VISIBLE);
        getAllTripCall.enqueue(new Callback<List<GetAllTrip>>() {
            @Override
            public void onResponse(Response<List<GetAllTrip>> response) {


                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {

                        insertData(response);

                        feedRecyclerViewAdapter.setGetAllTripList(response.body());

                        if (response.body().size() > 0) {
                            update_scores(response.body().get(0).getName(), response.body().get(0).getType());
                        }

//                        Cursor cursor = getActivity().getContentResolver().query(GetAllTrip.BASE_CONTENT_URI, null, null, null, null);
//                        if (cursor != null) {
//                            cursor.moveToFirst();
//                            while (cursor.moveToNext()) {
//                                System.out.println("PROVIDERU JONE 2 id : " + cursor.getString(0) + " Name: " + cursor.getString(1));
//                            }
//                        }

                    }
                } else {
                    try {
                        System.out.println("4)response messagee " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void insertData(Response<List<GetAllTrip>> response) {
        ContentValues[] content_values = new ContentValues[response.body().size()];
        for (int i = 0; i < response.body().size(); i++) {
            GetAllTrip trip = response.body().get(i);
            content_values[i] = trip.getContentValues();
        }

        int integer = getActivity().getContentResolver().bulkInsert(GetAllTrip.BASE_CONTENT_URI, content_values);
//        System.out.println("PROVIDERU JONE 1 : " + integer);
    }
}
