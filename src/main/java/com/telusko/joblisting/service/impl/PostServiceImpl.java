package com.telusko.joblisting.service.impl;

import com.telusko.joblisting.dto.PostDTO;
import com.telusko.joblisting.exception.code.DuplicateEntityException;
import com.telusko.joblisting.exception.code.EntityNotFoundException;
import com.telusko.joblisting.exception.code.EntityNotSavedException;
import com.telusko.joblisting.exception.code.ValidationException;
import com.telusko.joblisting.helper.PostServiceHelper;
import com.telusko.joblisting.model.Post;
import com.telusko.joblisting.repository.IPostRepository;
import com.telusko.joblisting.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    @Autowired
    IPostRepository postRepository;

    @Autowired
    PostServiceHelper postServiceHelper;


    @Override
    public List<PostDTO> fetchAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (isNull(posts) || posts.isEmpty()) {
            throw new EntityNotFoundException("No Posts present in Database");
        }
        return postServiceHelper.convertPostToPostDTOList(posts);
    }

    @Override
    public List<PostDTO> fetchPostsByProfile(String profile) {
        if (isBlank(profile)) {
            throw new ValidationException("Profile is Empty in request");
        }
        List<Post> posts = postRepository.findByProfile(profile);
        if (isNull(posts) || posts.isEmpty()) {
            throw new EntityNotFoundException("Post not found for profile : " + profile);
        }
        return postServiceHelper.convertPostToPostDTOList(posts);
    }

    @Override
    public List<PostDTO> fetchPostsByProfileAndExperience(String profile, Integer experience) {
        validateProfileAndExperienceRequest(profile, experience);
        List<Post> posts = postRepository.findByProfileAndExp(profile, experience);
        if (isNull(posts) || posts.isEmpty()) {
            throw new EntityNotFoundException("Post not found for profile : " + profile +
                    " and experience : " + experience);
        }
        return postServiceHelper.convertPostToPostDTOList(posts);
    }

    @Override
    public String createJobPost(PostDTO post) {
        if (isNull(post)) {
            throw new ValidationException("Empty post request");
        }

        validateForDuplicateEntity(post.getProfile());
        try {
            //we would have use GlobalExceptionHandler , but there can be many exceptions thrown while using repository
            postRepository.save(postServiceHelper.convertPostDTOToPost(post));
        } catch (Exception ex) {
            throw new EntityNotSavedException(ex.getMessage());
        }
        return "Successfully saved post with profile: " + post.getProfile();
    }

    @Override
    public String deleteJobPostByProfile(String profile) {
        if(isNull(profile) || profile.isEmpty()){
            throw new ValidationException("Profile cannot be empty");
        }
        fetchPostsFromDBByProfile(profile);
        try {
            //we would have use GlobalExceptionHandler , but there can be many exceptions thrown while using repository
            postRepository.deleteByProfile(profile);
        } catch (Exception ex) {
            throw new EntityNotSavedException(ex.getMessage());
        }
        return "Successfully deleted Job Posts with profile : "+profile;
    }

    @Transactional
    @Override
    public List<PostDTO> updateJopByProfile(String profile, PostDTO post) {
        if (isNull(profile) || isNull(post)) {
            throw new ValidationException("Request cannot be empty");
        }
        if (!profile.equals(post.getProfile())) {
            throw new ValidationException("Profile does not match with request body");
        }
        List<Post> postFetchedList = fetchPostsFromDBByProfile(profile);
        postServiceHelper.addPostDTODetailsInPostList(postFetchedList, post);//update values in postFetchedList
        List<Post> response;
        try {
            response = postRepository.saveAll(postFetchedList);
        } catch (Exception ex) {
            throw new EntityNotSavedException("Error saving updated posts: " + ex.getMessage());
        }
        return postServiceHelper.convertPostToPostDTOList(response);
    }

    private List<Post> fetchPostsFromDBByProfile(String profile) {
        List<Post> postFetchedList = postRepository.findByProfile(profile);
        if (isNull(postFetchedList) || postFetchedList.isEmpty()) {
            throw new EntityNotFoundException("Jobs not found for profile : " + profile);
        }
        return postFetchedList;
    }

    private void validateForDuplicateEntity(String profile) {
        List<Post> postFetchedList = postRepository.findByProfile(profile);
        if (nonNull(postFetchedList) && !postFetchedList.isEmpty()) {
            boolean hasDuplicate = postFetchedList.stream()//stream
                    .map(Post::getProfile) //get the profile of postFetchedList
                    .filter(Objects::nonNull) // go with only non-null profile fetched form postFetchedList
                    .anyMatch(p -> p.equalsIgnoreCase(profile)); // check if any profile is matching with post's profile
            if (hasDuplicate) {
                throw new DuplicateEntityException("Duplicate Found in Database while saving Posts");
            }
        }
    }

    private void validateProfileAndExperienceRequest(String profile, Integer experience) {
        if (isBlank(profile) && isNull(experience)) {
            throw new ValidationException("Both Profile and Experience are needed");
        } else if (isBlank(profile)) {
            throw new ValidationException("Profile is needed");
        } else if (isNull(experience)) {
            throw new ValidationException("Experience is needed");
        }
    }
}
