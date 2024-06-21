package com.app_tiny_tweet.security;

import com.app_tiny_tweet.model.User;

public class UserManager {
    private static UserManager instance;
    private User user;

    private String token;

    private UserManager() { }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void logout(){
        user = null;
        token = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}