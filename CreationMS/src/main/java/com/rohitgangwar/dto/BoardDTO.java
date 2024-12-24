package com.rohitgangwar.dto;

import com.rohitgangwar.entity.Board;
import com.rohitgangwar.entity.Pin;

import java.util.List;
import jakarta.validation.constraints.NotNull;

public class BoardDTO {
    private Long boardId;
    @NotNull(message = "Title should not be null!")
    private String title;
    @NotNull(message = "Cover Image should not be null!")
    private String coverImage;
    @NotNull(message = "userId should not be null!")
    private Long createdBy;
    @NotNull(message = "Summary should not be null!")
    private String summary;
    private List<Pin> pins;

    public List<Pin> getPins() {
        return pins;
    }
    public void setPins(List<Pin> pins) {
        this.pins = pins;
    }
    public Long getBoardId() {
        return boardId;
    }
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCoverImage() {
        return coverImage;
    }
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public static BoardDTO valueOf(Board board) {
        BoardDTO boardDTO=new BoardDTO();
        boardDTO.setBoardId(board.getId());
        boardDTO.setCoverImage(board.getCoverImage());
        boardDTO.setCreatedBy(board.getCreatedBy());
        boardDTO.setSummary(board.getSummary());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setPins(board.getPins());
        return boardDTO;
    }

    public Board createEntity() {
        Board board=new Board();
        board.setCoverImage(this.getCoverImage());
        board.setId(this.getBoardId());
        board.setSummary(this.getSummary());
        board.setTitle(this.getTitle());
        board.setCreatedBy(this.getCreatedBy());
        return board;
    }
}
