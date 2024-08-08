package com.Art.Services;

import com.Art.DTOS.Request.RegisterUserRequest;
import com.Art.DTOS.Request.UpdateProfileRequest;
import com.Art.DTOS.Response.DeleteProfileResponse;
import com.Art.DTOS.Response.LoginUserResponse;
import com.Art.DTOS.Response.RegisterUserResponse;
import com.Art.DTOS.Response.UpdateProfileResponse;
import com.Art.Data.models.ArtWork;
import com.Art.Data.models.User;
import com.Art.Exceptions.UserExistAlreadyException;
import com.Art.Exceptions.titleAlreadyExistException;
import com.Art.Exceptions.titleNotFoundException;
import com.Art.Exceptions.userNameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface userService {
    ArtWork createPost(MultipartFile file, String tile, String description) throws IOException, titleAlreadyExistException;
    RegisterUserResponse signUp(RegisterUserRequest registerUserRequest) throws UserExistAlreadyException;
    ArtWork findArtByTitle(String title) throws titleNotFoundException;
    User viewProfile(String userName);
    UpdateProfileResponse updateProfile(UpdateProfileRequest updateProfileRequest, String username, String password) throws userNameNotFoundException;
    DeleteProfileResponse deleteProfile(String  userName, String password);
    User findUser(String username);
    LoginUserResponse login(String username, String password);
    void logout(String username, String password);
    List<ArtWork> findAllArt (String userName, String email);
    void deleteAllArt (String userName, String email);
    void deleteArt (String userName, String email);
}
