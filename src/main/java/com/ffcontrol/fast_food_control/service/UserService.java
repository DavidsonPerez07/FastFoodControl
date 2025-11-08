package com.ffcontrol.fast_food_control.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ffcontrol.fast_food_control.DTO.UserDTO;
import com.ffcontrol.fast_food_control.DTO.UserLoginResponse;
import com.ffcontrol.fast_food_control.entity.User;
import com.ffcontrol.fast_food_control.repository.UserRepository;
import com.ffcontrol.fast_food_control.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDTO createUser(UserDTO request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new RuntimeException("Username already exists");
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = convertToEntity(request);
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    public UserDTO editUser(Long userId, UserDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getUserName() != null && !request.getUserName().isEmpty()) {
            user.setUserName(request.getUserName());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword());
        }

        if (request.getRole() != null && !request.getRole().isEmpty()) {
            user.setRole(request.getRole());
        }

        user = userRepository.save(user);

        return convertToDTO(user);
    }

    public UserDTO getUserById(Long userId) {
        return convertToDTO(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(userId);
    }

    public UserLoginResponse getUserFromToken(String token) {
        String userName = jwtUtil.extractUsername(token);

        if (userName == null || userName.isEmpty()) {
            return null;
        }

        var user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserLoginResponse response = convertToLoginResponse(user);
        
        return response;
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserLoginResponse convertToLoginResponse(User user) {
        return modelMapper.map(user, UserLoginResponse.class);
    }
}
