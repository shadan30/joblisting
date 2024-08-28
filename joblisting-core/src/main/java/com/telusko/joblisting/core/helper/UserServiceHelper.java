package com.telusko.joblisting.core.helper;

import com.telusko.joblisting.common.dto.UserDTO;
import com.telusko.joblisting.common.exception.code.RedisException;
import com.telusko.joblisting.common.model.User;
import com.telusko.joblisting.common.transformer.IUserMapper;
import com.telusko.joblisting.core.configuration.helper.RedisHelper;
import com.telusko.joblisting.security.configuration.PasswordEncoderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {

    @Autowired
    RedisHelper redisHelper;

    @Autowired
    IUserMapper userMapper;

    @Autowired
    PasswordEncoderService passwordEncoderService;

    public List<UserDTO> convertUserListToUserDTOList(List<User> user) {
        if (isNull(user)) {
            return new ArrayList<>();
        }
        return userMapper.convertUserListToUserDTOList(user);
    }

    public List<User> convertUserDTOListToUserList(List<UserDTO> user) {
        if (isNull(user)) {
            return new ArrayList<>();
        }
        return userMapper.convertUserDTOListToUserList(user);
    }

    public UserDTO convertUserToUserDTO(User user) {
        if (isNull(user)) {
            return null;
        }
        return userMapper.convertUserToUserDTO(user);
    }

    public User convertUserDTOToUser(UserDTO user) {
        if (isNull(user)) {
            return null;
        }
        return userMapper.convertUserDTOToUser(user);
    }

    public void updateCacheEntry(String cacheName, Object key, Object value) {
        if (isNull(cacheName) || isNull(key) || isNull(value)) {
            throw new RedisException("Empty request to update Cache");
        }
        redisHelper.putCache(cacheName, key, value);
    }

    public void encodePassword(UserDTO user) {
        if (isNull(user) || isNull(user.getPassword())) {
            return;
        }
        user.setPassword(passwordEncoderService.encodePassword(user.getPassword()));
    }

    public boolean matchesPassword(String newPassword, String encodedPassword) {
        if (isBlank(newPassword) || isBlank(encodedPassword)) {
            return false;
        }
        return passwordEncoderService.matchesPassword(newPassword, encodedPassword);
    }
}
