package com.durimaliu.capstonestage2.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.object.ResponseTrip;
import com.durimaliu.capstonestage2.request.RequestCallBack;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by macbook on 1/5/16.
 */
public class CreateTrip extends Activity {

    @Bind(R.id.edNameTrip)
    EditText edNameTrip;
    @Bind(R.id.edTypeOfTrip)
    EditText edTypeOfTrip;
    @Bind(R.id.txtDate)
    TextView txtDate;

    @Bind(R.id.txtLocation)
    TextView txtLocation;
    @Bind(R.id.txtPickUp)
    TextView txtPickUp;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    RequestCallBack reqCall;
    Retrofit retrofit;

    double lat_location, longi_location;
    double lat_pickup, longi_pickup;
    String invited_friends = "";


    Tracker t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_trip_screen);
        ButterKnife.bind(this);

        // Get tracker.
        t = ((Ntrip) getApplication()).getTracker(
                Ntrip.TrackerName.APP_TRACKER);

        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

        mAdView.loadAd(adRequest);
    }

    @OnClick(R.id.rlLocation)
    public void openMapLocation() {
        Intent i = new Intent(this, GoogleMapActivity.class);
        startActivityForResult(i, 1);
    }

    @OnClick(R.id.rlPickUp)
    public void openMapPickUp() {
        Intent i = new Intent(this, GoogleMapActivity.class);
        startActivityForResult(i, 2);
    }

    @OnClick(R.id.btnInviteFriends)
    public void openInviteFrends() {
        Intent i = new Intent(this, InviteFriends.class);
        startActivityForResult(i, 3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String city = data.getStringExtra("city");
                lat_location = data.getDoubleExtra("lat", 0.0);
                longi_location = data.getDoubleExtra("long", 0.0);

                String s_lat = new DecimalFormat("#.##").format(lat_location);
                String s_longi = new DecimalFormat("#.##").format(longi_location);

                txtLocation.setText(city + "   latLng: " + s_lat + " / " + s_longi);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String city = data.getStringExtra("city");
                lat_pickup = data.getDoubleExtra("lat", 0.0);
                longi_pickup = data.getDoubleExtra("long", 0.0);

                String s_lat = new DecimalFormat("#.##").format(lat_pickup);
                String s_longi = new DecimalFormat("#.##").format(longi_pickup);

                txtPickUp.setText(city + "   latLng: " + s_lat + " / " + s_longi);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {

                invited_friends = data.getStringExtra("invited_friends");

            }
        }
    }


    @OnClick(R.id.rlDate)
    public void pickDate() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                DatePickerDialog.THEME_HOLO_LIGHT, datePickerListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        datePickerDialog.setTitle("Select the date");
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            txtDate.setText(year1 + "-" + month1 + "-" + day1);
        }
    };

    @OnClick(R.id.btnCreateTrip)
    public void createTrip() {

        if (Utilitys.isBlank(edNameTrip.getText().toString())
                || Utilitys.isBlank(edTypeOfTrip.getText().toString())
                || Utilitys.isBlank(txtDate.getText().toString())
                || Utilitys.isBlank(txtLocation.getText().toString())
                || Utilitys.isBlank(txtPickUp.getText().toString())) {
            Toast.makeText(CreateTrip.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            createTripRequest();
        }


    }

    private void createTripRequest() {

        String nameTrip = edNameTrip.getText().toString();
        String typeTrip = edTypeOfTrip.getText().toString();
        String dateTrip = txtDate.getText().toString();


        Call<ResponseTrip> createTrip = reqCall.createTrip(appPreferences.getUserToken().getToken(),
                nameTrip,
                typeTrip,
                "",
                dateTrip,
                String.valueOf(lat_location),
                String.valueOf(longi_location),
                String.valueOf(longi_pickup),
                String.valueOf(lat_pickup),
                invited_friends);

        progressBar.setVisibility(View.VISIBLE);
//        System.out.println("Durim list2 : " + invited_friends);
        createTrip.enqueue(new Callback<ResponseTrip>() {
            @Override
            public void onResponse(Response<ResponseTrip> response) {

                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        Toast.makeText(CreateTrip.this, "Your Trip Is Created", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @OnClick(R.id.btnBackTrip)
    public void finishClass() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        t.setScreenName("CreateTrip");
        t.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
