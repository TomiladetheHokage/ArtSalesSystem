package com.Art.Services;

import com.Art.DTOS.RegisterUserRequest.RegisterUserRequest;
import com.Art.DTOS.RegisterUserRequest.UpdateProfileRequest;
import com.Art.DTOS.RegisterUserResponse.RegisterUserResponse;
import com.Art.DTOS.RegisterUserResponse.UpdateProfileResponse;
import com.Art.Data.Repositories.ArtworkRepo;
import com.Art.Data.Repositories.UserRepo;
import com.Art.Data.models.ArtWork;
import com.Art.Data.models.User;
import com.Art.Data.models.userRoles;
import com.Art.Utils.Mapper;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.Art.Utils.Mapper.map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements userService{

    private final ArtworkRepo artworkRepo;
    private final Cloudinary cloudinary;
    private final UserRepo userRepo;


    @Override
    public ArtWork createPost(MultipartFile multipartFile, String tile, String description) throws IOException {
        ArtWork artWork = new ArtWork();
        if(titleExists(tile)){
            throw new IllegalArgumentException("Title already exists");
        }
        String url = cloudinary.uploader().upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString())).get("url").toString();
        artWork.setImageUrl(url);
        artWork.setDescription(description);
        artWork.setTitle(tile);
        return artworkRepo.save(artWork);
    }

    @Override
    public RegisterUserResponse signUp(RegisterUserRequest registerUserRequest) {

        if(userRepo.existsByEmail(registerUserRequest.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        else {
            User user = map(registerUserRequest);
            user.setRole(userRoles.USER);
            userRepo.save(user);

            RegisterUserResponse registerUserResponse = new RegisterUserResponse();
            registerUserResponse.setMessage("Successfully registered");

            return registerUserResponse;
        }

    }

    @Override
    public ArtWork findArtByTitle(String title) {
        for (ArtWork artWork : artworkRepo.findAll()) {
            if (artWork.getTitle().equals(title)) {
                return artWork;
            }
        }
       return null;
    }

    @Override
    public User viewProfile(String username) {
        User user = userRepo.findByUserName(username);
        if (user != null) {
            return user;
        } else {
            throw new IllegalArgumentException("User with username " + username + " not found");
        }
    }

    @Override
    public User updateProfile(UpdateProfileRequest updateProfileRequest, String username, String password) {
        User existingUser = userRepo.findByUserName(username);

        if (existingUser != null) {
            existingUser.setUserName(updateProfileRequest.getUserName());
            existingUser.setPassword(updateProfileRequest.getPassword());
            existingUser.setEmail(updateProfileRequest.getEmail());
            userRepo.save(existingUser);
            return existingUser;
        }
        throw new IllegalArgumentException("User with username " + username + " not found");
    }



    @Override
    public void deleteProfile(String  userName, String password) {
        List<User> users =  userRepo.findAll();
        userRepo.findUserByUserNameAndPassword(userName, password);
    }

    public boolean titleExists(String title) {
       List<ArtWork> art =  artworkRepo.findAll();
       for (ArtWork artWork : art) {
           if (artWork.getTitle().equals(title)) {
               return true;
           }
       }
       return false;
    }


}
