package com.rohitgangwar.dto;

public class InvitationDTO {
    private String invitee;
    private String description;
    private Long boardId;
    private String inviter;
    private String boardName;
    private Long inviterId;

    public Long getInviterId() {
        return inviterId;
    }
    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }
    public String getBoardName() {
        return boardName;
    }
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
    public String getInviter() {
        return inviter;
    }
    public void setInviter(String inviter) {
        this.inviter = inviter;
    }
    public String getInvitee() {
        return invitee;
    }
    public void setInvitee(String invitee) {
        this.invitee = invitee;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getBoardId() {
        return boardId;
    }
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
}
