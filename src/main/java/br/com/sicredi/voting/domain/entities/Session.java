package br.com.sicredi.voting.domain.entities;

import br.com.sicredi.voting.domain.exceptions.InvalidEntityException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Getter
@EqualsAndHashCode
public class Session {
    private String id;
    private String agendaId;
    private String name;
    private Integer finishingTimeAmout;
    private LocalDateTime finishingTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Session(String agendaId, String name, Integer finishingTimeAmout) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.agendaId = agendaId;
        this.finishingTimeAmout = finishingTimeAmout;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        setFinishingTime();
        validation();
    }

    private void validation() {
        if(agendaId == null || "".equals(agendaId.trim())) {
            throw new InvalidEntityException("The agenda id is required.");
        }
    }

    private void setFinishingTime() {
        if (finishingTimeAmout == null) {
            throw new InvalidEntityException("the attribute finishingTimeAmout is required");
        } else {
            finishingTime = this.createdAt.plusMinutes(finishingTimeAmout);
        }
    }
}
