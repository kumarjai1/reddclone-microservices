package com.ga.commentmicroservice.service;

import com.ga.commentmicroservice.model.Comment;
import com.ga.commentmicroservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment createComment(String userId, Comment comment) {

        Long user_id = Long.parseLong(userId);

        comment.setUser_id(user_id);

        return commentRepository.save(comment);
    }

    @Override
    public Iterable<Comment> listComments() {
        return commentRepository.findAll();
    }
}
