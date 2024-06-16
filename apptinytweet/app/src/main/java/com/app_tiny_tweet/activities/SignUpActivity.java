package com.app_tiny_tweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.app_tiny_tweet.R;
import com.app_tiny_tweet.SignInActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText confirmPasswordEditText;
    private EditText passwordEditText;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        listenerButtonRegister();
        redirectToSignUp();
    }

    private void redirectToSignUp() {
        TextView registerLink = findViewById(R.id.sign_in_link);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void listenerButtonRegister() {
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

        });
    }
}