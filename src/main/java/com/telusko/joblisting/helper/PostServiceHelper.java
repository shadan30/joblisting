package com.telusko.joblisting.helper;

import com.telusko.joblisting.configuration.helper.RedisHelper;
import com.telusko.joblisting.dto.PostDTO;
import com.telusko.joblisting.exception.code.RedisException;
import com.telusko.joblisting.model.Post;
import com.telusko.joblisting.transformer.IPostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class PostServiceHelper {

    @Autowired
    IPostMapper postMapper;

    @Autowired
    RedisHelper redisHelper;

    public List<PostDTO> convertPostToPostDTOList(List<Post> posts) {
        if (isNull(posts)) {
            return new ArrayList<>();
        }
        return postMapper.convertToPostDTOs(posts);
    }

    public List<Post> convertPostDTOToPostList(List<PostDTO> posts) {
        if (isNull(posts)) {
            return new ArrayList<>();
        }
        return postMapper.convertToPosts(posts);
    }

    public Post convertPostDTOToPost(PostDTO post){
        if (isNull(post)) {
            return null;
        }
        return postMapper.convertToPost(post);
    }

    public void addPostDTODetailsInPostList(List<Post> postList,PostDTO post) {
        if (isNull(post) || isNull(postList) || postList.isEmpty()) {
            return;
        }
        postMapper.addPostDTODetailsInPostList(postList,post);
    }

    public void updateCacheEntry(String cacheName, Object key, Object value) {
        if (isNull(cacheName) || isNull(key) || isNull(value)) {
            throw new RedisException("Empty request to update Cache");
        }
        redisHelper.putCache(cacheName, key, value);
    }

}
