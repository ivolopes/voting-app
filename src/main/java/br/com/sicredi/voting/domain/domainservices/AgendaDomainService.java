package br.com.sicredi.voting.domain.domainservices;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.entities.Agenda;
import br.com.sicredi.voting.domain.entities.Session;
import br.com.sicredi.voting.domain.exceptions.AlreadyExistsException;
import br.com.sicredi.voting.domain.exceptions.NotFoundException;
import br.com.sicredi.voting.domain.services.AgendaService;
import br.com.sicredi.voting.domain.repository.AgendaRepository;
import br.com.sicredi.voting.domain.repository.SessionRepository;
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
    private final SessionRepository sessionRepository;
    @Value("${app.session-finishing-time}")
    private Integer sessionFinishingTime;

    public Mono<EntityCreatedResponse> save(String name) {
        log.info("Starting to save a voting agenda with name [{}]", name);

        Agenda agenda = new Agenda(name);

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

    public Flux<Session> findByAccountedAndFinishingTime(boolean accounted, LocalDateTime date){
        return sessionRepository.findAllByAccountedAndFinishingTime(accounted, date);
    }

    public Mono<EntityCreatedResponse> createSession(String agendaId, String name) {
        log.info("Creating session for agenda [{}]", agendaId);
        return repository.existsById(agendaId).flatMap(exists -> {
            if (exists) {
                Session session = new Session(agendaId, name, this.sessionFinishingTime);
                return sessionRepository.save(session).map(s -> {
                    log.info("Session for agenda [{}] created sucessfully", agendaId);
                    return new EntityCreatedResponse(s.getId());
                });
            } else {
                log.error("This voting agenda with id [{}] does not exists", agendaId);
                return Mono.error(new NotFoundException("Voting agenda"));
            }
        });
    }

}
