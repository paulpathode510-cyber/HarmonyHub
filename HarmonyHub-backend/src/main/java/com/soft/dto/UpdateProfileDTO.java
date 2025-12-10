package com.soft.dto;

import com.soft.enums.MusicianLevel;

public class UpdateProfileDTO {
    private String name;
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
    public UpdateProfileDTO(String name, String talent, String state, String city, String area, MusicianLevel level) {
        this.name = name;
        this.talent = talent;
        this.state = state;
        this.city = city;
        this.area = area;
        this.level = level;
    }
    public UpdateProfileDTO() {
    }
    
}
