package com.soft.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.dto.RegisterDTO;
import com.soft.repository.UserRepository;
import com.soft.service.UsersService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userrpo;
    private final UsersService uservice;

    public UserController(UserRepository userrpo,UsersService uservice) {
        this.userrpo = userrpo;
       this.uservice=uservice;
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO dto) {
    	System.out.println("REGISTER API HIT");
        if(userrpo.existsByEmail(dto.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already Exists");

        uservice.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully");
        
    }
}
