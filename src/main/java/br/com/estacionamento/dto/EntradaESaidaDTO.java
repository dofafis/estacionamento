package br.com.estacionamento.dto;

import br.com.estacionamento.models.EntradaESaida;
import lombok.Data;

@Data
public class EntradaESaidaDTO {

    private EntradaESaida entradaESaida;

    private Long preco;

    public EntradaESaidaDTO(EntradaESaida entradaESaida) {
        this.entradaESaida = entradaESaida;
    }

}
