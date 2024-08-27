package com.telusko.joblisting.core.controller;


import com.telusko.joblisting.common.dto.PostDTO;
import com.telusko.joblisting.core.service.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/posts")
public class PostController {

    @Autowired
    IPostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        log.info("GET /v1/posts API");
        return postService.fetchAllPosts();
    }

    @GetMapping("/profile/{profile}")
    public List<PostDTO> getPostsByProfile(@PathVariable("profile") String profile) {
        log.info("GET /v1/posts/profile API");
        return postService.fetchPostsByProfile(profile);
    }

    @GetMapping("/profile/experience")
    public List<PostDTO> getPostsByProfileAndExperience(
            @RequestParam(value = "profile") String profile,
            @RequestParam(value = "experience") Integer experience) {
        log.info("GET /v1/posts/profile/experience API");
        return postService.fetchPostsByProfileAndExperience(profile, experience);
    }

    @PostMapping
    public String createJobPost(@RequestBody @Valid PostDTO post) {
        log.info("POST /v1/posts/create API");
        return postService.createJobPost(post);
    }

    @DeleteMapping("/profile")
    public String deleteJobPostByProfile(@RequestParam String profile) {
        log.info("DELETE /v1/posts/profile API");
        return postService.deleteJobPostByProfile(profile);
    }

    @PutMapping("/profile")
    public List<PostDTO> updateJobByProfile(@RequestParam String profile,
                                     @RequestBody PostDTO post) {
        log.info("PUT /v1/posts/profile API");
        return postService.updateJobByProfile(profile, post);
    }

}
