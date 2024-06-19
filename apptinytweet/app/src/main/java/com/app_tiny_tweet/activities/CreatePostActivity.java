package com.app_tiny_tweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app_tiny_tweet.R;
import com.app_tiny_tweet.model.Post;
import com.app_tiny_tweet.security.UserManager;
import com.app_tiny_tweet.service.PostService;
import com.app_tiny_tweet.service.UserService;

import javax.security.auth.callback.Callback;

public class CreatePostActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Button btnSubmitPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        contentEditText = findViewById(R.id.etPostContent);
        titleEditText = findViewById(R.id.etPostTitle);
        btnSubmitPost = findViewById(R.id.btnSubmitPost);

        btnSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postContent = contentEditText.getText().toString().trim();
                String postTitle = titleEditText.getText().toString().trim();

                if (TextUtils.isEmpty(postContent) || TextUtils.isEmpty(postTitle)) {
                    Toast.makeText(CreatePostActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {
                    UserManager user = UserManager.getInstance();
                    PostService.getInstance().createPost(new Post(postTitle, postContent, user.getUser().getId()), UserManager.getInstance());

                    Intent intent = new Intent(CreatePostActivity.this, HomeActivity.class);
                    intent.putExtra("tab_index", 0); // Pass the tab index to open "Todos os posts"
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}