package com.app_tiny_tweet.service;

import com.app_tiny_tweet.model.Post;
import com.app_tiny_tweet.model.User;
import com.app_tiny_tweet.security.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;


import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class UserService {


    private static UserService instance;

    private UserService() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean login(UserManager user) {
        final boolean[] result = {false};

        Thread saveThread = new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                String json = buildJson(user.getUser());

                RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url("http://192.168.68.110:8080/auth/login")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(response.body().string(), Map.class);
                user.setToken(map.get("token").toString());
                user.getUser().setUsername(map.get("username").toString());
                Double value = (Double) map.get("id");
                user.getUser().setId(value.longValue());
                response.close();

                result[0] = true;
            } catch (IOException e) {
                e.printStackTrace();
                result[0] = false;
            }
        });

        saveThread.start();

        try {
            saveThread.join();
        } catch (InterruptedException e) {
            System.out.println("A thread foi interrompida.");
            Thread.currentThread().interrupt();
            return false;
        }

        return result[0];
    }

    public void save(User user) {

        Thread t = new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();

                String json = buildJson(user);

                RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url("http://192.168.68.110:8080/user/save")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                response.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("A thread foi interrompida.");
            Thread.currentThread().interrupt();
        }
    }

    public String buildJson(User user) {
        if (user.getId() != null) {
            return "{"
                    + "\"id\":\"" + user.getId() + "\","
                    + "\"username\":\"" + user.getUsername() + "\","
                    + "\"password\":\"" + user.getPassword() + "\""
                    + "}";
        }
        else{
            return "{"
                    + "\"username\":\"" + user.getUsername() + "\","
                    + "\"password\":\"" + user.getPassword() + "\""
                    + "}";
        }
    }
}
