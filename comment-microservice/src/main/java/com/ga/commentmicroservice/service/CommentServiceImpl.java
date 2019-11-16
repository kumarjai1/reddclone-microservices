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
    public Comment createComment(Long userId, String postId, Comment comment) {

//        Long user_id = Long.parseLong(userId);
        Long foundPostId = Long.parseLong(postId);
        System.out.println(foundPostId);
        comment.setUserId(userId);
        comment.setPostId(foundPostId);

        return commentRepository.save(comment);
    }

    @Override
    public Iterable<Comment> listComments() {
        return commentRepository.findAll();
    }

    @Override
    public Iterable<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findCommentByUserId(userId);
    }

    @Override
    public Iterable<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findCommentByPostId(postId);
    }

    @Override
    public Long deleteCommentsByPostId(Long postId) {
        return commentRepository.deleteByPostId(postId);
    }

    @Override
    public Long deleteCommentByUser(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment.getUserId() == userId ) {
            commentRepository.delete(comment);
        }
        return comment.getId();
    }


}
