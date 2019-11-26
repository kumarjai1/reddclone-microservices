package com.ga.commentmicroservice.service;

import com.ga.commentmicroservice.exception.EntityNotFound;
import com.ga.commentmicroservice.model.Comment;

public interface CommentService{

    Comment createComment(String username, String postId, Comment comment) throws EntityNotFound;

    Iterable<Comment> listComments();

    Iterable<Comment> getCommentsByUser(String username);

    Iterable<Comment> getCommentsByPostId(Long postId);

    Long deleteCommentsByPostId(Long postId);

    Long deleteCommentByUser (String username, Long commentId);


}
