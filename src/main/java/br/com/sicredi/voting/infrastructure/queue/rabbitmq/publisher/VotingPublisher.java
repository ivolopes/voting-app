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

    /**
     * Responsible for saving votes in the queue
     * @param agendaId
     * @param affiliatedId
     * @param vote
     */
    public void registerVote(String agendaId, String affiliatedId, boolean vote) {
        amqpTemplate.convertAndSend(RabbitmqConstants.VOTING_EXCHANGE,
                RabbitmqConstants.VOTING_ROUTING_KEY,
                new VotingRequest(agendaId, affiliatedId, vote));
    }

    /**
     * It sends the result to the queue
     * @param result
     */
    public void sendResult(VotingResultResponse result) {
        amqpTemplate.convertAndSend(RabbitmqConstants.RESULT_EXCHANGE, "", result);
    }
}
