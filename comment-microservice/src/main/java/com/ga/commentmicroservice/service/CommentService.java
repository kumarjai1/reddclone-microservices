package com.ga.commentmicroservice.service;

import com.ga.commentmicroservice.model.Comment;

public interface CommentService{

    Comment createComment ( Long userId, String postId, Comment comment);

    Iterable<Comment> listComments();

    Iterable<Comment> getCommentsByUserId(Long userId);
    Iterable<Comment> getCommentsByPostId(Long postId);
    Long deleteCommentsByPostId(Long postId);

    Long deleteCommentByUser (Long userId, Long commentId);


}
