package com.telusko.joblisting.repository;

import com.telusko.joblisting.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends MongoRepository<Post, String> {
    List<Post> findByProfile(String profile);

    List<Post> findByProfileAndExp(String profile, Integer experience);

    void deleteByProfile(String profile);
}
