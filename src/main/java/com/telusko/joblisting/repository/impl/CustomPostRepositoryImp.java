package com.telusko.joblisting.repository.impl;

import com.telusko.joblisting.model.Post;
import com.telusko.joblisting.repository.ICustomPostRepository;
import com.telusko.joblisting.repository.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

// can be used to use custom queries
@Repository
public class CustomPostRepositoryImp implements ICustomPostRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Post> findPostsByProfile(String criteria) {
        // Example custom query implementation
        return mongoTemplate.find(query(where("profile").is(criteria)), Post.class);
    }

}
