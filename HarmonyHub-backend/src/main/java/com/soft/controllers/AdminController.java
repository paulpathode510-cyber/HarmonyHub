package com.soft.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.entity.User;
import com.soft.repository.UserRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String getList() {
        // List<User> users = userRepository.findAll();
        // System.out.println("Controller Hitted");
        return "users";
    }
}
