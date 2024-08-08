package com.Art.Services;

import com.Art.DTOS.Request.RegisterUserRequest;
import com.Art.DTOS.Request.UpdateProfileRequest;
import com.Art.DTOS.Response.LoginUserResponse;
import com.Art.DTOS.Response.RegisterUserResponse;
import com.Art.DTOS.Response.UpdateProfileResponse;
import com.Art.Data.Repositories.ArtworkRepo;
import com.Art.Data.Repositories.UserRepo;
import com.Art.Data.models.ArtWork;
import com.Art.Data.models.User;
import com.Art.Data.models.userRoles;
import com.Art.Exceptions.UserExistAlreadyException;
import com.Art.Exceptions.titleAlreadyExistException;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
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
    public ArtWork createPost(MultipartFile multipartFile, String tile, String description) throws IOException, titleAlreadyExistException {
        ArtWork artWork = new ArtWork();
        if(titleExists(tile)){
            throw new titleAlreadyExistException(String.format("%s  already exist", tile));
        }
        String url = cloudinary.uploader().upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString())).get("url").toString();
        artWork.setImageUrl(url);
        artWork.setDescription(description);
        artWork.setTitle(tile);
        return artworkRepo.save(artWork);
    }

    @Override
    public RegisterUserResponse signUp(RegisterUserRequest registerUserRequest) throws UserExistAlreadyException {

        if(userRepo.existsByEmail(registerUserRequest.getEmail())){
            throw new UserExistAlreadyException(String.format("%s already exist",registerUserRequest.getEmail()));
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
        ArtWork artWork = artworkRepo.findByTitle(title);
        if (artWork != null) {
            return artWork;
        } else {
            throw new IllegalArgumentException("Artwork with title " + title + " not found");
        }
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
    public UpdateProfileResponse updateProfile(UpdateProfileRequest updateProfileRequest, String username, String password) {
        User existingUser = userRepo.findUserByUserNameAndPassword(username, password);

        if (existingUser != null) {
            existingUser.setUserName(updateProfileRequest.getUserName());
            existingUser.setPassword(updateProfileRequest.getPassword());
            existingUser.setEmail(updateProfileRequest.getEmail());
            userRepo.save(existingUser);

            UpdateProfileResponse updateProfileResponse = new UpdateProfileResponse();
            updateProfileResponse.setMessage("Successfully updated");
            return updateProfileResponse;
        }
        throw new IllegalArgumentException("User with username " + username + " not found");
    }


    @Override
    public void deleteProfile(String  userName, String password) {
        List<User> users =  userRepo.findAll();
        userRepo.findUserByUserNameAndPassword(userName, password);
    }

    @Override
    public User findUser(String username) {
        return null;
    }

    @Override
    public LoginUserResponse login(String username, String password) {
        User user = userRepo.findByUserName(username);
        if(username.equals(user.getUserName()) && password.equals(user.getPassword())){
            LoginUserResponse loginUserResponse = new LoginUserResponse();
            loginUserResponse.setMessage("Successfully logged in");
            user.setRole(userRoles.USER);
            user.setLoginStatus(true);
            return loginUserResponse;
        }
        else{
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    @Override
    public void logout(String username, String password) {

    }

    @Override
    public List<ArtWork> findAllArt(String userName, String email) {
        return List.of();
    }

    @Override
    public void deleteAllArt(String userName, String email) {

    }

    @Override
    public void deleteArt(String userName, String email) {

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
