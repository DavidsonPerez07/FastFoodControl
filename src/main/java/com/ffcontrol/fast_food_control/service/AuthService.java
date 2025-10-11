package com.ffcontrol.fast_food_control.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ffcontrol.fast_food_control.DTO.LoginResponse;
import com.ffcontrol.fast_food_control.DTO.UserDTO;
import com.ffcontrol.fast_food_control.DTO.UserLoginResponse;
import com.ffcontrol.fast_food_control.entity.User;
import com.ffcontrol.fast_food_control.repository.UserRepository;
import com.ffcontrol.fast_food_control.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    public LoginResponse login(String userName, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password)
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        User user = userRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
        UserLoginResponse userLoginResponse = new UserLoginResponse(user.getUserId(), user.getUserName(), user.getRole());


        return new LoginResponse(jwtUtil.generateToken(userDetails), userLoginResponse);
    }
}
