package com.example.demo.Member;
import java.sql.Timestamp;

public class ChangeHistory {

    private int id;
    private int userId;
    private String actionType;  // For example: "Add", "Update", "Delete"
    private String objectType;  // For example: "Show", "Setlist", "Artist"
    private int objectId;       // The ID of the object (e.g., Show ID, Setlist ID)
    private String description; // Description of the change (e.g., "Added new Show")
    private Timestamp createdAt; // Timestamp when the change was made

    // Constructor
    public ChangeHistory(int id, int userId, String actionType, String objectType, int objectId, String description, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.actionType = actionType;
        this.objectType = objectType;
        this.objectId = objectId;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Optional: Override toString method for easier debugging
    @Override
    public String toString() {
        return "ChangeHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", actionType='" + actionType + '\'' +
                ", objectType='" + objectType + '\'' +
                ", objectId=" + objectId +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
