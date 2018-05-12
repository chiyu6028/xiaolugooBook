package com.xiaolugoo.webapp.elasticsearch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @Auther: ALEX
 * @Date: 2018/5/12 10:27
 * @Description:1.容器绑定接收类和方法 , 2创建监听容器
 */
@Configuration
public class RedisChannel {

    /*
    * 监听器绑定接收类和方法
    * */
    @Bean
    MessageListenerAdapter listenerAdapter(IndexReceive indexReceive){
        return new MessageListenerAdapter(indexReceive,"receiveMsg");
    }

    /*
    * 创建监听容器
    * */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("elasticsearch"));
        return  container;
    }
}
