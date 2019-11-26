package com.ga.commentmicroservice.controller;

import com.ga.commentmicroservice.exception.EntityNotFound;
import com.ga.commentmicroservice.model.Comment;
import com.ga.commentmicroservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/hello")
    public String hello () {
        return "Hello world!!";
    }

    @GetMapping("/list")
    public Iterable<Comment> listComments () {
        return commentService.listComments();
    }

    /*@PostMapping("/{postId}")
    public Comment createComment(@RequestHeader("userId") Long userId, @PathVariable String postId, @RequestBody Comment comment) {
        return commentService.createComment(userId, postId, comment);
    }*/

    @PostMapping("/{postId}")
    public Comment createComment(@RequestHeader("username") String username, @PathVariable String postId, @RequestBody Comment comment) throws EntityNotFound {
        return commentService.createComment(username, postId, comment);
    }

    @GetMapping("/user")
    public Iterable<Comment> findCommentsByUser(@RequestHeader("username") String username) {
        return commentService.getCommentsByUser(username);
    }

    @GetMapping("/{postId}")
    public Iterable<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @DeleteMapping("/{commentId}")
    public Long deleteCommentByUser(@RequestHeader("username") String username, @PathVariable Long commentId) {
        return commentService.deleteCommentByUser(username, commentId);
    }

    @DeleteMapping("/post/{postId}")
    public Long deleteCommentsByPostId (@PathVariable Long postId) {
        return commentService.deleteCommentsByPostId(postId);
    }
}
