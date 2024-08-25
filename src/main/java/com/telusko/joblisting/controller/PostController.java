package com.telusko.joblisting.controller;

import com.telusko.joblisting.dto.PostDTO;
import com.telusko.joblisting.service.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {

    @Autowired
    IPostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.fetchAllPosts();
    }

    @GetMapping("/profile/{profile}")
    public List<PostDTO> getPostsByProfile(@PathVariable("profile") String profile) {
        return postService.fetchPostsByProfile(profile);
    }

    @GetMapping("/profile/experience")
    public List<PostDTO> getPostsByProfileAndExperience(
            @RequestParam(value = "profile") String profile,
            @RequestParam(value = "experience") Integer experience) {
        return postService.fetchPostsByProfileAndExperience(profile, experience);
    }

    @PostMapping
    public String createJobPost(@RequestBody @Valid PostDTO post) {
        return postService.createJobPost(post);
    }

    @DeleteMapping("/profile")
    public String deleteJobPostByProfile(@RequestParam String profile) {
        return postService.deleteJobPostByProfile(profile);
    }

    @PutMapping("/profile")
    public List<PostDTO> updateJobByProfile(@RequestParam String profile,
                                     @RequestBody PostDTO post) {
        return postService.updateJobByProfile(profile, post);
    }

}
