package com.itriad.teste.estacionamento.exceptions;

import lombok.Data;

@Data
public class RegistrosInsuficientesParaCalcularPreco {
    private String message = "Não é possível entrar duas vezes sem sair.";
    private Long code = 0L;

    public RegistrosInsuficientesParaCalcularPreco() {}
}
