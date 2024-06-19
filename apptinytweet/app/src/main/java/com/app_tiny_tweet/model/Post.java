package com.app_tiny_tweet.model;

public class Post {
    private Long id;
    private String content;
    private String title;
    private Long userId;

    public Post(Long id, String content, String title, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Post(String content, String title, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
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
}