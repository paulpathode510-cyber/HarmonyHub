package com.soft.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.soft.dto.RegisterDTO;
import com.soft.entity.User;
import com.soft.repository.UserRepository;

@Service
public class UsersService {

    private final UserRepository userrepo;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UserRepository userrepo,PasswordEncoder passwordEncoder) {
        this.userrepo = userrepo;
        this.passwordEncoder=passwordEncoder;
    }

    public User registerUser(RegisterDTO dto) {
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
        return userrepo.save(user);
    }
}
