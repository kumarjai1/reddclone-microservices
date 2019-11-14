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
    public Comment createComment(@RequestHeader("userId") Long userId, Long postId, @RequestBody Comment comment) {
        return commentService.createComment(userId, postId, comment);
    }
}
