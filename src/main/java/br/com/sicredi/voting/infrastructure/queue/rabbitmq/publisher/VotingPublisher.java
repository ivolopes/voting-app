package br.com.sicredi.voting.infrastructure.queue.rabbitmq.publisher;

import br.com.sicredi.voting.domain.dto.request.VotingRequest;
import br.com.sicredi.voting.domain.dto.response.VotingResultResponse;
import br.com.sicredi.voting.infrastructure.queue.rabbitmq.config.RabbitmqConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VotingPublisher {

    private final AmqpTemplate amqpTemplate;

    public void publish(String sessionId, String affiliatedId, boolean vote) {
        amqpTemplate.convertAndSend(RabbitmqConstants.VOTING_EXCHANGE,
                RabbitmqConstants.VOTING_ROUTING_KEY,
                new VotingRequest(sessionId, affiliatedId, vote));
    }

    public void sendResult(VotingResultResponse result) {
        amqpTemplate.convertAndSend(RabbitmqConstants.RESULT_EXCHANGE, "", result);
    }
}
