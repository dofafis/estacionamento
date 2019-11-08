package com.itriad.teste.estacionamento.exceptions;

import lombok.Data;

@Data
public class SaidaInvalida {
    private String message = "Não é possível sair sem entrar antes.";
    private Long code = 0L;

    public SaidaInvalida() {}
}
