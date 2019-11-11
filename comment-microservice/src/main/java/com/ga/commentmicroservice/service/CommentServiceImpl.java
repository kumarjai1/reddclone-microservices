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
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Iterable<Comment> listComments() {
        return commentRepository.findAll();
    }
}
