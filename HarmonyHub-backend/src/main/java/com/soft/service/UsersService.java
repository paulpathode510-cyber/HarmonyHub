//Service Layer for Users where the business logic is written

package com.soft.service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.soft.dto.LoginDTO;
import com.soft.dto.RegisterDTO;
import com.soft.dto.RegisterResponseDTO;
import com.soft.dto.UpdateProfileDTO;
import com.soft.dto.UpdateProfileResponseDTO;
import com.soft.dto.UserListDTO;
import com.soft.entity.User;
import com.soft.repository.UserRepository;

@Service
public class UsersService {


    //varibale for accessing the methods of this classes
    private final UserRepository userrepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsersService(UserRepository userrepo,PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userrepo = userrepo;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }

    //Method to Register the User
    public RegisterResponseDTO registerUser(RegisterDTO dto) {

        //Checks whether User already Registered via email
        if(userrepo.existsByEmail(dto.getEmail())){
            throw new IllegalStateException("Email Already Exists");
        }
        
        //Creating empty User object
        User user = new User();

        //Setting values to it using dto via setter methods
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setArea(dto.getArea());
        user.setCity(dto.getCity());    
        user.setLevel(dto.getLevel());
        user.setState(dto.getState());
        user.setTalent(dto.getTalent());

        //Implicityly setting the role to user
        user.setRole("USER");
        //Hashing the password before saving to the database
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User response= userrepo.save(user);

        //Converting the entity to USERDTO for safe response
        return mapToResponseDTO(response);
    }


    //Method to handle Login Logic
    public RegisterResponseDTO loginRequest(LoginDTO dto) {

        //If email not registered then throw error
        User user= userrepo.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("Email Not Registered"));

        //If registered then Encode the password from database and match with the password sent by the user
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credentials Do Not match");
        }

        //Coverting the entity to USERDTO for safe response
        return mapToResponseDTO(user);
    }
    
    //private Method for coverting the USER ENTITY to USERDTO for safer Response
    private RegisterResponseDTO mapToResponseDTO(User user) {

        //generating String token
        String token = jwtService.generatToken(user);

        //Creating a DTO
        RegisterResponseDTO dto = new RegisterResponseDTO();

        //Setting the values to emptyDTO
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


    //Returning the Page with required attributes
    public Page<UserListDTO> findPage(String talent,String city,Pageable pageable) {

        //java 8 map method and arrow function is used
        return userrepo.findUser(talent, city, pageable).map((user)->mapToFindPage(user));
    }

    //for mapping the DTO
    private UserListDTO mapToFindPage(User user) {
        UserListDTO dto = new UserListDTO();
        dto.setCity(user.getCity());
        dto.setId(user.getId());
        dto.setLevel(user.getLevel());
        dto.setName(user.getName());
        dto.setTalent(user.getTalent());
        return dto;
    }




    public UpdateProfileResponseDTO updateUserProfile(String jwtemail,UpdateProfileDTO dto) {
        UpdateProfileResponseDTO respose = new UpdateProfileResponseDTO();
        User user = userrepo.findByEmail(jwtemail).orElseThrow(() -> new RuntimeException("Email Not Register"));
        //Database(Entiy Setup)
        user.setArea(dto.getArea());
        user.setCity(dto.getCity());
        user.setLevel(dto.getLevel());
        user.setName(dto.getName());
        user.setTalent(dto.getTalent());
        user.setState(dto.getState());
        User savedUser=userrepo.save(user);

        //Response setUp
        respose.setArea(savedUser.getArea());
        respose.setCity(savedUser.getCity());
        respose.setEmail(savedUser.getEmail());
        respose.setLevel(savedUser.getLevel());
        respose.setName(savedUser.getName());
        respose.setState(savedUser.getState());
        respose.setTalent(savedUser.getTalent());
        return respose;
    }
}
