package com.example.postmicroservice.controller;

import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/list")
    public Iterable<Post> listAll(){
        return postService.listPosts();
    }

    @PostMapping("/")
    public Post createPost(@RequestHeader("userId") String userId, @RequestBody Post post) {
        return postService.createPost(userId, post);
    }

    @GetMapping("/{userId}")
    public Iterable<Post> listPostByUser(@PathVariable String userId) {
        return postService.listPostsByUser(userId);
    }


//    @DeleteMapping("/{postId}")
//    public Post deletePost(@PathVariable Long postId, @RequestHeader("Authorization") String tokerHeader) {
//
//        return postService.deletePost(userId, post);
//    }

//    @GetMapping("/{postId}/comment")
//    public List<Comment> allComments(@PathVariable Long postId) {
//        return postService.allComments(postId);
//    }



}
