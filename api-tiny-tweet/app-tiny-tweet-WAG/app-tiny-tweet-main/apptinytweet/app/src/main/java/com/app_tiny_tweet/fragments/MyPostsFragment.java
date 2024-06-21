package com.app_tiny_tweet.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app_tiny_tweet.R;
import com.app_tiny_tweet.model.Post;
import com.app_tiny_tweet.security.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyPostsFragment extends Fragment {

    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_my_posts, container, false);

        linearLayout = view.findViewById(R.id.mainLayout);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        getUserPosts();

        return view;
    }

    public void getUserPosts() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.68.110:8080/post/user/"+UserManager.getInstance().getUser().getId();
        String token = UserManager.getInstance().getToken();

        Request request = buildRequest(url, token);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handleRequestFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(response);
            }
        });
    }

    private Request buildRequest(String url, String token) {
        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    private void handleRequestFailure(IOException e) {
        e.printStackTrace();
    }

    private void handleResponse(Response response) throws IOException {
        if (response.isSuccessful()) {
            processSuccessfulResponse(response);
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private void processSuccessfulResponse(Response response) throws IOException {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(response.body().string(), Map.class);
        String contentJson = gson.toJson(map.get("content"));

        Type listType = new TypeToken<List<Post>>() {}.getType();
        List<Post> postList = gson.fromJson(contentJson, listType);

        updatePostsView(postList);
    }

    private void updatePostsView(List<Post> postList) {
        getActivity().runOnUiThread(() -> {
            linearLayout.removeAllViews();

            for (Post post : postList) {
                TextView textView = createTextForPost(post);
                linearLayout.addView(textView);
            }

            postAdapter.setPostList(postList);
            postAdapter.notifyDataSetChanged();
        });
    }

    private TextView createTextForPost(Post post) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(50, 50, 50, 50);

        textView.setLayoutParams(params);
        SpannableStringBuilder spannable = new SpannableStringBuilder();
        spannable.append("\n");
        int start = spannable.length();
        spannable.append(post.getTitle() + ":");
        int end = spannable.length();

        spannable.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append("\n" + post.getContent());

        textView.setText(spannable);
        textView.setPadding(50, 0, 50, 50);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.rounded_background);

        return textView;
    }
}
