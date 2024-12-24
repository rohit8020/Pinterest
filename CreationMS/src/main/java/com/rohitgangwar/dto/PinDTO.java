package com.rohitgangwar.dto;

import com.rohitgangwar.entity.Pin;
import jakarta.validation.constraints.NotNull;

public class PinDTO {
    private Long pinId;
    @NotNull(message = "Title should not be null!")
    private String title;
    @NotNull(message = "Image should not be null!")
    private String image;
    @NotNull(message = "Description should not be null!")
    private String description;
    @NotNull(message = "BoardId should not be null!")
    private Long boardId;
    @NotNull(message = "UserId should not be null!")
    private Long createdBy;
    @NotNull(message = "Link should not be null!")
    private String link;
    @NotNull(message = "Tags should not be null!")
    private String tags;
    @NotNull(message = "Privacy should not be null!")
    private boolean privacy;

    public Long getPinId() {
        return pinId;
    }
    public void setPinId(Long pinId) {
        this.pinId = pinId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
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
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public boolean getPrivacy() {
        return privacy;
    }
    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public static PinDTO valueOf(Pin pin) {
        PinDTO pinDTO=new PinDTO();
        pinDTO.setBoardId(pin.getBoardId());
        pinDTO.setDescription(pin.getDescription());
        pinDTO.setImage(pin.getImage());
        pinDTO.setLink(pin.getLink());
        pinDTO.setPinId(pin.getId());
        pinDTO.setPrivacy(pin.getPrivacy());
        pinDTO.setTags(pin.getTags());
        pinDTO.setTitle(pin.getTitle());
        pinDTO.setCreatedBy(pin.getCreatedBy());
        return pinDTO;
    }

    public Pin createEntity() {
        Pin pin=new Pin();
        pin.setBoardId(this.getBoardId());
        pin.setDescription(this.getDescription());
        pin.setImage(this.getImage());
        pin.setLink(this.getLink());
        pin.setId(this.getPinId());
        pin.setPrivacy(this.getPrivacy());
        pin.setTags(this.getTags());
        pin.setTitle(this.getTitle());
        pin.setCreatedBy(this.getCreatedBy());
        return pin;
    }
}

