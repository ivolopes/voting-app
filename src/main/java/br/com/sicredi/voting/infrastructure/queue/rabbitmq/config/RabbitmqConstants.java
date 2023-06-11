package br.com.sicredi.voting.infrastructure.queue.rabbitmq.config;

public class RabbitmqConstants {
    public static final String VOTING_QUEUE = "voting.notification.queue";
    public static final String VOTING_QUEUE_DLQ = "voting.notification.queue.dlq";
    public static final String VOTING_EXCHANGE = "voting.notification.direct";
    public static final String VOTING_ROUTING_KEY = "voting.notification.routing";
    public static final String RESULT_PLATFORM_ONE_QUEUE = "result.platformOne.queue";
    public static final String RESULT_PLATFORM_ONE_QUEUE_DLQ = "result.platformOne.queue.dlq";
    public static final String RESULT_EXCHANGE = "result.fanout.exchange";
}
