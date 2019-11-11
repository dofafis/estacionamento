package br.com.estacionamento.dto;

import lombok.Data;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
public class PrecoDTO {
    private OffsetDateTime data;
    private Long faturamento;

    public PrecoDTO(OffsetDateTime data, Long faturamento) {
        this.data = data;
        this.faturamento = faturamento;
    }

    public PrecoDTO() {}
}



















