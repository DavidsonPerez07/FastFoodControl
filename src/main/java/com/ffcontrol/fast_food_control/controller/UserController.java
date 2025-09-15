package com.ffcontrol.fast_food_control.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ffcontrol.fast_food_control.DTO.UserDTO;
import com.ffcontrol.fast_food_control.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/edit-user")
    public ResponseEntity<UserDTO> editUser(@RequestParam Long userId, @RequestBody UserDTO request) {
        return ResponseEntity.ok(userService.editUser(userId, request));
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserDTO> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        
        return ResponseEntity.ok("User deleted successfully");
    }
}
