package com.Art.Services;

import com.Art.DTOS.Request.RegisterUserRequest;
import com.Art.DTOS.Response.RegisterUserResponse;
import com.Art.Data.Repositories.ArtworkRepo;
import com.Art.Data.Repositories.UserRepo;
import com.Art.Data.models.ArtWork;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ArtworkRepo artworkRepo;

    @BeforeEach
   public void setUp() {
        userRepo.deleteAll();
        //artworkRepo.deleteAll();
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
    @Test
    public void testUploadPicture(){
        Path path = Paths.get("C:\\Users\\OWNER\\Downloads\\ArtSalesSystem\\src\\main\\resources\\static\\Screenshot 2024-08-05 120706.png");
        try(InputStream inputStream = Files.newInputStream(path)){
            MultipartFile file = new MockMultipartFile("file", inputStream);
            ArtWork artWork = userService.createPost(file, "title", "description");
            assertThat(artWork.getImageUrl()).isNotNull();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFIndByTitle() throws IOException {
        Path path = Paths.get("C:\\Users\\OWNER\\Downloads\\ArtSalesSystem\\src\\main\\resources\\static\\Screenshot 2024-08-05 120706.png");
        String title = "okay";
        String description = "someDescription";
        InputStream inputStream = Files.newInputStream(path);
        MultipartFile file = new MockMultipartFile("file", inputStream);

        userService.createPost(file, title , description);
        ArtWork foundArt =  userService.findArtByTitle(title);
        Assertions.assertEquals(title, foundArt.getTitle());
    }

    @Test
    public void testUserCan() throws IOException {}






}