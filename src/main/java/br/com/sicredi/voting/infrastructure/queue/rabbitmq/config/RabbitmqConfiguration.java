package br.com.sicredi.voting.infrastructure.queue.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

/**
 * Class used to configure the queues in RabbitMQ
 */
@Configuration
public class RabbitmqConfiguration {

    @Bean
    Queue votingNotificationQueueDlq() {
        return QueueBuilder.durable(RabbitmqConstants.VOTING_QUEUE_DLQ).build();
    }

    @Bean
    DirectExchange votingNotificationExchange() {
        return new DirectExchange(RabbitmqConstants.VOTING_EXCHANGE);
    }

    @Bean
    Queue votingNotificationQueue() {
        return QueueBuilder.durable(RabbitmqConstants.VOTING_QUEUE).withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", RabbitmqConstants.VOTING_QUEUE_DLQ)
                .build();
    }

    @Bean
    Binding votingNotificationBinding() {
        return BindingBuilder.bind(votingNotificationQueue()).to(votingNotificationExchange()).with(RabbitmqConstants.VOTING_ROUTING_KEY);
    }

    @Bean
    Queue resultPlatformOneQueue() {
        return new Queue(RabbitmqConstants.RESULT_PLATFORM_ONE_QUEUE);
    }
    @Bean
    FanoutExchange resultExchange() {
        return new FanoutExchange(RabbitmqConstants.RESULT_EXCHANGE);
    }
    @Bean
    Binding resultPlatformOneBinding() {
        return BindingBuilder.bind(resultPlatformOneQueue()).to(resultExchange());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
