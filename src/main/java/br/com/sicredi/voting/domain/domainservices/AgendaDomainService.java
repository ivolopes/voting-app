package br.com.sicredi.voting.domain.domainservices;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.entities.Agenda;
import br.com.sicredi.voting.domain.exceptions.AlreadyExistsException;
import br.com.sicredi.voting.domain.repository.AgendaRepository;
import br.com.sicredi.voting.domain.services.AgendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class AgendaDomainService implements AgendaService {

    private final AgendaRepository repository;
    @Value("${app.session-finishing-time}")
    private Integer sessionFinishingTime;

    public Mono<EntityCreatedResponse> save(String name) {
        log.info("Starting to save a voting agenda with name [{}]", name);

        Agenda agenda = new Agenda(name, sessionFinishingTime);

        Mono<Boolean> existsAgenda = repository.existsByName(name);

        return existsAgenda.flatMap(exists -> {
            if (exists) {
                log.error("Already exists an agenda with name equal to [{}]", name);
                return Mono.error(new AlreadyExistsException("Voting agenda"));
            } else {
                Mono<Agenda> savedAgenda = repository.save(agenda);
                log.info("Voting agenda with name [{}] saved successfully ", name);
                return savedAgenda.map(sa -> new EntityCreatedResponse(sa.getId()));
            }
        });

    }

    public Flux<Agenda> findByAccountedAndFinishingTime(boolean accounted, LocalDateTime date){
        return repository.findAllByAccountedAndFinishingTime(accounted, date);
    }

}
