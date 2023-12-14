package com.developement.crm.rabbit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMqService {

    private final AmqpTemplate amqpTemplate;


    public void send(String fila, Object object){
        this.amqpTemplate.convertAndSend(fila, object);
    }
}
