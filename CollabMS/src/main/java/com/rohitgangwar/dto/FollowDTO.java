package com.rohitgangwar.dto;

import java.time.LocalDateTime;

public class FollowDTO {
    private Long followId;
    private Long followeeId;
    private Long followerId;
    private String status;
    private LocalDateTime updatedAt;


    public Long getFollowId() {
        return followId;
    }
    public void setFollowId(Long followId) {
        this.followId = followId;
    }
    public Long getFolloweeId() {
        return followeeId;
    }
    public void setFolloweeId(Long followeeId) {
        this.followeeId = followeeId;
    }
    public Long getFollowerId() {
        return followerId;
    }
    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    // Getters and setters
}

