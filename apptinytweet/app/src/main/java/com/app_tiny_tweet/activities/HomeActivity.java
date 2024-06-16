package com.app_tiny_tweet.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.app_tiny_tweet.R;
import com.app_tiny_tweet.adapter.PostPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton fabCreatePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Todos os posts"));
        tabLayout.addTab(tabLayout.newTab().setText("Meus posts"));
        tabLayout.addTab(tabLayout.newTab().setText("Perfil"));

        final PostPagerAdapter adapter = new PostPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        fabCreatePost = findViewById(R.id.fabCreatePost);
        fabCreatePost.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CreatePostActivity.class);
            startActivity(intent);
        });
    }
}