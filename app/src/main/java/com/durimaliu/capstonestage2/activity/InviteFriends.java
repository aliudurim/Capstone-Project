package com.durimaliu.capstonestage2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.adapter.FeedRecyclerViewAdapter;
import com.durimaliu.capstonestage2.adapter.InviteFriendsAdapter;
import com.durimaliu.capstonestage2.object.FbFriend;
import com.durimaliu.capstonestage2.object.UserToken;
import com.durimaliu.capstonestage2.request.RequestCallBack;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by macbook on 1/26/16.
 */
public class InviteFriends extends Activity {

    @Bind(R.id.inviteTripList)
    RecyclerView inviteTripList;

    InviteFriendsAdapter inviteFriendsAdapter;

    LinearLayoutManager mLayoutManager;


    RequestCallBack reqCall;
    Retrofit retrofit;

    Intent returnIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_trip);
        ButterKnife.bind(this);


        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);
        setFeedRecyclerViewAdapter();
        getFbFriends();
    }

    private void setFeedRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(this);
        inviteTripList.setLayoutManager(mLayoutManager);
        inviteFriendsAdapter = new InviteFriendsAdapter(this);
        inviteTripList.setHasFixedSize(true);
        inviteTripList.setAdapter(inviteFriendsAdapter);
    }

    private void getFbFriends() {

        Call<List<FbFriend>> getFbFriends = reqCall.getFaceBookFriends(appPreferences.getUserToken().getToken(), appPreferences.getFaceBookTokenResponse());

        getFbFriends.enqueue(new Callback<List<FbFriend>>() {
            @Override
            public void onResponse(Response<List<FbFriend>> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {
                        inviteFriendsAdapter.setGetFbFriends(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    StringBuilder stringBuilder;

    void getFriendList() {
        stringBuilder = new StringBuilder();
        List<FbFriend> getFbFriends = new ArrayList<>();
        for (int k = 0; k < inviteFriendsAdapter.getFbFriends().size(); k++) {
            if (inviteFriendsAdapter.getFbFriends().get(k).isSelected) {
                getFbFriends.add(inviteFriendsAdapter.getFbFriends().get(k));
            }

        }
        if (getFbFriends.size() > 0) {
            for (int i = 0; i < getFbFriends.size(); i++) {
                int lastItem = getFbFriends.size() - 1;
                if (lastItem == i) {
                    stringBuilder.append(getFbFriends.get(i).getId());
                } else {
                    stringBuilder.append(getFbFriends.get(i).getId() + ",");
                }

            }
        }
        returnIntent.putExtra("invited_friends", stringBuilder.toString());
        setResult(Activity.RESULT_OK, returnIntent);
    }


    @OnClick(R.id.imgBackInvite)
    public void finishInvite() {
        getFriendList();
        finish();
    }

    @Override
    public void onBackPressed() {
        getFriendList();
        super.onBackPressed();

    }
}
