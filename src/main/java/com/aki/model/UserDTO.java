package com.aki.model;

import lombok.Data;

@Data
public class UserDTO {

    // This is only required in GET requests.
    private Long id;

    private String username;

    private String password;

    private String email;

}
