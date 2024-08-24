package com.telusko.joblisting.transformer;

import com.telusko.joblisting.dto.PostDTO;
import com.telusko.joblisting.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface IPostMapper {
    PostDTO convertToPostDTO(Post post);

    Post convertToPost(PostDTO post);

    List<PostDTO> convertToPostDTOs(List<Post> posts);

    List<Post> convertToPosts(List<PostDTO> posts);

    void addPostDTODetailsInPost(@MappingTarget Post postDTO, PostDTO post);

    default void addPostDTODetailsInPostList(@MappingTarget List<Post> postList, PostDTO post) {
        for (Post postDTO : postList) {
            addPostDTODetailsInPost(postDTO, post);
        }
    }
}
