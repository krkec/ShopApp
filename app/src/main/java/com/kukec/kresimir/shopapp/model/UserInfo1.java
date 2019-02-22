package com.kukec.kresimir.shopapp.model;

public class UserInfo1 {
    private static UserInfo1 instance;
    private User ActiveUser;
    private UserInfo1(){}

    public static synchronized UserInfo1 getInstance() {
        if(null == instance){
            instance = new UserInfo1();
        }
        return instance;
    }

    public User getActiveUser() {
        return ActiveUser;
    }

    public void setActiveUser(User activeUser) {
        ActiveUser = activeUser;
    }
}
