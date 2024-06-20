package com.app_tiny_tweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.app_tiny_tweet.R;
import com.app_tiny_tweet.SignInActivity;
import com.app_tiny_tweet.model.User;
import com.app_tiny_tweet.service.UserService;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText confirmPasswordEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private TextView loginLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.username_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        listenerButtonRegister();
        listenerLoginLink();
    }

    private void listenerLoginLink() {
        loginLink = findViewById(R.id.sign_in_link);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               redirectToSignIn();
            }
        });
    }

    private void listenerButtonRegister() {
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if(validateFields(username, password, confirmPassword)) {
                UserService.getInstance().save(new User(username, password));
                redirectToSignIn();

            }
        });

    }

    private boolean validateFields(String username, String password, String confirmPassword) {
        if(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(SignUpActivity.this, "Fill out the fields!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(SignUpActivity.this, "The passwords don't match!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void redirectToSignIn(){
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}