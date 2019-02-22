package com.kukec.kresimir.shopapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemQty")
    @Expose
    private String itemQty;
    @SerializedName("listId")
    @Expose
    private Integer listId;
    @SerializedName("buyed")
    @Expose
    private Integer buyed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Integer getBuyed() {
        return buyed;
    }

    public void setBuyed(Integer buyed) {
        this.buyed = buyed;
    }

    public ShopItem(Integer id, String itemName, String itemQty, Integer listId, Integer buyed) {
        this.id = id;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.listId = listId;
        this.buyed = buyed;
    }
}