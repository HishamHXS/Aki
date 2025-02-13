package com.aki.service;

import com.aki.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/** Add more specific tests. */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    private UserDTO testUserDTO;
    private final Long userId = 1L;
    private final String username = "testUser";
    private final String password = "testPassword";
    private final String token = "testToken";

    @BeforeEach
    void setUp() {
        testUserDTO = new UserDTO();
        testUserDTO.setId(userId);
        testUserDTO.setUsername(username);
        testUserDTO.setPassword(password);
    }

    @Test
    void testLoginUser_Success() throws Exception {
        when(userService.findByUsername(username)).thenReturn(Optional.of(testUserDTO));

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        when(tokenService.generateToken(userId, authentication)).thenReturn(token);

        Optional<String> result = authService.loginUser(username, password);

        assertTrue(result.isPresent());
        assertEquals(token, result.get());

        verify(userService).findByUsername(username);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(userId, authentication);
    }

    @Test
    void testLoginUser_UserNotFound() {
        when(userService.findByUsername(username)).thenReturn(Optional.empty());

        Optional<String> result = authService.loginUser(username, password);

        assertFalse(result.isPresent());

        verify(userService).findByUsername(username);
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, never()).generateToken(any(Long.class), any(Authentication.class));
    }
}
