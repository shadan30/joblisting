package com.telusko.joblisting.database.repository;


import com.telusko.joblisting.common.model.Post;

import java.util.List;

public interface ICustomPostRepository {

    List<Post> findPostsByProfile(String criteria);
}
