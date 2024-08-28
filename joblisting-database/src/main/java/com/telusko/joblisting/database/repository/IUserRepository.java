package com.telusko.joblisting.database.repository;

import com.telusko.joblisting.common.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<User,String> {

    @Cacheable(value = "userName", key = "#userName")
    User findByUserName(String userName);
}
