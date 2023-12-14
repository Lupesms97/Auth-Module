package com.developement.crm.rabbit.config;

import constants.RabbitMqConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;


@Component
public class RabbitMqConnection {

    private static final String NOME_EXCHANGE = "amq.direct";
    private final AmqpAdmin amqpAdmin;

    public RabbitMqConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca) {
      return new Binding(fila.getName(),
              Binding.DestinationType.QUEUE,
              troca.getName(),
              fila.getName(),
              null);

    }

    @PostConstruct
    private void adicionar(){

        Queue filaLogin = this.fila(RabbitMqConstants.FILA_LOGIN);
        Queue filaValidation = this.fila(RabbitMqConstants.FILA_VALIDATION);
        Queue filaCreation = this.fila(RabbitMqConstants.FILA_CREATION);


        DirectExchange troca = this.trocaDireta();

        Binding ligacaoLogin = this.relacionamento(filaLogin, troca);
        Binding ligacaoValidation = this.relacionamento(filaValidation, troca);
        Binding ligacaoCreation = this.relacionamento(filaCreation, troca);


        this.amqpAdmin.declareQueue(filaLogin);
        this.amqpAdmin.declareQueue(filaValidation);
        this.amqpAdmin.declareQueue(filaCreation);

        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(ligacaoLogin);
        this.amqpAdmin.declareBinding(ligacaoValidation);
        this.amqpAdmin.declareBinding(ligacaoCreation);
    }

}
