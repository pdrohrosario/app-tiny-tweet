package com.app_tiny_tweet.model;

public class Post {
    private String id;
    private String content;
    private String creationDate;
    private String userId;

    public Post(String id, String content, String creationDate, String userId) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}