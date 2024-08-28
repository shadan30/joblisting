package com.telusko.joblisting.common.transformer;

import com.telusko.joblisting.common.dto.UserDTO;
import com.telusko.joblisting.common.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface IUserMapper {

    UserDTO convertUserToUserDTO(User user);

    User convertUserDTOToUser(UserDTO user);

    List<UserDTO> convertUserListToUserDTOList(List<User> user);

    List<User> convertUserDTOListToUserList(List<UserDTO> user);
}
