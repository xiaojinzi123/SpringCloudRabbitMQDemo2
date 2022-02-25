package com.xiaojinzi.test;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    @ResponseBody
    @RequestMapping("pushMessage")
    public String pushMessage() {
        for (int i = 1; i <= 10; i++) {
            // amqpTemplate.convertAndSend("testQueue", "testData____" + i);
            amqpTemplate.convertAndSend("testExchange", "", "testData____" + i);
        }
        return "hello, convertAndSend 'testData' success ";
    }

    @Bean
    public FanoutExchange testExchange() {
        return new FanoutExchange("testExchange");
    }

    @Bean
    public Queue testQueue() {
        return new Queue("testQueue");
    }

    @Bean
    public Binding testBinding(Queue testQueue, FanoutExchange testExchange) {
        return BindingBuilder
                .bind(testQueue)
                .to(testExchange);
    }

}