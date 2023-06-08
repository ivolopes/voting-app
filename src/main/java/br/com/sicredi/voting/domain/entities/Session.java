package br.com.sicredi.voting.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Document
@Getter
@EqualsAndHashCode
public class Session {
    private String id;
    private String agendaId;
    private Integer finishingTimeAmout;
    private LocalDateTime finishingTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Session(String agendaId, Integer finishingTimeAmout) {
        this.id = UUID.randomUUID().toString();
        this.agendaId = agendaId;
        this.finishingTimeAmout = finishingTimeAmout;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.finishingTime = this.createdAt.plusMinutes(finishingTimeAmout);
    }
}
