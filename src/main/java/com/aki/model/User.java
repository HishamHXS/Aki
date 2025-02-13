package com.aki.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "email", columnDefinition = "citext")
    private String email;
    @Column(name = "hash_pass", nullable = false)
    private String password;
}
