package com.neo.rabbit.my;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.addresses}")
    private String addresses;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
//    @Value("${spring.rabbitmq.queue.record.commonName}")
//    private String queueName;

    private String myFaoutExchangeName ="MyFanoutExchange";

    @Bean
    public ConnectionFactory getConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setAddresses(addresses);
        return cachingConnectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(integrationEventMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        return factory;
    }

    @Bean
    public MessageConverter integrationEventMessageConverter() {
        return new JsonMessageConverter();
    }

    @Bean
    public Queue getQueue(String queueName) {
        return new Queue(queueName, true);
    }


    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(myFaoutExchangeName);
    }


//    @Bean
//    public Binding binding(@Qualifier("getQueue") Queue queue, @Qualifier("getDirectExchange")DirectExchange directExchange) {
//        return BindingBuilder.bind(queue).to(directExchange).with("routingKey.huihu.sms.common.status");
//    }

    private Binding binding(Queue q, Exchange exchange) {
    	return BindingBuilder.bind(q).to( fanoutExchange());
    }

    @Bean
    public RabbitTemplate getRabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(getConnectionFactory());
        rabbitTemplate.setConnectionFactory(getConnectionFactory());
        rabbitTemplate.setMessageConverter(integrationEventMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        return rabbitTemplate;
    }
}
