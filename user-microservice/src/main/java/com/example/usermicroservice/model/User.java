package com.example.usermicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true, nullable = false)
    private String username;

//    @Column(unique = true, nullable = false)
    private String email;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Column(name = "password", nullable = false)
    private String password;

    public User() { }

    @ManyToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name="user_role_id", nullable= false)
    private List<UserRole> userRoles;
//    private UserRole userRole;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
//
//    public UserRole getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(UserRole userRole) {
//        this.userRole = userRole;
//    }
        public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

}

