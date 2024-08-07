package com.Art.DTOS.RegisterUserRequest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserRequest {
    private String userName;
    private String password;
    private String email;
}
