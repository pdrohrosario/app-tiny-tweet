package com.app_tiny_tweet.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app_tiny_tweet.R;
import com.app_tiny_tweet.adapter.PostPagerAdapter;
import com.app_tiny_tweet.fragments.AllPostsFragment;
import com.app_tiny_tweet.fragments.MyPostsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton createPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("All Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("My posts"));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));

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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = (Fragment) adapter.instantiateItem(viewPager, position);
                if (fragment instanceof AllPostsFragment) {
                    ((AllPostsFragment) fragment).loadPostsFromAPI();
                }
                else if (fragment instanceof MyPostsFragment) {
                    ((MyPostsFragment) fragment).loadPostsByUserIdFromAPI();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        createPost = findViewById(R.id.fabCreatePost);
        createPost.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CreatePostActivity.class);
            startActivity(intent);
        });
    }
}
