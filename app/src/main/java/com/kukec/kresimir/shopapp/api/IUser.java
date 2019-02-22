package com.kukec.kresimir.shopapp.api;

import com.kukec.kresimir.shopapp.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUser {
    String URL = "http://www.service2.xdev.com.hr";

    @GET("/api/GetUid")
    Call<User> GetUid(@Query("uname") String Uname, @Query("pass") String Pass);
    @GET("/api/GetAllUsers")
    Call<List<String>> GetAllUsers();
    @POST("/api/ShareList")
    @FormUrlEncoded
    Call<ResponseBody> ShareListByUsername(@Field("ShopListID") int ListId,@Field("UserName") String UserName);
}
