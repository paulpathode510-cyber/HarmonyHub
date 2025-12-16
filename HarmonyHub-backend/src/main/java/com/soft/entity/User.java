//This is the Users Entity All the fields which are here will be mapped to the databse(PostgraceSQL) via ORM(Hibernate)

package com.soft.entity;
import com.soft.enums.MusicianLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mucicians")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String talent;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String area;

    @Enumerated(EnumType.STRING)
    private MusicianLevel level;


    //Role Based Access
    @Column(nullable = false)
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public void setRole(String role) {
        this.role=role;
    }
    
    public String getRole() {
        return role;
    }

	public User(Integer id, String name, String email, String password, String talent, String state, String city,
			String area, MusicianLevel level, String role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.talent = talent;
		this.state = state;
		this.city = city;
		this.area = area;
		this.level = level;
		this.role = role;
	}

	public User() {
		
	}
    
	
    
}
