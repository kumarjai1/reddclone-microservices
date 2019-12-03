package com.ga.commentmicroservice.service;

import com.ga.commentmicroservice.model.Comment;
import com.ga.commentmicroservice.mq.Sender;
import com.ga.commentmicroservice.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Implements the Post Service CRUD functionality
 */
@Service
public class CommentServiceImpl implements CommentService {
    private Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    Sender sender;

    /**
     *
     * @param username passed on by the header
     * @param postId passed on by the header
     * @param comment accepted from the body
     * @return savedComment
     */
    @Override
    public Comment createComment(String username, Long postId, Comment comment) {

//        Long user_id = Long.parseLong(userId);
//        Long foundPostId = Long.parseLong(postId);
//        comment.setUserId(userId);
//        String checkPostIdExists = (String) rabbitTemplate.convertSendAndReceive("post.comment", foundPostId);
//        System.out.println(checkIfPostExists(postId));
        comment.setPostId(sender.checkIfPostExists(postId));

        comment.setUsername(username);

        Comment savedComment = commentRepository.save(comment);

        if (savedComment != null) {
            logger.info("Comment Created, By Username: "+username+" , commentId:"+savedComment.getId());
        }

        return savedComment;
    }

    /**
     *
     * @return All comments
     */
    @Override
    public Iterable<Comment> listComments() {

        return commentRepository.findAll();
    }

    /**
     *
     * @param username passed on by the header
     * @return List of comments by username
     */

    @Override
    public Iterable<Comment> getCommentsByUser(String username) {
        logger.info("Return Posts of username: "+username);
        return commentRepository.findCommentsByUsername(username);
    }

    /**
     *
     * @param postId passed on by the header
     * @return List of comments by postId
     */
    @Override
    public Iterable<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    /**
     *
     * @param postId passed on by the header
     * @return Long postId
     */

    @Override
    public Long deleteCommentsByPostId(Long postId) {
        List<Comment> comments = (List<Comment>) commentRepository.findCommentsByPostId(postId);
        commentRepository.deleteAll(comments);
        return postId;
    }

    /**
     *
     * @param username passed on by the header
     * @param commentId passed on by the path
     * @return Long commentId of deleted comment
     */

    @Override
    public Long deleteCommentByUser(String username, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        System.out.println(username + " commentId: " + commentId);
        if(comment == null) {
            logger.warn("DELETE Comment: commentId {} doesn't Exit.",commentId);
        }
        if (comment.getUsername().equals(username)) {
            commentRepository.deleteById(commentId);
        } else {
            return 0L;
        }
        return comment.getId();
    }



}
