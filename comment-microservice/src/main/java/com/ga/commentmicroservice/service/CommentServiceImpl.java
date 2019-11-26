package com.ga.commentmicroservice.service;

import com.ga.commentmicroservice.exception.EntityNotFound;
import com.ga.commentmicroservice.model.Comment;
import com.ga.commentmicroservice.repository.CommentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Comment createComment(String username, String postId, Comment comment) throws EntityNotFound {

//        Long user_id = Long.parseLong(userId);
        Long foundPostId = Long.parseLong(postId);
//        System.out.println(foundPostId);

//        System.out.println(checkIfPostExists(foundPostId));
        comment.setPostId(checkIfPostExists(foundPostId));

        comment.setUsername(username);

        return commentRepository.save(comment);
    }

    @Override
    public Iterable<Comment> listComments() {
        return commentRepository.findAll();
    }

    @Override
    public Iterable<Comment> getCommentsByUser(String username) {
        return commentRepository.findCommentsByUsername(username);
    }

    @Override
    public Iterable<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    @Override
    public Long deleteCommentsByPostId(Long postId) {
        List<Comment> comments = (List<Comment>) commentRepository.findCommentsByPostId(postId);
        commentRepository.deleteAll(comments);
        return postId;
    }

    @Override
    public Long deleteCommentByUser(String username, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        System.out.println(username + " commentId: " + commentId);
        if (comment.getUsername().equals(username)) {
            commentRepository.deleteById(commentId);
        } else {
            return 0L;
        }
        return comment.getId();
    }

    private Long checkIfPostExists(Long postId) throws EntityNotFound {
        System.out.println("receiving postId back from post: " + postId);
        Long res = (Long) rabbitTemplate.convertSendAndReceive("post.comment", postId);
        System.out.println("Response from + " + res);
        if (res == null) throw new EntityNotFound("Post doesn't exist");
        if (res != null) {
            return res;
        }

        System.out.println("Comment check post id exists received:" + res);
        throw new EntityNotFoundException("Post doesn't exist");
    }

}
