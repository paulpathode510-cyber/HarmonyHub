package com.soft.service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soft.dto.LoginDTO;
import com.soft.dto.RegisterDTO;
import com.soft.dto.RegisterResponseDTO;
import com.soft.dto.UserListDTO;
import com.soft.entity.User;
import com.soft.repository.UserRepository;

@Service
public class UsersService {

    private final UserRepository userrepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsersService(UserRepository userrepo,PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userrepo = userrepo;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }

    public RegisterResponseDTO registerUser(RegisterDTO dto) {

        if(userrepo.existsByEmail(dto.getEmail())){
            throw new IllegalStateException("Email Already Exists");
        }
        
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setArea(dto.getArea());
        user.setCity(dto.getCity());    
        user.setLevel(dto.getLevel());
        user.setState(dto.getState());
        user.setTalent(dto.getTalent());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User response= userrepo.save(user);
        return mapToResponseDTO(response);
    }


    public RegisterResponseDTO loginRequest(LoginDTO dto) {
        User user= userrepo.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("Email Not Registered"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credentials Do Not match");
        }

        return mapToResponseDTO(user);
    }
    
    private RegisterResponseDTO mapToResponseDTO(User user) {

        String token = jwtService.generatToken(user);

        RegisterResponseDTO dto = new RegisterResponseDTO();
        dto.setArea(user.getArea());
        dto.setCity(user.getCity());
        dto.setEmail(user.getEmail());
        dto.setLevel(user.getLevel());
        dto.setName(user.getName());
        dto.setTalent(user.getTalent());
        dto.setState(user.getState());
        dto.setToken(token);
        return dto;
    }


    public Page<UserListDTO> findPage(String talent,String city,Pageable pageable) {
        return userrepo.findUser(talent, city, pageable).map((user)->mapToFindPage(user));
    }

    private UserListDTO mapToFindPage(User user) {
        UserListDTO dto = new UserListDTO();
        dto.setCity(user.getCity());
        dto.setId(user.getId());
        dto.setLevel(user.getLevel());
        dto.setName(user.getName());
        dto.setTalent(user.getTalent());
        return dto;
    }
}
