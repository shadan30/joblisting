package com.telusko.joblisting.core.service;

import com.telusko.joblisting.common.dto.UserDTO;

public interface IUserService {

    String createNewUser(UserDTO user);
    String updateUserPassword(UserDTO user);
}
