package com.app_tiny_tweet.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app_tiny_tweet.R;
import com.app_tiny_tweet.SignInActivity;
import com.app_tiny_tweet.model.Post;
import com.app_tiny_tweet.security.UserManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileFragment extends Fragment {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private MaterialButton updateButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_profile, container, false);

        usernameEditText = view.findViewById(R.id.username_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        updateButton = view.findViewById(R.id.update_button);

        updateButton.setOnClickListener(v -> {
            // Here you would typically update the user details in your database or authentication provider
            String updatedUsername = usernameEditText.getText().toString().trim();
            String updatedPassword = passwordEditText.getText().toString().trim();

            if (updatedUsername.isEmpty() || updatedPassword.isEmpty()) {
                Toast.makeText(getActivity(), "Username and Password cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                UserManager.getInstance().getUser().setUsername(updatedUsername);
                UserManager.getInstance().getUser().setPassword(updatedPassword);
               updateUser(UserManager.getInstance());
            }
        });

        return view;
    }

    public void updateUser(UserManager user) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.68.110:8080/user/save";
        String token = UserManager.getInstance().getToken();

        Request request = buildRequest(url, token, user);

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

    private Request buildRequest(String url, String token, UserManager user) {
        String json = "{"
                + "\"id\":\"" + user.getUser().getId() + "\","
                + "\"username\":\"" + user.getUser().getUsername() + "\","
                + "\"password\":\"" + user.getUser().getPassword() + "\""
                + "}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
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
            Toast.makeText(getContext(), "Credenciais Atualizadas", Toast.LENGTH_SHORT).show();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

}