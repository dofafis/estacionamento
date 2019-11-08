package br.com.estacionamento.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PrecoDTO {
    private Instant data;
    private Long faturamento;

    public PrecoDTO(Instant data, Long faturamento) {
        this.data = data;
        this.faturamento = faturamento;
    }

    public PrecoDTO() {}
}



















