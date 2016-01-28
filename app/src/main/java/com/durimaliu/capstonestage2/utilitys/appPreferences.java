package com.durimaliu.capstonestage2.utilitys;

import android.content.Context;
import android.content.SharedPreferences;

import com.durimaliu.capstonestage2.object.User;
import com.durimaliu.capstonestage2.object.UserToken;
import com.google.gson.Gson;

/**
 * Created by macbook on 1/19/16.
 */
public class appPreferences {

    private static UserToken userToken;
    private static User user;

    public static SharedPreferences prefs;
    public static String USER_TOKEN = "token";
    public static String USER_DATA = "user_data";
    public static String USER_IS_LOGGED_IN = "user_is_logged_in";

    public static String USER_TOKEN_FACEBOK = "token_facebook";

    public static String PUSHNOTIFICATION = "push_notification";

    public static void init(Context ctx) {
        prefs = ctx.getSharedPreferences(ctx.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void saveString(String key, String value) {
        prefs.edit().putString(key, value).commit();
    }

    public static String getFromPrefs(String key) {
        return prefs.getString(key, "");
    }

    public static void deletePrefs() {
        prefs.edit().clear().commit();
    }

    public static void saveUserResponse(String user_data) {
        prefs.edit().putString(USER_DATA, user_data).commit();
    }

    public static String getUserResponse() {
        return prefs.getString(USER_DATA, "");
    }

    public static User getUser() {
        if (getUserResponse() != null) {
            user = new Gson().fromJson(getUserResponse(), User.class);
        }
        return user;
    }

    public static void saveBoolForPush(boolean push_notification) {
        prefs.edit().putBoolean(PUSHNOTIFICATION, push_notification).commit();
    }


    public static boolean getBoolForPush() {
        return prefs.getBoolean(PUSHNOTIFICATION, true);
    }

    public static void saveFaceBookToken(String fb_token) {
        prefs.edit().putString(USER_TOKEN_FACEBOK, fb_token).commit();
    }

    public static String getFaceBookTokenResponse() {
        return prefs.getString(USER_TOKEN_FACEBOK, "");
    }

    public static void saveUserToken(String token) {
        prefs.edit().putString(USER_TOKEN, token).commit();
    }

    public static String getUserTokenResponse() {
        return prefs.getString(USER_TOKEN, "");
    }


    public static UserToken getUserToken() {
        if (getUserTokenResponse() != null) {
            userToken = new Gson().fromJson(getUserTokenResponse(), UserToken.class);
        }
        return userToken;
    }


    public static void UserJustLoggedIn(boolean status) {
        prefs.edit().putBoolean(USER_IS_LOGGED_IN, status).commit();
    }

    public static boolean isLogged() {
        if (prefs.getBoolean(USER_IS_LOGGED_IN, false)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * GCM
     */
    public static String GCM_TOKEN = "gcm_token";
    public static String GCM_TOKENBOOLEAN = "gcm_token_boolean";
    public static String GCMTOKEN_SENT_TO_SERVER = "gcm_token_sent_to_server";

    public static void saveGCMTokenSentToServer(boolean bool) {
        prefs.edit().putBoolean(GCMTOKEN_SENT_TO_SERVER, bool).commit();
    }

    public static void saveGCMTokenBoolean(boolean gcmtk) {
        prefs.edit().putBoolean(GCM_TOKENBOOLEAN, gcmtk).commit();
    }

    public static boolean isGCMToken() {
        if (prefs.getBoolean(GCM_TOKENBOOLEAN, false) && prefs.getBoolean(GCMTOKEN_SENT_TO_SERVER, false)) {
            return true;
        } else {
            return false;
        }
    }

    public static void saveGCMToken(String regId) {
        prefs.edit().putString(GCM_TOKEN, regId).commit();
    }

    public static String getGCMToken() {
        return getFromPrefs(GCM_TOKEN);
    }

    /**
     * Push Notifications
     */
    public static String IM_FROM_PUSH = "imfrom_push";
    public static String PUSH_MESSAGE = "push_message";

    public static void savePushMessage(String pushMessage) {
        prefs.edit().putString(PUSH_MESSAGE, pushMessage).commit();
    }

    public static String getPushMessage() {
        return getFromPrefs(PUSH_MESSAGE);
    }

    public static void saveImFromPush(boolean push_notification) {
        prefs.edit().putBoolean(IM_FROM_PUSH, push_notification).commit();
    }

    public static boolean getImFromPush() {
        return prefs.getBoolean(IM_FROM_PUSH, false);
    }
    /********************************************/

}
