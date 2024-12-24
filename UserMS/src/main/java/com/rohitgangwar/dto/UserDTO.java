package com.rohitgangwar.dto;

import com.rohitgangwar.entity.User;

public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static UserDTO valueOf(User user) {
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUserId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setImage(user.getImage());
        return userDTO;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
