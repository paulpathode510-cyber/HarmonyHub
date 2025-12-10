package com.soft.controllers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soft.dto.LoginDTO;
import com.soft.dto.RegisterDTO;
import com.soft.dto.RegisterResponseDTO;
import com.soft.dto.UpdateProfileDTO;
import com.soft.dto.UserListDTO;
import com.soft.service.JwtService;
import com.soft.service.UsersService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UsersService uservice;
    private final JwtService jwtService;

    public UserController(UsersService uservice,JwtService jwtService) {
       this.uservice=uservice;
       this.jwtService=jwtService;
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

    @GetMapping("/getUsersList") 
    public ResponseEntity<Page<UserListDTO>> getUsersList(@RequestParam(required = false) String talent,
                                                @RequestParam(required = false) String city,
                                                @RequestParam(defaultValue = "0") int Page,
                                                @RequestParam(defaultValue = "10") int size) {
                                                    Pageable pageable=PageRequest.of(Page, size);
                                                    return ResponseEntity.ok(uservice.findPage(talent, city, pageable));
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUserDetail(@RequestHeader("Authorization") String authHead,@RequestBody UpdateProfileDTO dto) {
        String token=authHead.substring(7);
        String jwtemail = jwtService.extractUsername(token);
        return ResponseEntity.ok(uservice.updateUserProfile(jwtemail, dto));
    }


}
