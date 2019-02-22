package com.kukec.kresimir.shopapp.api;

import com.kukec.kresimir.shopapp.model.ShopList;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IShopList {

    ///String URL = "http://www.service2.xdev.com.hr";

    String URL = "http://localhost:54647";

    @GET("/api/GetAllShopLst")
    Call<List<ShopList>> GetAllShopLst(@Query("Uid") int uid);


    @FormUrlEncoded
    @POST("/api/CreateList")
    Call<ResponseBody> CreateList(@Field("ListName") String ListName, @Field("Store") String Store, @Field("Uid") int Uid);

    @FormUrlEncoded
    @POST("/api/CreateList")
    Call<Integer> CreateList2(@Field("ListName") String ListName, @Field("Store") String Store, @Field("Uid") int Uid);


    @FormUrlEncoded
    @POST("/api/DeleteList")
    Call<ResponseBody> DeleteList(@Field("ShopListID") int ShopListID);
}
