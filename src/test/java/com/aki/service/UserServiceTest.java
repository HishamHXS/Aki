package com.aki.service;

import com.aki.model.User;
import com.aki.model.UserDTO;
import com.aki.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
    }

    @Test
    void testSaveUser() {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setUsername(testUser.getUsername());
        testUserDTO.setPassword(testUser.getPassword());

        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.addUser(testUserDTO);

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(testUser.getUsername(), testUserDTO.getUsername());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        UserDTO userDTO = userService.getUserById(testUser.getId()).orElse(null);

        assertNotNull(userDTO);
        assertEquals(testUser.getId(), userDTO.getId());
        assertEquals(testUser.getUsername(), userDTO.getUsername());
    }
}
