package com.ga.commentmicroservice.controller;

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

    @PostMapping("/{postId}")
    public Comment createComment(@RequestHeader("userId") Long userId, @PathVariable String postId, @RequestBody Comment comment) {
        return commentService.createComment(userId, postId, comment);
    }

    @GetMapping("/user")
    public Iterable<Comment> findCommentsByUser_id(@RequestHeader("userId") Long userId) {
        return commentService.getCommentsByUserId(userId);
    }

    @GetMapping("/{postId}")
    public Iterable<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

//    @GetMapping("/user/{userId}")
//    public Iterable<Comment> getCommentByUserId(@PathVariable Long userId)
//    {
//        return commentService.findCommentByUser_id(userId);
//    }

//
//    @GetMapping ("/{postId}")
//    public Iterable<Comment> findAllByPostId(@PathVariable long postId) {
//        return commentService.findAllByPostId(postId);
//    }
}
