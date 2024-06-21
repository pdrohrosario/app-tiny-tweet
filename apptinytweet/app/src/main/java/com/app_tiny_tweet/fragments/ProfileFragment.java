package com.app_tiny_tweet.fragments;

import android.content.Intent;
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
import com.app_tiny_tweet.activities.SignUpActivity;
import com.app_tiny_tweet.model.Post;
import com.app_tiny_tweet.security.UserManager;
import com.app_tiny_tweet.service.UserService;
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
    private MaterialButton logoutButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_profile, container, false);

        usernameEditText = view.findViewById(R.id.username_edit_text);
        usernameEditText.setText(UserManager.getInstance().getUser().getUsername());
        passwordEditText = view.findViewById(R.id.password_edit_text);

        updateButton = view.findViewById(R.id.update_button);
        logoutButton = view.findViewById(R.id.logout_button);

        updateButton.setOnClickListener(v -> {
            String updatedUsername = usernameEditText.getText().toString().trim();
            String updatedPassword = passwordEditText.getText().toString().trim();

            if (updatedUsername.isEmpty() || updatedPassword.isEmpty()) {
                Toast.makeText(getActivity(), "Username and Password must not be empty", Toast.LENGTH_SHORT).show();
            } else if (UserManager.getInstance().getUser().getUsername().equals(updatedUsername)) {
                Toast.makeText(getActivity(), "The new username must be different from the current", Toast.LENGTH_SHORT).show();
            } else {
               UserManager.getInstance().getUser().setUsername(updatedUsername);
               UserManager.getInstance().getUser().setPassword(updatedPassword);
               try {
                   UserService.getInstance().save(UserManager.getInstance().getUser());
                   Toast.makeText(getActivity(), "Updated credentials", Toast.LENGTH_SHORT).show();
               }catch (Exception e) {
                   Toast.makeText(getActivity(), "Username already exists", Toast.LENGTH_SHORT).show();
               }
            }
        });

        logoutButton.setOnClickListener(v -> {
            UserManager.getInstance().logout();
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            startActivity(intent);
        });

        return view;
    }

}