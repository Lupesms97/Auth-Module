package com.developement.crm;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableRabbit
public class BackEndCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndCrmApplication.class, args);
    }

}
