package com.app_tiny_tweet.service;

import android.widget.Toast;

import com.app_tiny_tweet.activities.SignUpActivity;
import com.app_tiny_tweet.model.Post;
import com.app_tiny_tweet.model.User;
import com.app_tiny_tweet.security.GlobalVariables;
import com.app_tiny_tweet.security.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;


import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

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

    public boolean login(UserManager user)  {
        final boolean[] result = {false};

        Thread saveThread = new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                String json = buildJson(user.getUser());

                RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url(GlobalVariables.getAPI_URL()+"/auth/login")
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

    public void save(User user) throws RuntimeException {
        OkHttpClient client = new OkHttpClient();

        String json = buildJson(user);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(GlobalVariables.getAPI_URL()+"/user/save")
                .post(body)
                .build();

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Exception> exceptionReference = new AtomicReference<>();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                exceptionReference.set(e);
                latch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    exceptionReference.set(new RuntimeException("Error on save user: " + response));
                }
                response.close();
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Exception exception = exceptionReference.get();
        if (exception != null) {
            throw new RuntimeException(exception);
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
