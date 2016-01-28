package com.durimaliu.capstonestage2.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.route.Route;
import com.durimaliu.capstonestage2.route.Routing;
import com.durimaliu.capstonestage2.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 1/25/16.
 */
public class DetailGoogleMap extends Activity implements RoutingListener {

    Polyline polyline;
    private GoogleMap googleMap;

    double location_lang, location_lat, pickup_lang, pickup_lat;

    LatLng start;
    LatLng end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_google_map);
        ButterKnife.bind(this);

        Bundle b = this.getIntent().getExtras();

        location_lang = b.getDouble("location_lang");
        location_lat = b.getDouble("location_lat");
        pickup_lang = b.getDouble("pickup_lang");
        pickup_lat = b.getDouble("pickup_lat");


        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();


            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }


            Routing routing = new Routing(Routing.TravelMode.DRIVING);
            routing.registerListener(DetailGoogleMap.this);

            start = new LatLng(location_lat, location_lang);
            end = new LatLng(pickup_lat, pickup_lang);


            routing.execute(start, end);

            final Handler handler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 11));
                }
            };

            handler.postDelayed(r, 500);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    @OnClick(R.id.imgBackDetail)
    public void finishClass() {
        finish();
    }

    @Override
    public void onRoutingFailure() {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions, Route route) {
        if (googleMap != null)
            googleMap.clear();
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.parseColor("#33ACE0"));
        polyOptions.width(20);
        polyOptions.addAll(mPolyOptions.getPoints());
        googleMap.addPolyline(polyOptions);


        googleMap.addMarker(new MarkerOptions()
                .position(start)
                .title("From"));
        googleMap.addMarker(new MarkerOptions()
                .position(end)
                .title("To"));

    }
}
