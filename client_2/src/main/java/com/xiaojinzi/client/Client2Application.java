package com.xiaojinzi.client;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Client2Application {

    public static void main(String[] args) {
        SpringApplication.run(Client2Application.class, args);
    }

    @Bean
    public FanoutExchange testExchange() {
        return new FanoutExchange("testExchange");
    }

    @Bean
    public Queue testQueue() {
        return new Queue("testQueue2");
    }

    @Bean
    public Binding testBinding(Queue testQueue, FanoutExchange testExchange) {
        return BindingBuilder
                .bind(testQueue)
                .to(testExchange);
    }

    @RabbitListener(queues = "testQueue2")
    public void listen(String in) throws InterruptedException {
        Thread.sleep(100);
        System.out.println("client1 listen = " + in);
    }

}
