package com.ga.commentmicroservice;

import com.ga.commentmicroservice.model.Comment;
import com.ga.commentmicroservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class CommentMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentMicroserviceApplication.class, args);
    }

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

    @PostMapping
    public Comment createComment(@RequestHeader("userId") String userId, @RequestBody Comment comment) {
        return commentService.createComment(userId, comment);
    }

}
