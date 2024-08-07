package com.Art.Utils;

import com.Art.DTOS.RegisterUserRequest.RegisterUserRequest;
import com.Art.Data.models.User;

public class Mapper {
    public static User map (RegisterUserRequest registerUserRequest){
        User user = new User();
        user.setUserName(registerUserRequest.getUserName());
        user.setPassword(registerUserRequest.getPassword());
        user.setEmail(registerUserRequest.getEmail());

        return user;
    }
}
