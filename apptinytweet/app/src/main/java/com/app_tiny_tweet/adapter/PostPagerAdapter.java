package com.app_tiny_tweet.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app_tiny_tweet.fragments.AllPostsFragment;
import com.app_tiny_tweet.fragments.MyPostsFragment;
import com.app_tiny_tweet.fragments.ProfileFragment;

public class PostPagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PostPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllPostsFragment();
            case 1:
                return new MyPostsFragment();
            case 2:
                return new ProfileFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
