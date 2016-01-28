package com.durimaliu.capstonestage2.callback;

import com.facebook.FacebookException;
import com.facebook.GraphResponse;

import org.json.JSONObject;

/**
 * Created by DurimAliu on 30/12/15.
 */
public interface CustomFaceBookCallBack {

    void onSucess(JSONObject object, GraphResponse response, String token);

    void onCancel();

    void onError(FacebookException exception);
}
