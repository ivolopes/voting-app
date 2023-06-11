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
    private Integer finishingTimeAmount;
    private LocalDateTime finishingTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean accounted;

    public Session(String agendaId, String name, Integer finishingTimeAmount) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.agendaId = agendaId;
        this.finishingTimeAmount = finishingTimeAmount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.accounted = false;
        setFinishingTime();
        validation();
    }

    public void markAsAccounted() {
        this.accounted = true;
        this.updatedAt = LocalDateTime.now();
    }

    private void validation() {
        if(agendaId == null || "".equals(agendaId.trim())) {
            throw new InvalidEntityException("The agenda id is required.");
        }
    }

    private void setFinishingTime() {
        if (finishingTimeAmount == null) {
            throw new InvalidEntityException("the attribute finishingTimeAmout is required");
        } else {
            finishingTime = this.createdAt.plusMinutes(finishingTimeAmount);
        }
    }
}
