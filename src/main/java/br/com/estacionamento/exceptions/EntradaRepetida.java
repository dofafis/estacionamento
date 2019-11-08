package br.com.estacionamento.exceptions;

import lombok.Data;

@Data
public class EntradaRepetida {
    private String message = "Não é possível entrar duas vezes sem sair.";
    private Long code = 0L;

    public EntradaRepetida() {}
}
