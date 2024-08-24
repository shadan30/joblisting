package com.telusko.joblisting.repository;

import com.telusko.joblisting.model.Post;

import java.util.List;

public interface ICustomPostRepository {

    List<Post> findPostsByProfile(String criteria);
}
