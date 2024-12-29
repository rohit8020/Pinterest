package com.rohitgangwar.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "board_invitations")
public class BoardInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long invitee;
    private Long inviter;
    private Long boardId;
    private String description;
    private String status;
    private LocalDateTime updatedAt;

    public Long getBoardId() {
        return boardId;
    }
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
    public void setInviter(Long inviter) {
        this.inviter = inviter;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public void setInviterId(Long inviter) {
        this.inviter = inviter;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
}
