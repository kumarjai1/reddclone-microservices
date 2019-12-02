package com.example.postmicroservice.dummyMq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues="post.comment")
public class DummyReceiver {
    private Long id;

    @RabbitHandler
    public void receive(Long id){
        this.id = id;
        System.out.println(id);
    }
    public Long getId(){
        return id;
    }
}
