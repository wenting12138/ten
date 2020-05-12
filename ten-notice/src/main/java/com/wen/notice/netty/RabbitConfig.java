package com.wen.notice.netty;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean("sysNoticeContainer")
    public SimpleMessageListenerContainer sysCreate(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer c = new SimpleMessageListenerContainer(connectionFactory);
        // 使用channel监听
        c.setExposeListenerChannel(true);
        // 设置自己的监听器
        c.setMessageListener(new SysNoticeListener());
        return c;
    }

    @Bean("userNoticeContainer")
    public SimpleMessageListenerContainer createUser(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer c = new SimpleMessageListenerContainer(connectionFactory);
        // 使用channel监听
        c.setExposeListenerChannel(true);
        // 设置自己的监听器
        c.setMessageListener(new UserNoticeListener());
        return c;
    }

}
