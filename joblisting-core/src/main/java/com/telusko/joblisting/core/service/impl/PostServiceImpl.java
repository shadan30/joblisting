package com.telusko.joblisting.core.service.impl;

import com.telusko.joblisting.common.dto.PostDTO;
import com.telusko.joblisting.common.exception.code.DuplicateEntityException;
import com.telusko.joblisting.common.exception.code.EntityNotFoundException;
import com.telusko.joblisting.common.exception.code.EntityNotSavedException;
import com.telusko.joblisting.common.exception.code.ValidationException;
import com.telusko.joblisting.common.model.Post;
import com.telusko.joblisting.core.helper.PostServiceHelper;
import com.telusko.joblisting.core.service.IPostService;
import com.telusko.joblisting.database.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    @Autowired
    IPostRepository postRepository;

    @Autowired
    PostServiceHelper postServiceHelper;


    @Override
    public List<PostDTO> fetchAllPosts() {
        List<Post> posts;
        try {
            posts = postRepository.findAll();
        } catch (Exception ex) {
            log.error("Error while fetching Jobs");
            throw new EntityNotFoundException(ex.toString());
        }
        if (isNull(posts) || posts.isEmpty()) {
            log.error("No Posts present in database");
            throw new EntityNotFoundException("No Posts present in Database");
        }
        log.info("Total posts present : {}",posts.size());
        return postServiceHelper.convertPostToPostDTOList(posts);
    }

    @Override
    public List<PostDTO> fetchPostsByProfile(String profile) {
        if (isBlank(profile)) {
            throw new ValidationException("Profile is Empty in request");
        }
        List<Post> posts;
        try {
            posts = postRepository.findByProfile(profile);
        } catch (Exception ex) {
            throw new EntityNotFoundException(ex.toString());
        }
        if (isNull(posts) || posts.isEmpty()) {
            throw new EntityNotFoundException("Post not found for profile : " + profile);
        }
        return postServiceHelper.convertPostToPostDTOList(posts);
    }

    @Override
    public List<PostDTO> fetchPostsByProfileAndExperience(String profile, Integer experience) {
        validateProfileAndExperienceRequest(profile, experience);
        List<Post> posts;
        try {
            posts = postRepository.findByProfileAndExp(profile, experience);
        } catch (Exception ex) {
            throw new EntityNotFoundException(ex.toString());
        }
        if (isNull(posts) || posts.isEmpty()) {
            throw new EntityNotFoundException("Post not found for profile : " + profile +
                    " and experience : " + experience);
        }
        return postServiceHelper.convertPostToPostDTOList(posts);
    }

    @Override
    @Transactional
    public String createJobPost(PostDTO post) {
        if (isNull(post)) {
            throw new ValidationException("Empty post request");
        }

        validateForDuplicateEntity(post.getProfile());
        Post postSaved;
        try {
            //we would have use GlobalExceptionHandler , but there can be many exceptions thrown while using repository
            postSaved=postRepository.save(postServiceHelper.convertPostDTOToPost(post));
        } catch (Exception ex) {
            throw new EntityNotSavedException(ex.getMessage());
        }
        // will update cache after the fetch
        // Since we are first fetching to check for duplicate (in validateForDuplicateEntity)
        // So , if we do not find any entry for profile we will come in the flow and save that entry
        // Now , we have to update that cache entry which was saved as null before , for correct fetching
        postServiceHelper.updateCacheEntry("profile", post.getProfile(), List.of(postSaved));
        return "Successfully saved post with profile: " + post.getProfile();
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    //Not use cache evict here , because fetchPostsFromDBByProfile is using Cacheable , so using cacheEvict here will
    //empty the cache and while fetching the data will be fetched from database , and stale data will be saved in cache,
    //so we have use cacheable or cachePut while calling saveAll
    public List<PostDTO> updateJobByProfile(String profile, PostDTO post) {
        if (isNull(profile) || isNull(post)) {
            throw new ValidationException("Request cannot be empty");
        }
        if (!profile.equals(post.getProfile())) {
            throw new ValidationException("Profile does not match with request body");
        }
        List<Post> postFetchedList = fetchPostsFromDBByProfile(profile); // will use cache to fetch data
        postServiceHelper.addPostDTODetailsInPostList(postFetchedList, post);//update values in postFetchedList
        List<Post> response;
        try {
            response = postRepository.saveAll(postFetchedList);
        } catch (Exception ex) {
            throw new EntityNotSavedException("Error saving updated posts: " + ex.getMessage());
        }
        postServiceHelper.updateCacheEntry("profile", profile, response); // will update cache
        return postServiceHelper.convertPostToPostDTOList(response);
    }

    private List<Post> fetchPostsFromDBByProfile(String profile) {
        List<Post> postFetchedList;
        try {
            postFetchedList = postRepository.findByProfile(profile);
        } catch (Exception ex) {
            throw new EntityNotFoundException(ex.toString());
        }
        if (isNull(postFetchedList) || postFetchedList.isEmpty()) {
            throw new EntityNotFoundException("Jobs not found for profile : " + profile);
        }
        return postFetchedList;
    }

    private void validateForDuplicateEntity(String profile) {
        List<Post> postFetchedList;
        try {
            postFetchedList = postRepository.findByProfile(profile);
        } catch (Exception ex) {
            throw new EntityNotFoundException(ex.toString());
        }
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
