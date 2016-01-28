package com.durimaliu.capstonestage2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.activity.FragmentHolder;
import com.durimaliu.capstonestage2.activity.Settings;
import com.durimaliu.capstonestage2.adapter.FeedRecyclerViewAdapter;
import com.durimaliu.capstonestage2.adapter.ProfileRecyclerViewAdapter;
import com.durimaliu.capstonestage2.customclass.CircleTransform;
import com.durimaliu.capstonestage2.object.GetAllTrip;
import com.durimaliu.capstonestage2.object.User;
import com.durimaliu.capstonestage2.object.UserToken;
import com.durimaliu.capstonestage2.request.RequestCallBack;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
 * Created by DurimAliu on 03/01/16.
 */
public class Profile_Fragment extends Fragment {
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.view3)
    View view3;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.txtUpcomingTripsNumber)
    TextView txtUpcomingTripsNumber;

    @Bind(R.id.txtYourTripsNumber)
    TextView txtYourTripsNumber;

    @Bind(R.id.txtPreviousTripsNumber)
    TextView txtPreviousTripsNumber;

    @Bind(R.id.txtProfileName)
    TextView txtProfileName;

    @Bind(R.id.imgProfilePic)
    ImageView imgProfilePic;

    @Bind(R.id.rlHeaderProfile)
    RelativeLayout rlHeaderProfile;

    @Bind(R.id.profileRecyclerView)
    RecyclerView profileRecyclerView;

    ProfileRecyclerViewAdapter profileRecyclerViewAdapter;

    LinearLayoutManager mLayoutManager;

    RequestCallBack reqCall;
    Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View gv = inflater.inflate(R.layout.profile_screen, container, false);
        ButterKnife.bind(this, gv);


        setFeedRecyclerViewAdapter();

        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);

        getUserInfo();
        trips("YourTrips");

        return gv;
    }

    private void getUserInfo() {
        Call<User> authCall = reqCall.getUser(appPreferences.getUserToken().getToken());

        authCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {
                        String json_string = new Gson().toJson(response.body(), User.class);
                        appPreferences.saveUserResponse(json_string);



                        Picasso.with(getActivity()).load(appPreferences.getUser().getProfilePic()).transform(new CircleTransform()).into(imgProfilePic);
                        txtProfileName.setText(appPreferences.getUser().getName());


                        txtUpcomingTripsNumber.setText("" + appPreferences.getUser().getUpcommingtrips());
                        txtPreviousTripsNumber.setText("" + appPreferences.getUser().getPrevioustrips());
                        txtYourTripsNumber.setText("" + appPreferences.getUser().getCreated_trips());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @OnClick(R.id.rlUpcomingTrips)
    public void onUpcomingTrips() {

        trips("Upcoming");
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
    }

    @OnClick(R.id.rlYourTrips)
    public void onYourTrips() {
        trips("YourTrips");
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.VISIBLE);
        view3.setVisibility(View.GONE);
    }

    @OnClick(R.id.rlPreviousTrips)
    public void onPreviousTrips() {
        trips("Previews");
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnSettings)
    public void openSettingsScreen() {
        startActivity(new Intent(getActivity(), Settings.class));
    }

    private void setFeedRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        profileRecyclerView.setLayoutManager(mLayoutManager);
        profileRecyclerViewAdapter = new ProfileRecyclerViewAdapter(getActivity());
        profileRecyclerView.setHasFixedSize(true);
        profileRecyclerView.setAdapter(profileRecyclerViewAdapter);

        rlHeaderProfile.post(new Runnable() {
            @Override
            public void run() {
                profileRecyclerView.setPadding(0, rlHeaderProfile.getHeight(), 0, 0);
                profileRecyclerView.setClipToPadding(false);
            }
        });


        profileRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                rlHeaderProfile.setY(-profileRecyclerView.computeVerticalScrollOffset());
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void trips(String type) {

        Call<List<GetAllTrip>> getAllTripCall = null;

        if (type.equalsIgnoreCase("YourTrips")) {
            getAllTripCall = reqCall.getOwnTrip(appPreferences.getUserToken().getToken());
        } else if (type.equalsIgnoreCase("Upcoming")) {
            getAllTripCall = reqCall.getUpcommingTrips(appPreferences.getUserToken().getToken());
        } else if (type.equalsIgnoreCase("Previews")) {
            getAllTripCall = reqCall.getPreviousTrips(appPreferences.getUserToken().getToken());
        }
        progressBar.setVisibility(View.VISIBLE);

        getAllTripCall.enqueue(new Callback<List<GetAllTrip>>() {
            @Override
            public void onResponse(Response<List<GetAllTrip>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        profileRecyclerViewAdapter.setGetAllTripList(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
