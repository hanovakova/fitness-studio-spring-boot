package com.fitnessstudiospringboot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String PURCHASE_QUEUE = "purchases.queue";

    public static final String PURCHASE_EVENTS_TOPIC = "purchase.events.topic";

    public static final String CONFIRMATION_QUEUE = "purchase.confirmation.queue";
    public static final String CONFIRMATION_ROUTING_KEY = "purchase.confirmed";

    public static final String CANCELLED_QUEUE = "cancelled.purchases.queue";
    public static final String CANCELLED_ROUTING_KEY = "purchase.cancelled";

    @Bean
    public Queue purchaseQueue() {
        return new Queue(PURCHASE_QUEUE, true);
    }

    @Bean
    public TopicExchange purchaseEventsTopic() {
        return new TopicExchange(PURCHASE_EVENTS_TOPIC);
    }

    @Bean
    public Queue confirmationQueue() {
        return new Queue(CONFIRMATION_QUEUE, true);
    }

    @Bean
    public Queue cancellationQueue() {
        return new Queue(CANCELLED_QUEUE, true);
    }

    @Bean
    public Binding confirmedBinding(Queue confirmationQueue, TopicExchange purchaseEventsTopic) {
        return BindingBuilder.bind(confirmationQueue)
                .to(purchaseEventsTopic)
                .with(CONFIRMATION_ROUTING_KEY);
    }

    @Bean
    public Binding cancelledBinding(Queue cancellationQueue, TopicExchange purchaseEventsTopic) {
        return BindingBuilder.bind(cancellationQueue)
                .to(purchaseEventsTopic)
                .with(CANCELLED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}