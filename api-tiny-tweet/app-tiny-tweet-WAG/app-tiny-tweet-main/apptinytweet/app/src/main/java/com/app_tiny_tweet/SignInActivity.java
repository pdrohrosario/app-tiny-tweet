package com.app_tiny_tweet;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app_tiny_tweet.activities.HomeActivity;
import com.app_tiny_tweet.activities.SignUpActivity;
import com.app_tiny_tweet.model.User;
import com.app_tiny_tweet.security.UserManager;
import com.app_tiny_tweet.service.UserService;

public class SignInActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        listenerButtonLogin();
        listenerSignUp();
    }
    private void listenerButtonLogin() {
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            validateFields(username, password);

            UserManager userManager = UserManager.getInstance();
            userManager.setUser(new User(username, password));
            if (UserService.getInstance().login(userManager)) {
                redirectToHome();
            } else {
                Toast.makeText(SignInActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listenerSignUp() {
        TextView registerLink = findViewById(R.id.register_link);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToSignUp();
            }
        });
    }

    private boolean validateFields(String username, String password) {
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(SignInActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void redirectToSignUp(){
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void redirectToHome(){
        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}