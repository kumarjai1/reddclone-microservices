package com.example.postmicroservice.model;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class User {
    private String username;

//    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
//    private List<Post> posts;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
