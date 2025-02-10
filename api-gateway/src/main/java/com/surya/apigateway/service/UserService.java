package com.surya.apigateway.service;

import com.surya.apigateway.repository.UserRepository;
import com.surya.apigateway.utilities.JwtUtil;
import com.surya.microservices.dto.User.UserRequest;
import com.surya.microservices.dto.User.UserResponse;
import com.surya.microservices.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private User save(User user) {
        log.trace("save()");
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("save() Exception: {}", e.getMessage());
        }
        return null;
    }

    public User findByUserName(String userName) {
        log.trace("findByUserName()");
        try {
            return userRepository.findByUserName(userName);
        } catch (Exception e) {
            log.error("findByUserName() Exception: {}", e.getMessage());
        } return null;
    }

    public UserResponse registerUser(UserRequest userRequest) {
        log.trace("registerUser()");
        try {
            User existUser = findByUserName(userRequest.userName());
            if(existUser != null) {
                return new UserResponse(existUser.getUserName(), "");
            } else {
                existUser = User.builder()
                        .userName(userRequest.userName())
                        .password(passwordEncoder.encode(userRequest.password()))
                        .build();
                existUser =save(existUser);
                if(existUser != null)
                    return new UserResponse(existUser.getUserName(), "");
            }
        } catch (Exception e) {
            log.error("registerUser() Exception: {}", e.getMessage());
        }
        return null;
    }

    public UserResponse loginUser(UserRequest userRequest) {
        log.trace("loginUser()");
        try {
            User user = findByUserName(userRequest.userName());
            if(user != null & validatePassword(userRequest.password(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUserName());
                return new UserResponse(user.getUserName(), token);
            }
        } catch (Exception e) {
            log.error("loginUser() Exception: {}", e.getMessage());
        }
        return new UserResponse(userRequest.userName(), "");
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
