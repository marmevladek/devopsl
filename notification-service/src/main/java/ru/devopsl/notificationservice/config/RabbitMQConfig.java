package ru.devopsl.notificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue createProductQueue() {
        return new Queue("create-product-queue");
    }

    @Bean
    public Queue updateProductQueue() {
        return new Queue("update-product-queue");
    }

    @Bean
    public Queue deleteProductQueue() {
        return new Queue("delete-product-queue");
    }

    @Bean
    public TopicExchange matchExchange() {
        return new TopicExchange("match-exchange");
    }

    @Bean
    public Binding createProductBinding(Queue createProductQueue, TopicExchange matchExchange) {
        return BindingBuilder.bind(createProductQueue)
                .to(matchExchange)
                .with("match-exchange.create-product");
    }

    @Bean
    public Binding updateProductBinding(Queue updateProductQueue, TopicExchange matchExchange) {
        return BindingBuilder.bind(updateProductQueue)
                .to(matchExchange)
                .with("match-exchange.update-product");
    }

    @Bean
    public Binding deleteProductBinding(Queue deleteProductQueue, TopicExchange matchExchange) {
        return BindingBuilder.bind(deleteProductQueue)
                .to(matchExchange)
                .with("match-exchange.delete-product");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}