package com.example.postmicroservice.controller;

import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @DeleteMapping("/{userId}/{postId}")
    public Long deletePost(@PathVariable String userId, @PathVariable Long postId) {
        return postService.deletePost(userId, postId);
    }

//    @GetMapping("/{postId}/comment")
//    public List<Comment> allComments(@PathVariable Long postId) {
//        return postService.allComments(postId);
//    }



}
