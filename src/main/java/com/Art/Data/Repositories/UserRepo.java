package com.Art.Data.Repositories;

import com.Art.Data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {
    User findByUserName(String username);
    User findUserByUserNameAndPassword(String username, String password);
    boolean existsByEmail(String email);
    //booleane existsByEmail
}
