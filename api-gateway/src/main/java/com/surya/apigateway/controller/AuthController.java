package com.surya.apigateway.controller;


import com.surya.apigateway.service.UserService;
import com.surya.microservices.dto.User.UserRequest;
import com.surya.microservices.dto.User.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserRequest userRequest) {
        return userService.loginUser(userRequest);
    }

}
