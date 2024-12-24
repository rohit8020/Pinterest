package com.rohitgangwar.controller;

import com.rohitgangwar.dto.AuthRequest;
import com.rohitgangwar.dto.UserDTO;
import com.rohitgangwar.dto.UserResponseDTO;
import com.rohitgangwar.entity.User;
import com.rohitgangwar.repository.UserRepository;
import com.rohitgangwar.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        try {
            service.saveUser(user);
            return new ResponseEntity<String>("User registered successfuly!",HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/userbyemail")
    public ResponseEntity<UserDTO> getUser(@RequestParam String email)throws Exception {
        User user=service.getUser(email);
        return new ResponseEntity<UserDTO>(UserDTO.valueOf(user), HttpStatus.OK) ;
    }

    @GetMapping("/userbyid")
    public ResponseEntity<UserDTO> getUser(@RequestParam Long userId)throws Exception {
        User user=service.getUserByUserId(userId);
        return new ResponseEntity<UserDTO>(UserDTO.valueOf(user),HttpStatus.OK) ;
    }

    @GetMapping("/fetchByUserIds")
    public ResponseEntity<List<UserResponseDTO>> fetchUsersByUserIds(@RequestParam List<Long> userIds) {
        List<UserResponseDTO> usernameList = service.getUsersByUserIds(userIds).stream().map(e->{
            UserResponseDTO userDTO=new UserResponseDTO();
            userDTO.setImage(e.getImage());
            userDTO.setUserId(e.getUserId());
            userDTO.setUsername(e.getUsername());
            return userDTO;
        }).toList();
        return new ResponseEntity<List<UserResponseDTO>>(usernameList, HttpStatus.OK);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Optional<User> optional=userRepository.findByEmail(authRequest.getEmail());
        User user=optional.get();
        authRequest.setUsername(user.getUsername());
        authRequest.setUserId(user.getId());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest);
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
