package com.app_tiny_tweet.security;

public class UserManager {
    private static UserManager instance;
    private String token;

    private UserManager() { }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}