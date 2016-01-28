package com.durimaliu.capstonestage2.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.adapter.ViewPagerAdapter;
import com.durimaliu.capstonestage2.callback.CustomFaceBookCallBack;
import com.durimaliu.capstonestage2.customclass.FaceBookLogIn;
import com.durimaliu.capstonestage2.object.UserToken;
import com.durimaliu.capstonestage2.push.QuickstartPreferences;
import com.durimaliu.capstonestage2.push.RegistrationIntentService;
import com.durimaliu.capstonestage2.request.RequestCallBack;
import com.durimaliu.capstonestage2.utilitys.Utilitys;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import android.provider.Settings.Secure;
import android.widget.Toast;

public class LogIn extends Activity implements CustomFaceBookCallBack {

    ViewPagerAdapter viewPagerAdapter;

    @Bind(R.id.pagerView)
    ViewPager pagerView;

    @Bind(R.id.faceBookButton)
    LoginButton faceBookButton;

    @Bind(R.id.circlePageIndicator)
    CirclePageIndicator circlePageIndicator;

    FaceBookLogIn faceBookLogIn;

    String facebookId, facebookEmail, facebookName, facebookPic;

    RequestCallBack reqCall;
    Retrofit retrofit;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    String android_id;

    public interface TokenReceived {
        void onTokenReceived(String gcmToken);
    }

    public static TokenReceived tokenReceived;
    String gcmTOKEN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.log_in_screen);
        appPreferences.init(this);
        Utilitys.initScreenSize(this);
        ButterKnife.bind(this);
        ifImFromPush();
        if (appPreferences.isLogged()) {
            Intent intent = new Intent(LogIn.this, FragmentHolder.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            }
        };


        retrofit = new Retrofit.Builder()
                .baseUrl(Utilitys.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reqCall = retrofit.create(RequestCallBack.class);


        int paddingButton = (int) (Utilitys.screenHeight * 0.036875f);

        faceBookLogIn = new FaceBookLogIn(faceBookButton, LogIn.this);
        faceBookButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        faceBookButton.setBackgroundResource(R.drawable.rounded_button);
        faceBookButton.setPadding(0, paddingButton, 0, paddingButton);

        initViewPagerAdapter();

        if (appPreferences.isGCMToken()) {
            gcmTOKEN = appPreferences.getGCMToken();
        }
        tokenReceived = new TokenReceived() {
            @Override
            public void onTokenReceived(String gcmToken) {

                gcmTOKEN = gcmToken;
                System.out.println("gcmToken " + gcmTOKEN);
            }
        };


    }


    @OnClick(R.id.faceBookButton)
    public void faceBookButton() {
        faceBookLogIn.LogInWithFaceBook();
    }

    private void initViewPagerAdapter() {
        viewPagerAdapter = new ViewPagerAdapter(getApplicationContext());
        pagerView.setAdapter(viewPagerAdapter);
        circlePageIndicator.setViewPager(pagerView);
        pagerView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                circlePageIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                circlePageIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                circlePageIndicator.onPageScrollStateChanged(state);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        faceBookLogIn.onActivityResultFb(requestCode, resultCode, data);
    }

    @Override
    public void onSucess(JSONObject object, GraphResponse response, String token) {


        System.out.println("Durim token facebook : " + token);
        logInRequest(token);
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onError(FacebookException exception) {
        System.out.println("Durim onError : ");
    }


    private void logInRequest(String token) {


        appPreferences.saveFaceBookToken(token);
        Call<UserToken> authCall = reqCall.userToken(token, android_id, "Android", gcmTOKEN);
        authCall.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Response<UserToken> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {

                        String json_string = new Gson().toJson(response.body(), UserToken.class);

                        appPreferences.saveUserToken(json_string);
                        appPreferences.UserJustLoggedIn(true);

                        Intent intent = new Intent(LogIn.this, FragmentHolder.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        if (gcmTOKEN.length() == 0) {
            if (checkPlayServices()) {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            Toast.makeText(LogIn.this, "Please try again, something wrong happened!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkPlayServices() && !appPreferences.isGCMToken()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000).show();
            } else {
                Log.i("Push ", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    void ifImFromPush() {
        if (appPreferences.getImFromPush()) {
            Intent intent = new Intent(LogIn.this, FragmentHolder.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }
    }
}
