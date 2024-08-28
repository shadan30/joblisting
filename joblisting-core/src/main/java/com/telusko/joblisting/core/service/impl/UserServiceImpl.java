package com.telusko.joblisting.core.service.impl;

import com.telusko.joblisting.common.dto.UserDTO;
import com.telusko.joblisting.common.exception.code.DuplicateEntityException;
import com.telusko.joblisting.common.exception.code.EntityNotFoundException;
import com.telusko.joblisting.common.exception.code.EntityNotSavedException;
import com.telusko.joblisting.common.exception.code.ValidationException;
import com.telusko.joblisting.common.model.User;
import com.telusko.joblisting.core.helper.UserServiceHelper;
import com.telusko.joblisting.core.service.IUserService;
import com.telusko.joblisting.database.repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    UserServiceHelper userServiceHelper;

    @Override
    public String registerUser(UserDTO user) {
        if (isNull(user) || isBlank(user.getUserName()) || isBlank((user.getPassword()))) {
            throw new ValidationException("Invalid User Details");
        }
        validateForDuplicateUser(user);
        User savedUser;
        try {
            savedUser = userRepository.save(userServiceHelper.convertUserDTOToUser(user));
        } catch (Exception ex) {
            throw new EntityNotSavedException(ex.getMessage());
        }
        userServiceHelper.updateCacheEntry("userName", user.getUserName(), savedUser);
        return user.getUserName() + " user registered successfully";
    }

    private void validateForDuplicateUser(UserDTO user) {
        User fetchedUser;
        try {
            fetchedUser = userRepository.findByUserName(user.getUserName());
        } catch (Exception ex) {
            throw new EntityNotFoundException(ex.getMessage());
        }
        if (nonNull(fetchedUser) && nonNull(fetchedUser.getUserName())
                && fetchedUser.getUserName().equals(user.getUserName())) {
            throw new DuplicateEntityException(user.getUserName() + " : user already registered");
        }
    }
}
