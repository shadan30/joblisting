package com.telusko.joblisting.core.service;

import com.telusko.joblisting.common.dto.PostDTO;

import java.util.List;

public interface IPostService {
    List<PostDTO> fetchAllPosts();

    List<PostDTO> fetchPostsByProfile(String profile);

    List<PostDTO> fetchPostsByProfileAndExperience(String profile, Integer experience);

    String createJobPost(PostDTO post);

    String deleteJobPostByProfile(String profile);

    List<PostDTO> updateJobByProfile(String profile, PostDTO post);
}
