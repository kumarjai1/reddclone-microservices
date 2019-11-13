package com.ga.commentmicroservice.service;

import com.ga.commentmicroservice.model.Comment;

public interface CommentService {

    Comment createComment(Comment comment);
    Iterable<Comment> listComments();
}