package com.soft.dto;

import com.soft.enums.MusicianLevel;

public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private String talent;
    private String state;
    private String city;
    private String area;
    private MusicianLevel level;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getTalent() {
        return talent;
    }
    public void setTalent(String talent) {
        this.talent = talent;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
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
    public MusicianLevel getLevel() {
        return level;
    }
    public void setLevel(MusicianLevel level) {
        this.level = level;
    }    
}
