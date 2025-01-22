package com.aki.controller;

import com.aki.model.UserDTO;
import com.aki.service.AuthService;
import com.aki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        logger.info(userDTO.getUsername(), userDTO.getPassword());
        logger.info(userDTO.getPassword());
        Optional<String> jwt = authService.loginUser(userDTO.getUsername(), userDTO.getPassword());

        return jwt.map(s -> ResponseEntity.ok("Bearer " + s)).orElseGet(() ->
                ResponseEntity.status(401).body("Invalid credentials"));

    }
}