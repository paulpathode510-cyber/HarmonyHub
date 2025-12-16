/*
User List DTO is for sending the List of Other users to the specific Users

All Other User who are signed up in the application will be sent via(Pagination)
Specific amount of user per request

Response DTO
*/

package com.soft.dto;

import com.soft.enums.MusicianLevel;

public class UserListDTO {
    private String name;
    private String talent;
    private String city;
    private Integer id;
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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public MusicianLevel getLevel() {
        return level;
    }
    public void setLevel(MusicianLevel level) {
        this.level = level;
    }
    public UserListDTO(String name, String talent, String city, Integer id, MusicianLevel level) {
        this.name = name;
        this.talent = talent;
        this.city = city;
        this.id = id;
        this.level = level;
    }
    public UserListDTO() {
    }
}
