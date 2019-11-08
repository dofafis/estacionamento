package br.com.estacionamento.exceptions;

import lombok.Data;

@Data
public class SaidaRepetida {
    private String message = "Não é possível sair sem entrar novamente.";
    private Long code = 0L;

    public SaidaRepetida() {}
}
