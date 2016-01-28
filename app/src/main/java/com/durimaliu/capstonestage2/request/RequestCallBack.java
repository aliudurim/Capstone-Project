package com.durimaliu.capstonestage2.request;

import com.durimaliu.capstonestage2.object.AllTypes;
import com.durimaliu.capstonestage2.object.FbFriend;
import com.durimaliu.capstonestage2.object.GetAllTrip;
import com.durimaliu.capstonestage2.object.GetTripById;
import com.durimaliu.capstonestage2.object.ResponseTrip;
import com.durimaliu.capstonestage2.object.User;
import com.durimaliu.capstonestage2.object.UserToken;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by macbook on 1/19/16.
 */
public interface RequestCallBack {

    @FormUrlEncoded
    @POST("api/v1/login")
    Call<UserToken> userToken(@Field("fb_access_token") String fb_access_token,
                              @Field("device_id") String device_id,
                              @Field("device_os") String device_os,
                              @Field("push_token") String push_token);

    @GET("api/v1/trips?")
    Call<List<GetAllTrip>> getAllTrip(@Query("token") String access_token);

    @GET("api/v1/account?")
    Call<User> getUser(@Query("token") String access_token);


    @GET("api/v1/account/createdtrips?")
    Call<List<GetAllTrip>> getOwnTrip(@Query("token") String access_token);

    @GET("api/v1/account/previoustrips?")
    Call<List<GetAllTrip>> getPreviousTrips(@Query("token") String access_token);

    @GET("api/v1/account/upcommingtrips?")
    Call<List<GetAllTrip>> getUpcommingTrips(@Query("token") String access_token);

    @GET("api/v1/trips?")
    Call<List<GetAllTrip>> getTripsByType(@Query("type") String type, @Query("token") String access_token);


    @GET("api/v1/trips/types?")
    Call<List<AllTypes>> getAllType(@Query("token") String access_token);

    @FormUrlEncoded
    @POST("api/v1/trips/create")
    Call<ResponseTrip> createTrip(@Query("token") String access_token,
                                  @Field("name") String name,
                                  @Field("type") String type,
                                  @Field("description") String description,
                                  @Field("start_date") String start_date,
                                  @Field("location_lang") String location_lang,
                                  @Field("location_lat") String location_lat,
                                  @Field("pickup_lang") String pickup_lang,
                                  @Field("pickup_lat") String pickup_lat,
                                  @Field("invited_friends") String invited_friends);


    @GET("api/v1/trips/{id}")
    Call<GetTripById> getTripById(@Path("id") String id, @Query("token") String access_token);


    @GET("api/v1/trips/{id}/join")
    Call<ResponseTrip> joinedTrip(@Path("id") String id, @Query("token") String access_token);

    @GET("api/v1/trips/{id}/unjoin")
    Call<ResponseTrip> unJoinedTrip(@Path("id") String id, @Query("token") String access_token);

    @GET("api/v1/account/facebook/friendlist")
    Call<List<FbFriend>> getFaceBookFriends(@Query("token") String access_token, @Query("fb_token") String fb_token);
}
