package com.Art.Services;

import com.Art.DTOS.RegisterUserRequest.RegisterUserRequest;
import com.Art.DTOS.RegisterUserResponse.RegisterUserResponse;
import com.Art.Data.Repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
   public void setUp() {
        userRepo.deleteAll();
    }

 @Autowired
 private UserServiceImpl userService;

    @Test
    public void testUserCanRegister() {
        RegisterUserRequest user = new RegisterUserRequest();
        user.setUserName("tomi");
        user.setPassword("123456");
        user.setEmail("tomi@gmail.com");
        RegisterUserResponse response = userService.signUp(user);
        assertNotNull(response);
    }

    @Test
    public void testUserCannotRegisterWithTheSameEmail() {
        RegisterUserRequest firstUser = new RegisterUserRequest();
        firstUser.setUserName("tomi");
        firstUser.setPassword("123456");
        firstUser.setEmail("tomi@gmail.com");
        userService.signUp(firstUser);

        RegisterUserRequest secondUser = new RegisterUserRequest();
        secondUser.setUserName("romi");
        secondUser.setPassword("55555");
        secondUser.setEmail("tomi@gmail.com");

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.signUp(secondUser);
        });

        Assertions.assertEquals("Email already exists", thrown.getMessage());
    }





}