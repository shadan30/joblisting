package com.telusko.joblisting.repository;

import com.telusko.joblisting.model.Post;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends MongoRepository<Post, String> {
    @Cacheable(value = "profile", key = "#profile")
    List<Post> findByProfile(String profile);

    @Cacheable(value = "profileAndExp", key = "#profile + '_' + #experience")
    List<Post> findByProfileAndExp(String profile, Integer experience);

    @CacheEvict(value = "profile", key = "#profile")
    void deleteByProfile(String profile);
}
