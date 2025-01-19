package com.aki.service;

import com.aki.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public Optional<String> loginUser(String username, String password) {
        logger.info("Attempting login for username: {}", username);

        Optional<UserDTO> userOptional = userService.findByUsername(username);

        if (userOptional.isEmpty()) {
            logger.warn("Login failed: No user found with username: {}", username);
            return Optional.empty();
        }

        UserDTO user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("Login failed: Invalid password for username: {}", username);
            return Optional.empty();
        }

        logger.info("Login successful for username: {}", username);
        String token = "placeholder";
        return Optional.of(token);
    }
}
