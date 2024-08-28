package com.telusko.joblisting.core.controller;

import com.telusko.joblisting.common.dto.UserDTO;
import com.telusko.joblisting.core.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDTO user) {
        log.info("POST /v1/user/register API");
        return userService.registerUser(user);
    }
}
