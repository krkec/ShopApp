package com.kukec.kresimir.shopapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kukec.kresimir.shopapp.api.IUser;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class User implements IUser {
    @SerializedName("uname")
    @Expose
    private String Uname;
    @SerializedName("pass")
    @Expose
    private String UPass;
    @SerializedName("id")
    @Expose
    private int Id;

    public User(String uname, String UPass, int id) {
        Uname = uname;
        this.UPass = UPass;
        this.Id = id;
    }
    public User(){

    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUPass() {
        return UPass;
    }

    public void setUPass(String UPass) {
        this.UPass = UPass;
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int id) {
        this.Id = id;
    }


    @Override
    public Call<User> GetUid(String Uname, String Pass) {
        return null;
    }

    @Override
    public Call<List<String>> GetAllUsers() {
        return null;
    }

    @Override
    public Call<ResponseBody> ShareListByUsername(int ListId, String UserName) {
        return null;
    }


    @Override
    public String toString() {
        String s = getUname()+"-"+getUPass()+"-"+getId();
        return s;
    }
}
