package com.telusko.joblisting.core.controller;

import com.telusko.joblisting.common.dto.UserDTO;
import com.telusko.joblisting.core.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping
    public String createNewUser(@RequestBody UserDTO user) {
        log.info("POST /v1/user API");
        return userService.createNewUser(user);
    }
    @PutMapping("/password")
    public String updateUserPassword(@RequestBody UserDTO user) {
        log.info("PUT /v1/user/password API");
        return userService.updateUserPassword(user);
    }
}
