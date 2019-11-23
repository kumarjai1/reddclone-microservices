package com.ga.commentmicroservice.receiver;

import com.ga.commentmicroservice.service.CommentService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues= "post.comment")
public class Receiver {

    @Autowired
    CommentService commentService;


    @RabbitListener(queuesToDeclare = @Queue ("post.comment"))
    @RabbitHandler
    public Long receive(String message) {
        System.out.println("message receving: " + message);
        Long postId = Long.parseLong(message);
        System.out.println("message received: " + message);
//        commentService.deleteCommentsByPostId(postId);
        return commentService.deleteCommentsByPostId(postId);
    }

}
