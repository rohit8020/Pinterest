package com.rohitgangwar.dto;

import java.time.LocalDateTime;

public class BoardInvitationDTO {
    private Long id;
    private Long boardId;
    private Long invitee;
    private Long inviter;
    private String status;
    private String description;
    private LocalDateTime updatedAt;

    public Long getBoardId() {
        return boardId;
    }
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getInviteId() {
        return id;
    }
    public void setInviteId(Long id) {
        this.id = id;
    }
    public Long getInvitee() {
        return invitee;
    }
    public void setInvitee(Long invitee) {
        this.invitee = invitee;
    }
    public Long getInviter() {
        return inviter;
    }
    public void setInviter(Long inviter) {
        this.inviter = inviter;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}


