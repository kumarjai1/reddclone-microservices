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


//    receiving postid from the post microservice to delete comments by postid
    @RabbitHandler
    public String deleteCommentsByPostId(String message){
        System.out.println("msg received " + message);
        Long postId = Long.parseLong(message);
        System.out.println("msg received " + message);
        return String.valueOf(commentService.deleteCommentsByPostId(postId));
    }

//
////    @RabbitListener(queuesToDeclare = @Queue ("post.comment"))
//    @RabbitHandler
//    public void receive(String message) {
//        System.out.println("message receving: " + message);
//        Long postId = Long.parseLong(message);
//        System.out.println("message received: " + message);
////        commentService.deleteCommentsByPostId(postId);
//        commentService.deleteCommentsByPostId(postId);
//    }

}
