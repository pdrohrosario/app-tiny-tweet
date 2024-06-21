package com.app_tiny_tweet.service;

import com.app_tiny_tweet.model.Post;
import com.app_tiny_tweet.security.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostService {

    private static PostService instance;

    private PostService() {
    }

    public static synchronized PostService getInstance() {
        if (instance == null) {
            instance = new PostService();
        }
        return instance;
    }

    public void createPost(Post post, UserManager user) {
        Thread t = new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                String json = "{\n" +
                        "    \"content\": \"" + post.getContent() + "\",\n" +
                        "    \"title\": \"" + post.getTitle() + "\",\n" +
                        "    \"userId\": " + user.getUser().getId() + "\n" +
                        "}";

                RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                Request request = new Request.Builder()
                        .url("http://192.168.68.110:8080/post/save")
                        .post(body)
                        .addHeader("Authorization", "Bearer " + user.getToken())
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(response.body().string(), Map.class);
                System.out.println(map);
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

    public List<Post> getAllPosts() {
        List<Post> postList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.68.110:8080/post/list")
                .get()
                .addHeader("Authorization", "Bearer " + UserManager.getInstance().getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Map<String, Object> map = gson.fromJson(response.body().string(), Map.class);
                    String contentJson = gson.toJson(map.get("content"));

                    Type listType = new TypeToken<List<Post>>() {}.getType();
                    List<Post> posts = gson.fromJson(contentJson, listType); // Clear old data

                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });

        return postList;
    }
}
