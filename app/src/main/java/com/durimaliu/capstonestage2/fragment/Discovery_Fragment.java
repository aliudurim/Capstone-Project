package com.durimaliu.capstonestage2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.adapter.DiscoveryRecyclerViewAdapter;
import com.durimaliu.capstonestage2.adapter.MenuTripAdapter;
import com.durimaliu.capstonestage2.object.AllTypes;
import com.durimaliu.capstonestage2.object.GetAllTrip;
import com.durimaliu.capstonestage2.request.RequestCallBack;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.durimaliu.capstonestage2.utilitys.appPreferences;

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
public class Discovery_Fragment extends Fragment {

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.rlMenu)
    RelativeLayout rlMenu;

    @Bind(R.id.discoveryRecyclerView)
    RecyclerView discoveryRecyclerView;

    @Bind(R.id.recycleViewTypeTrip)
    RecyclerView recycleViewTypeTrip;

    MenuTripAdapter menuTripAdapter;

    DiscoveryRecyclerViewAdapter discoveryRecyclerViewAdapter;

    LinearLayoutManager mLayoutManager;

    boolean isMenuOpen = false;


    RequestCallBack reqCall;
    Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View gv = inflater.inflate(R.layout.discovery_screen, container, false);
        ButterKnife.bind(this, gv);

        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);

        setDiscoveryRecyclerViewAdapter();
        setMenuRecyclerViewAdapter();
        getTripByType("skiing");
        getAllTypes();


        menuTripAdapter.selectType = new MenuTripAdapter.SelectType() {
            @Override
            public void onSelect(String type) {
                if (!type.equalsIgnoreCase(""))
                    getTripByType(type);
            }
        };
        return gv;
    }

    @OnClick(R.id.imgMenu)
    public void openMenu() {
        isMenuOpen = !isMenuOpen;
        if (isMenuOpen) {
            rlMenu.setVisibility(View.VISIBLE);
        } else {
            rlMenu.setVisibility(View.GONE);
        }
    }

    private void setMenuRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycleViewTypeTrip.setLayoutManager(mLayoutManager);
        menuTripAdapter = new MenuTripAdapter(getActivity());
        recycleViewTypeTrip.setHasFixedSize(true);
        recycleViewTypeTrip.setAdapter(menuTripAdapter);
    }

    private void setDiscoveryRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        discoveryRecyclerView.setLayoutManager(mLayoutManager);
        discoveryRecyclerViewAdapter = new DiscoveryRecyclerViewAdapter(getActivity());
        discoveryRecyclerView.setHasFixedSize(true);
        discoveryRecyclerView.setAdapter(discoveryRecyclerViewAdapter);
    }

    public void getTripByType(String type) {


        Call<List<GetAllTrip>> getAllTripCall = reqCall.getTripsByType(type, appPreferences.getUserToken().getToken());

        progressBar.setVisibility(View.VISIBLE);
        getAllTripCall.enqueue(new Callback<List<GetAllTrip>>() {
            @Override
            public void onResponse(Response<List<GetAllTrip>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        discoveryRecyclerViewAdapter.setGetAllTripListByType(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void getAllTypes() {


        Call<List<AllTypes>> getAllTypes = reqCall.getAllType(appPreferences.getUserToken().getToken());
        getAllTypes.enqueue(new Callback<List<AllTypes>>() {
            @Override
            public void onResponse(Response<List<AllTypes>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        menuTripAdapter.setArrayList(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

    }
}
