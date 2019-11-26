package com.ga.commentmicroservice.service;

import com.ga.commentmicroservice.model.Comment;

public interface CommentService{

    Comment createComment(String username, Long postId, Comment comment);

    Iterable<Comment> listComments();

    Iterable<Comment> getCommentsByUser(String username);

    Iterable<Comment> getCommentsByPostId(Long postId);

    Long deleteCommentsByPostId(Long postId);

    Long deleteCommentByUser (String username, Long commentId);


}
