//This DTO is created so that User can send his Login Information such as Email and password

package com.soft.dto;

//This is RequestDTO
public class LoginDTO {
    private String email;
    private String password;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public LoginDTO() {
        
    }
}
