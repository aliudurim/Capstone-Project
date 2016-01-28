package com.durimaliu.capstonestage2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.adapter.FeedRecyclerViewAdapter;
import com.durimaliu.capstonestage2.object.GetAllTrip;
import com.durimaliu.capstonestage2.request.RequestCallBack;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.durimaliu.capstonestage2.utilitys.appPreferences;

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

        System.out.println("Durim L : " + gv);


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

                System.out.println("2)response messagee " + response.message());

                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        System.out.println("3)response messagee " + response.body().size());
                        feedRecyclerViewAdapter.setGetAllTripList(response.body());
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
                System.out.println("5) response messagee " + t.getMessage());
            }
        });

    }
}
