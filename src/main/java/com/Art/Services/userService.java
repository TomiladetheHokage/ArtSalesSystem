package com.Art.Services;

import com.Art.DTOS.RegisterUserRequest.RegisterUserRequest;
import com.Art.DTOS.RegisterUserResponse.RegisterUserResponse;
import com.Art.Data.models.ArtWork;
import com.Art.Data.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface userService {
    ArtWork createPost(MultipartFile file, String tile, String description) throws IOException;
    RegisterUserResponse signUp(RegisterUserRequest registerUserRequest);
    ArtWork findArtByTitle(String title);
    User viewProfile(String userName);
   // User updateProfile(int userId, UpdateUserRequest updateUserRequest);
  // List<ArtWork> searchArtworks(String query);
    void deleteUser(int userId);







}
