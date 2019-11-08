package com.itriad.teste.estacionamento.dto;

import com.itriad.teste.estacionamento.models.EntradaESaida;
import lombok.Data;

@Data
public class EntradaESaidaDTO {

    private EntradaESaida entradaESaida;

    private Long preco;

    public EntradaESaidaDTO(EntradaESaida entradaESaida) {
        this.entradaESaida = entradaESaida;
    }

}
