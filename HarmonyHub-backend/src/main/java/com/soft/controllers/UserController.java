/*This Controller class is for providing essential and required endpoints for the User Related services only User who has Role("USER") can
Access this this class provids the direct endpoint for login, registration, update profile and fetching the all users list with some parameter
such as Filtering by city and talent and Pageable object arguments like pageNumber and pageSize */

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

    //Variable for accessing User-Service class Methods
    private final UsersService uservice;

    //Variable for accessing JWT-Service class methods
    private final JwtService jwtService;


    //Constructor Ingetion
    public UserController(UsersService uservice,JwtService jwtService) {
       this.uservice=uservice;
       this.jwtService=jwtService;
    }

    //This is the Endpoint for Registration for new User (This is the public Endpoint User and Admin can access it without jwt Tokem)
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO dto) {
    	
        RegisterResponseDTO response = uservice.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
    }

    //This is the Endpoint for Logging-In for old User (This is the public Endpoint User and Admin can access it without jwt Tokem)
    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO dto) {
        RegisterResponseDTO response = uservice.loginRequest(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //This Endpoint is for fetching all the other Users with their basic details, it also includes page number pageSize and findbycity and 
    //findbycity option SIMPLY this method contains PAGINATION concept
    @GetMapping("/getUsersList") 
    public ResponseEntity<Page<UserListDTO>> getUsersList(@RequestParam(required = false) String talent,
                                                @RequestParam(required = false) String city,
                                                @RequestParam(defaultValue = "0") int Page,
                                                @RequestParam(defaultValue = "10") int size) {
                                                    Pageable pageable=PageRequest.of(Page, size);
                                                    return ResponseEntity.ok(uservice.findPage(talent, city, pageable));
    }

    //This Endpoiint allows user to update their profile by changing their details
    @PutMapping("/update-user")
    public ResponseEntity<?> updateUserDetail(@RequestHeader("Authorization") String authHead,@RequestBody UpdateProfileDTO dto) {
        String token=authHead.substring(7);
        String jwtemail = jwtService.extractUsername(token);
        return ResponseEntity.ok(uservice.updateUserProfile(jwtemail, dto));
    }


}
