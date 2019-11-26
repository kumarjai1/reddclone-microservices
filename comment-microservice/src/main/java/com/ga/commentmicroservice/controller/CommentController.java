package com.ga.commentmicroservice.controller;

import com.ga.commentmicroservice.model.Comment;
import com.ga.commentmicroservice.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags="Comment Handler")
public class CommentController {
    @Autowired
    CommentService commentService;

    @ApiIgnore
    @GetMapping("/hello")
    public String hello () {
        return "Hello world!!";
    }

    @ApiOperation(value="Lists all comments", produces="application/json")
    @GetMapping("/list")
    public Iterable<Comment> listComments () {
        return commentService.listComments();
    }

    /*@PostMapping("/{postId}")
    public Comment createComment(@RequestHeader("userId") Long userId, @PathVariable String postId, @RequestBody Comment comment) {
        return commentService.createComment(userId, postId, comment);
    }*/

    @ApiOperation(value="Creates a new comment", produces="application/json")
    @PostMapping("/{postId}")
    public Comment createComment(
            @RequestHeader("username") String username, @ApiParam(value="Token", required = true)
            @PathVariable String postId,
            @RequestBody @ApiParam(value = "Comment Body", required = true) Comment comment) {
        return commentService.createComment(username, postId, comment);
    }

    @ApiIgnore
    @GetMapping("/user")
    public Iterable<Comment> findCommentsByUser(@RequestHeader("username") String username) {
        return commentService.getCommentsByUser(username);
    }

    @ApiOperation(value="Lists all comments by PostId", produces="application/json")
    @GetMapping("/{postId}")
    public Iterable<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @ApiOperation(value="Deletes a Comment by CommentId", produces = "Integer")
    @DeleteMapping("/{commentId}")
    public Long deleteCommentByUser(
            @RequestHeader("username") @ApiParam(value="Token", required = true) String username,
            @PathVariable @ApiParam(value = "Id of existing post", required=true) Long commentId) {
        return commentService.deleteCommentByUser(username, commentId);
    }

    @DeleteMapping("/post/{postId}")
    public Long deleteCommentsByPostId (@PathVariable Long postId) {
        return commentService.deleteCommentsByPostId(postId);
    }
}
