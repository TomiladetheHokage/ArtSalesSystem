package com.Art.Data.Repositories;

import com.Art.Data.models.AdminUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminUserRepo extends MongoRepository<AdminUser, String> {

}
