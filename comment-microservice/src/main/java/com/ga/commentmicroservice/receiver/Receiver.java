package com.ga.commentmicroservice.receiver;

import com.ga.commentmicroservice.service.CommentService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues= "post.comment")
public class Receiver {
    @Autowired
    CommentService commentService;

    @RabbitHandler
    public String deleteCommentsByPostId(String message){
        Long postId = Long.parseLong(message);
        return String.valueOf(commentService.deleteCommentsByPostId(postId));
    }

}
