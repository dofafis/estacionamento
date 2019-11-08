package br.com.estacionamento.services.precos;

import lombok.Data;
import java.time.LocalTime;

@Data
public class PrecoParaCalculo {

    private Long day;
    private LocalTime inicio;
    private LocalTime fim;
    private Long valor;

    PrecoParaCalculo(Long day, LocalTime inicio, LocalTime fim, Long valor) {
        this.day = day;
        this.inicio = inicio;
        this.fim = fim;
        this.valor = valor;
    }

}
