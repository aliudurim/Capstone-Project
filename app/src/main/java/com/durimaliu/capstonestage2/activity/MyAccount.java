package com.durimaliu.capstonestage2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.durimaliu.capstonestage2.R;
import com.durimaliu.capstonestage2.customclass.CircleTransform;
import com.durimaliu.capstonestage2.utilitys.appPreferences;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 1/27/16.
 */
public class MyAccount extends Activity {

    @Bind(R.id.imgProfilePic)
    ImageView imgProfilePic;

    @Bind(R.id.txtName)
    TextView txtName;

    @Bind(R.id.txtEmail)
    TextView txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
        ButterKnife.bind(this);

        Picasso.with(this).load(appPreferences.getUser().getProfilePic()).transform(new CircleTransform()).into(imgProfilePic);
        txtName.setText(appPreferences.getUser().getName());
        txtEmail.setText(appPreferences.getUser().getEmail());

    }

    @OnClick(R.id.btnBack)
    public void finishClass() {
        onBackPressed();
    }
}
