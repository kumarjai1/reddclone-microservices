package com.ga.commentmicroservice.service;

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
    public Comment createComment(String username, String postId, Comment comment) {

//        Long user_id = Long.parseLong(userId);
        Long foundPostId = Long.parseLong(postId);
        System.out.println(foundPostId);
//        comment.setUserId(userId);
//        String checkPostIdExists = (String) rabbitTemplate.convertSendAndReceive("post.comment", foundPostId);
        System.out.println(checkIfPostExists(foundPostId));
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

    private Long checkIfPostExists(Long postId) {
        System.out.println("receiving postId back from post: " + postId);
        Long res = (Long) rabbitTemplate.convertSendAndReceive("post.comment", postId);
        if (res !=null) {
            return res;
        }

        System.out.println("Comment check post id exists received:" + res);
        throw new EntityNotFoundException("Post doesn't exist");
    }


//    private Long deleteCommentsOfPostUsingRabbitMq(Long postId){
//        System.out.println("msg sending:" + postId);
//        Long res = (Long) rabbitTemplate.convertSendAndReceive("post.comment", postId);
//        System.out.println(res);
//        System.out.println("msg sent:" + postId);
////        Long postIdSent = Long.parseLong(res);
//        return res;
//    }

}
