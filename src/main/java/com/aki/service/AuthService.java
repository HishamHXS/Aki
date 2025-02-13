package com.aki.service;

import com.aki.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public Optional<String> loginUser(String username, String password) {
        logger.info("Attempting login for username: {}", username);

        Optional<UserDTO> userOptional = userService.findByUsername(username);

        if (userOptional.isEmpty()) {
            logger.warn("Login failed: No user found with username: {}", username);
            return Optional.empty();
        }

        UserDTO user = userOptional.get();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            Long userId = user.getId();

            String token = tokenService.generateToken(userId, authentication);
            logger.info("Login successful for username: {}", username);
            return Optional.of(token);

        } catch (Exception e) {
            logger.error("Login failed: Authentication error for username: {}", username, e);
            return Optional.empty();
        }
    }
}
