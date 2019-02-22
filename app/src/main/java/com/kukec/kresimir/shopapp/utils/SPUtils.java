package com.kukec.kresimir.shopapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kukec.kresimir.shopapp.model.User;

import butterknife.BindView;

public class SPUtils {
    public static void writeToSP(User user, Context context) {
        SharedPreferences pref = context.getSharedPreferences("UserSL", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("UserName", user.getUname());
        edit.putString("PassWord", user.getUPass());
        edit.clear();
        edit.putInt("Id", user.getId());
        edit.commit();
    }
    public static void removeSP(Context context) {
        SharedPreferences pref = context.getSharedPreferences("UserSL", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
    }
    public static int getUserId(Context context){
        SharedPreferences pref = context.getSharedPreferences("UserSL", Context.MODE_PRIVATE);
        return pref.getInt("Id",0);
    }
}
