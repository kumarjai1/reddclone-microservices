package com.example.postmicroservice.controller;

import com.example.postmicroservice.exception.EntityNotFound;
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
    public Post createPost(@RequestHeader("username") String username, @RequestBody Post post) {
        return postService.createPost(username, post);
    }

    @GetMapping
    public Iterable<Post> listPostByUser(@RequestHeader String username) {
        return postService.listPostsByUser(username);
    }


    @DeleteMapping("/{postId}")
    public Long deletePost(@RequestHeader("username") String username, @PathVariable Long postId) throws EntityNotFound {
        return postService.deletePost(username, postId);
    }

//    @GetMapping("/{postId}/comment")
//    public List<Comment> allComments(@PathVariable Long postId) {
//        return postService.allComments(postId);
//    }



}
