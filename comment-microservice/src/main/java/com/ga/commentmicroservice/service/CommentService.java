package com.ga.commentmicroservice.service;

import com.ga.commentmicroservice.model.Comment;

public interface CommentService {

    Comment createComment ( Long userId, Long postId, Comment comment);

    Iterable<Comment> listComments();

}
