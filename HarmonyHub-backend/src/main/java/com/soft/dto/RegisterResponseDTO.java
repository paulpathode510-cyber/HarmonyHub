package com.soft.dto;

import com.soft.enums.MusicianLevel;

public class RegisterResponseDTO {
    private String name;
    private String email;
    private String talent;
    private String city;
    private String area;
    private String state;
    private MusicianLevel level;
    private String token;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTalent() {
        return talent;
    }
    public void setTalent(String talent) {
        this.talent = talent;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public MusicianLevel getLevel() {
        return level;
    }
    public void setLevel(MusicianLevel level) {
        this.level = level;
    }
    public RegisterResponseDTO(String name, String email, String talent, String city, String area, String state,
            MusicianLevel level,String token) {
        this.name = name;
        this.email = email;
        this.talent = talent;
        this.city = city;
        this.area = area;
        this.state = state;
        this.level = level;
        this.token=token;
    }
    public RegisterResponseDTO() {
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
