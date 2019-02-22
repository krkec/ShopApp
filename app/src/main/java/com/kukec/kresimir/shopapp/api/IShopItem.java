package com.kukec.kresimir.shopapp.api;

import com.kukec.kresimir.shopapp.model.ShopItem;
import com.kukec.kresimir.shopapp.model.ShopList;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IShopItem {
    String URL = "http://www.service2.xdev.com.hr";

    @GET("/api/GetAllShopItems")
    Call<List<ShopItem>> GetAllShopItems(@Query("ListId") int _id);


    @FormUrlEncoded
    @POST("/api/CreateItem")
    Call<ResponseBody> CreateItem(@Field("ItemName") String ItemName, @Field("ItemQty") String ItemQty, @Field("ListId") int ListId);


    @FormUrlEncoded
    @POST("/api/DeleteItem")
    Call<ResponseBody> DeleteItem(@Field("ShopItemID") int ShopItemID);
}
