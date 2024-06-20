package com.app_tiny_tweet.model;

public class Post {
    private Long id;
    private String content;
    private String title;
    private Long userId;
    private String username;

    public Post(Long id, String content, String title, Long userId, String username) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.userId = userId;
        this.username = username;
    }

    public Post(String content, String title, Long userId) {
        this.content = content;
        this.title = title;
        this.userId = userId;
    }

    public Post(String content, String title, Long userId, String username) {
        this.content = content;
        this.title = title;
        this.userId = userId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}