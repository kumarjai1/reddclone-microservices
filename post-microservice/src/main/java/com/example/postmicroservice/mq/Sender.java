package com.example.postmicroservice.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

//    @Autowired
//    Queue queue;
//
//    public void send (String msg) {
//
//        System.out.println("Sending message..." + msg);
//        rabbitTemplate.convertAndSend(queue.getName(), msg);
//        System.out.println("Msg sent" + msg);
//
//    }

    //Post sender to rabbitmq for the message - sending the post id
    public Long deleteCommentsOfPostUsingRabbitMq(Long postId){
        System.out.println("msg sending:" + postId);
        Long res = (Long) rabbitTemplate.convertSendAndReceive("post.comment", postId);
        System.out.println(res);
        System.out.println("msg sent:" + postId);
        return res;
    }
}
