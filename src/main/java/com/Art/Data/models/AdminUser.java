package com.Art.Data.models;

import lombok.Data;

import java.util.List;

@Data
public class AdminUser {
    private String username;
    private String password;
    private String role;
    private List<User> ListOfusers;
}
