package com.itriad.teste.estacionamento.exceptions;

import lombok.Data;

@Data
public class DataEntradaInvalida {
    private String message;
    private Long code;

    public DataEntradaInvalida() {
        this.message = "A hora de entrada é inválida.";
        this.code = 0L;
    }
}
