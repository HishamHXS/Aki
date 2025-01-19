package com.aki.controller;

import com.aki.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Optional<String> jwt = authService.loginUser(username, password);

        return jwt.map(s -> ResponseEntity.ok("Bearer " + s)).orElseGet(() ->
                ResponseEntity.status(401).body("Invalid credentials"));

    }
}
