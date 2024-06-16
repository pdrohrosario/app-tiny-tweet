package com.app_tiny_tweet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app_tiny_tweet.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

        // For demonstration purposes, these are hardcoded.
        // In a real app, you would retrieve this from a database or authentication provider.
        String username = "User123";
        String password = "Password123";

        usernameEditText.setText(username);
        passwordEditText.setText(password);

        updateButton.setOnClickListener(v -> {
            // Here you would typically update the user details in your database or authentication provider
            String updatedUsername = usernameEditText.getText().toString().trim();
            String updatedPassword = passwordEditText.getText().toString().trim();

            if (updatedUsername.isEmpty() || updatedPassword.isEmpty()) {
                Toast.makeText(getActivity(), "Username and Password cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                // Simulate saving the updated user details
                // For this example, we'll just show a toast message
                Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}