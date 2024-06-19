package com.app_tiny_tweet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_tiny_tweet.R;
import com.app_tiny_tweet.model.Post;
import com.app_tiny_tweet.security.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllPostsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_all_posts, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        // Call API to fetch posts
        getAllPosts();

        return view;
    }

    private void getAllPosts() {
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
                    Type listType = new TypeToken<List<Post>>(){}.getType();
                    postList.clear(); // Clear old data
                    postList.addAll(gson.fromJson(response.body().string(), listType));

                    // Update UI on the main thread
                    getActivity().runOnUiThread(() -> {
                        postAdapter.notifyDataSetChanged(); // Notify adapter of data change
                    });
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }
}