package com.durimaliu.capstonestage2.customclass;

import android.content.Intent;
import android.os.Bundle;

import com.durimaliu.capstonestage2.callback.CustomFaceBookCallBack;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by DurimAliu on 30/12/15.
 */
public class FaceBookLogIn {

    LoginButton loginButton;
    //    Fragment fragment;
    CallbackManager callbackManager;
    CustomFaceBookCallBack customFaceBookCallBack;

    public FaceBookLogIn(LoginButton loginButton, CustomFaceBookCallBack customFaceBookCallBack) {

//        this.fragment = fragment;
        this.loginButton = loginButton;
        this.customFaceBookCallBack = customFaceBookCallBack;
        callbackManager = CallbackManager.Factory.create();

//        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends,publish_actions"));
//        loginButton.setFragment(fragment);

    }


    public void LogInWithFaceBook() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                final String facebookAccessToken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        System.out.println("Durim aliu response: " + response.toString() + " : " + object.optString("id"));
                        // Application code
                        if (object != null) {
                            customFaceBookCallBack.onSucess(object, response, facebookAccessToken);
                        }

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                customFaceBookCallBack.onCancel();
            }

            @Override
            public void onError(FacebookException error) {
                customFaceBookCallBack.onError(error);
            }
        });

    }

    public void onActivityResultFb(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
