package com.kukec.kresimir.shopapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kukec.kresimir.shopapp.api.IShopList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;

public class ShopList implements IShopList {

    @SerializedName("list")
    @Expose
    private List<ShopItem> list = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("listName")
    @Expose
    private String listName;
    @SerializedName("store")
    @Expose
    private String store;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    @SerializedName("numberOfItems")
    @Expose
    private Integer numberOfItems;

    public List<ShopItem> getList() {
        return list;
    }

    public void setList(List<ShopItem> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Call<List<ShopList>> GetAllShopLst(int Uid) {
        return null;
    }

    @Override
    public Call<ResponseBody> CreateList(String ListName, String Store, int Uid) {
        return null;
    }

    @Override
    public Call<Integer> CreateList2(String ListName, String Store, int Uid) {
        return null;
    }

    @Override
    public Call<ResponseBody> DeleteList(int ShopListID) {
        return null;
    }


    public ShopList(List<ShopItem> list, Integer id, String listName, String store, Integer userId, Integer numberOfItems) {
        this.list = list;
        this.id = id;
        this.listName = listName;
        this.store = store;
        this.userId = userId;
        this.numberOfItems = numberOfItems;
    }

    @Override
    public String toString() {
        String s = id.toString()+"\r\n"+listName+"\r\n"+store+"\r\n"+userId.toString()+"\r\n"+numberOfItems.toString();
        return s;
    }
}