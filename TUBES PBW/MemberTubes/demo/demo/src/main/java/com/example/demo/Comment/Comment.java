package com.example.demo.Comment;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private int showId;
    private String commentText;
    private Timestamp createdAt;

    // Constructor
    public Comment(int id, int showId, String commentText, Timestamp createdAt) {
        this.id = id;
        this.showId = showId;
        this.commentText = commentText;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}