package br.com.sicredi.voting.application.rest.v1;

import br.com.sicredi.voting.domain.dto.request.VotingRequest;
import br.com.sicredi.voting.domain.services.VotingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/voting")
@RequiredArgsConstructor
public class VotingController {
    private final VotingService service;

    @PostMapping
    public Mono<Void> save(@RequestBody @Valid VotingRequest request) {
        return service.register(request.getSessionId(), request.getAffiliatedId(), request.isVote());
    }
}
