package com.surya.apigateway.utilities;

import com.surya.apigateway.service.UserInfoService;
import com.surya.apigateway.service.UserService;
import com.surya.microservices.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        final String userName = jwtUtil.extractUsername(token);

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.findByUserName(userName);
            if(user != null) {
                if(jwtUtil.validateToken(token, userName)) {
                    UserDetails userDetails = userInfoService.loadUserByUsername(userName);

                    SecurityContextHolder.getContext().setAuthentication(
                            new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities())
                    );
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
