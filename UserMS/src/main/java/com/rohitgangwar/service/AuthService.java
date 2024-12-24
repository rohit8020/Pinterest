package com.rohitgangwar.service;

import com.rohitgangwar.dto.AuthRequest;
import com.rohitgangwar.dto.UserDTO;
import com.rohitgangwar.entity.User;
import com.rohitgangwar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public void saveUser(User credential)throws Exception {
        Optional<User> optional=repository.findByEmail(credential.getEmail());
        if(optional.isPresent())throw new Exception("User with this email or username is already present!");
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
    }

    public User getUser(String email) throws Exception {
        Optional<User> optional = repository.findByEmail(email);
        User user = optional.orElseThrow(() -> new Exception("User not found with the email: " + email));
        return user;
    }

    public String generateToken(AuthRequest authRequest) {
        return jwtService.generateToken(authRequest);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public List<UserDTO> getUsersByUserIds(List<Long> userIds) {
        List<User> users = repository.findAllByIds(userIds);
        return users.stream().map(e -> UserDTO.valueOf(e)).toList();
    }

    public User getUserByUserId(Long userId) throws Exception {
        Optional<User> optional = repository.findById(userId);
        User user = optional.orElseThrow(() -> new Exception("User not found with the userId: " + userId));
        return user;
    }
}
