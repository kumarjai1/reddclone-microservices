package com.ga.commentmicroservice.model;


import javax.persistence.*;
/***
 * Creates a Comment Schema in the database
 * Comment composes of the following properties:
 * Commentid- Long,
 * Text- String,
 * Username- String
 * @author      Hristina Lapanova (hristina.lapanova@gmail.com), Jai Kumar (jai.essarani@gmail.com)
 * @version     0.01                 (current version number of program)
 * @since       0.01          (the version of the package this class was first added to)

 */

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    @Column(nullable = false)
    private String text;

    @Column
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    @Transient
//    UserBean user;

    public Comment() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

//    public UserBean getUser() {
//        return user;
//    }
//
//    public void setUser(UserBean user) {
//        this.user = user;
//    }
}
