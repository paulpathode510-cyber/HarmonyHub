package com.soft.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.dto.LoginDTO;
import com.soft.dto.RegisterDTO;
import com.soft.dto.RegisterResponseDTO;
import com.soft.service.UsersService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UsersService uservice;

    public UserController(UsersService uservice) {
       this.uservice=uservice;
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO dto) {
    	
        RegisterResponseDTO response = uservice.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
    }

    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO dto) {
        RegisterResponseDTO response = uservice.loginRequest(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
