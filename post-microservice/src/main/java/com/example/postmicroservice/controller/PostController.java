package com.example.postmicroservice.controller;

import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags="Post Handler")
public class PostController {

    @Autowired
    PostService postService;

    @ApiOperation(value="Lists all posts", produces="application/json")
    @GetMapping("/list")
    public Iterable<Post> listAll(){
        return postService.listPosts();
    }

    @ApiOperation(value="Creates a new post", produces="application/json")
    @PostMapping("/")
    public Post createPost(
            @RequestHeader("username") @ApiParam(value="Token", required = true) String username,
            @RequestBody @ApiParam(value = "Post Body", required = true) Post post

    ) {
        return postService.createPost(username, post);
    }

    @ApiIgnore
    @GetMapping
    public Iterable<Post> listPostByUser(@RequestHeader String username) {
        return postService.listPostsByUser(username);
    }

    @ApiOperation(value="Deletes a Post by PostId", produces = "Integer")
    @DeleteMapping("/{postId}")
    public Long deletePost(
            @RequestHeader("username") @ApiParam(value="Token", required = true) String username,
            @PathVariable @ApiParam(value = "Id of existing post", required=true) String postId) {
        return postService.deletePost(username, Long.valueOf(postId));
    }

//    @GetMapping("/{postId}/comment")
//    public List<Comment> allComments(@PathVariable Long postId) {
//        return postService.allComments(postId);
//    }



}
