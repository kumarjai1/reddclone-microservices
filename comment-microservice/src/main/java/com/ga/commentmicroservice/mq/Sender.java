package com.ga.commentmicroservice.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    public Long checkIfPostExists(Long postId) {
        System.out.println("receiving postId back from post: " + postId);
        Long res = (Long) rabbitTemplate.convertSendAndReceive("post.comment", postId);
        if (res != null) {
            return res;
        }

        System.out.println("Comment check post id exists received:" + res);
        throw new EntityNotFoundException("Post doesn't exist");
    }
}
