package com.example.postmicroservice.model;

import javax.persistence.*;
import java.util.List;

/***
 * Creates a Post Schema in the database
 * Post composes of the following properties:
 * Postid- Long,
 * Tile- Stirng,
 * Description- String,
 * Username- String
 * @author      Hristina Lapanova (hristina.lapanova@gmail.com), Jai Kumar (jai.essarani@gmail.com)
 * @version     0.01                 (current version number of program)
 * @since       0.01          (the version of the package this class was first added to)

 */
@Entity
@Table(name = "posts")
public class Post {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Post() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
