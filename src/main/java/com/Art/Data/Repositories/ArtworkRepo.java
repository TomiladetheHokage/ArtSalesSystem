package com.Art.Data.Repositories;

import com.Art.Data.models.ArtWork;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtworkRepo extends MongoRepository<ArtWork, String> {

}
