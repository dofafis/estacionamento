package br.com.estacionamento.services.precos;

import lombok.Data;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TabelaDePrecos {

    private List<PrecoParaCalculo> precoParaCalculos = new ArrayList<>();

    public TabelaDePrecos() {

        // Segunda a Sexta - de 8h às 12h - R$ 2,00
        this.precoParaCalculos.add(new PrecoParaCalculo(1L, LocalTime.of(8, 0), LocalTime.of(12, 0), 200L));
        this.precoParaCalculos.add(new PrecoParaCalculo(2L, LocalTime.of(8, 0), LocalTime.of(12, 0), 200L));
        this.precoParaCalculos.add(new PrecoParaCalculo(3L, LocalTime.of(8, 0), LocalTime.of(12, 0), 200L));
        this.precoParaCalculos.add(new PrecoParaCalculo(4L, LocalTime.of(8, 0), LocalTime.of(12, 0), 200L));
        this.precoParaCalculos.add(new PrecoParaCalculo(5L, LocalTime.of(8, 0), LocalTime.of(12, 0), 200L));

        // Segunda a Sexta - de 8h às 12h - R$ 2,00
        this.precoParaCalculos.add(new PrecoParaCalculo(1L, LocalTime.of(12, 0), LocalTime.of(18, 0), 300L));
        this.precoParaCalculos.add(new PrecoParaCalculo(2L, LocalTime.of(12, 0), LocalTime.of(18, 0), 300L));
        this.precoParaCalculos.add(new PrecoParaCalculo(3L, LocalTime.of(12, 0), LocalTime.of(18, 0), 300L));
        this.precoParaCalculos.add(new PrecoParaCalculo(4L, LocalTime.of(12, 0), LocalTime.of(18, 0), 300L));
        this.precoParaCalculos.add(new PrecoParaCalculo(5L, LocalTime.of(12, 0), LocalTime.of(18, 0), 300L));

        // Sábado e DOmingo - 8h às 18h - R$ 2,50
        this.precoParaCalculos.add(new PrecoParaCalculo(6L, LocalTime.of(8, 0), LocalTime.of(18, 0), 350L));
        this.precoParaCalculos.add(new PrecoParaCalculo(7L, LocalTime.of(8, 0), LocalTime.of(18, 0), 350L));
    }

    public List<PrecoParaCalculo> getPrecoParaCalculos() {
        return this.precoParaCalculos;
    }

    public Boolean dataDeEntradaEhValida(Instant now) {
        // Se o dia da semana é igual e está no intervalo de tempo definido na tabela de preços, retorna true
        // Se não se encaixa em nenhum dos preços da tabela de preços, retorna false
        return this.precoParaCalculos.stream().anyMatch(precoParaCalculo -> (DayOfWeek.from(now.atZone(ZoneId.of("UTC"))).equals(DayOfWeek.of(precoParaCalculo.getDay().intValue()))) &&
                ((LocalTime.from(now.atZone(ZoneId.of("UTC"))).isAfter(precoParaCalculo.getInicio()) && LocalTime.from(now.atZone(ZoneId.of("UTC"))).isBefore(precoParaCalculo.getFim()))
                        || LocalTime.from(now.atZone(ZoneId.of("UTC"))).equals(precoParaCalculo.getInicio()) || LocalTime.from(now.atZone(ZoneId.of("UTC"))).equals(precoParaCalculo.getFim())));

    }

    public PrecoParaCalculo getPrecoQueSeEnquadraComADataEHora(Instant datetime) {
        return this.precoParaCalculos.stream().filter(precoParaCalculo -> (DayOfWeek.from(datetime.atZone(ZoneId.of("UTC"))).equals(DayOfWeek.of(precoParaCalculo.getDay().intValue()))) &&
                ((LocalTime.from(datetime.atZone(ZoneId.of("UTC"))).isAfter(precoParaCalculo.getInicio()) && LocalTime.from(datetime.atZone(ZoneId.of("UTC"))).isBefore(precoParaCalculo.getFim()))
                        || LocalTime.from(datetime.atZone(ZoneId.of("UTC"))).equals(precoParaCalculo.getInicio()) || LocalTime.from(datetime.atZone(ZoneId.of("UTC"))).equals(precoParaCalculo.getFim()))).findFirst().orElse(null);
    }

    public Long getValor(Instant entrada, Instant saida) {
        PrecoParaCalculo precoParaCalculoQueSeEnquadraNaEntrada = this.getPrecoQueSeEnquadraComADataEHora(entrada);
        PrecoParaCalculo precoParaCalculoQueSeEnquadraNaSaida = this.getPrecoQueSeEnquadraComADataEHora(saida);

        // Caso o período inteiro esteja na mesma faixa de preço, calcule normalmente
        if(precoParaCalculoQueSeEnquadraNaEntrada.equals(precoParaCalculoQueSeEnquadraNaSaida))
            return (long) (precoParaCalculoQueSeEnquadraNaEntrada.getValor() * (ChronoUnit.MILLIS.between(entrada, saida)/3600000.0));

        // Caso a saída tenha sido após o horário limite do dia, ou seja, fora de uma faixa de preço 18:10 por exemplo
        // O cálculo considera da entrada até o último horário válido do dia
        else if(precoParaCalculoQueSeEnquadraNaSaida == null){
            Long valor = (long) (precoParaCalculoQueSeEnquadraNaEntrada.getValor() * (ChronoUnit.MILLIS.between(LocalTime.from(entrada.atZone(ZoneId.of("UTC"))), precoParaCalculoQueSeEnquadraNaEntrada.getFim())/3600000.0));
            List<PrecoParaCalculo> listaDePrecosAposAEntrada = this.precoParaCalculos.stream()
                    .filter(
                            precoParaCalculo -> DayOfWeek.from(entrada.atZone(ZoneId.of("UTC"))).equals(DayOfWeek.of(precoParaCalculo.getDay().intValue()))
                            && precoParaCalculo.getFim().isAfter(precoParaCalculoQueSeEnquadraNaEntrada.getFim())
                    ).collect(Collectors.toList());
            for(PrecoParaCalculo precoParaCalculo : listaDePrecosAposAEntrada)
                valor += (long) (precoParaCalculo.getValor() * (ChronoUnit.MILLIS.between(precoParaCalculo.getInicio(), precoParaCalculo.getFim())/3600000.0));
            return valor;
        } else {
            Long valor = (long) (precoParaCalculoQueSeEnquadraNaEntrada.getValor() * (ChronoUnit.MILLIS.between(LocalTime.from(entrada.atZone(ZoneId.of("UTC"))), precoParaCalculoQueSeEnquadraNaEntrada.getFim())/3600000.0));

            List<PrecoParaCalculo> listaDePrecosAposAEntrada = this.precoParaCalculos.stream()
                .filter(
                        precoParaCalculo -> DayOfWeek.from(entrada.atZone(ZoneId.of("UTC"))).equals(DayOfWeek.of(precoParaCalculo.getDay().intValue()))
                    && precoParaCalculo.getFim().isAfter(precoParaCalculoQueSeEnquadraNaEntrada.getFim())
                    && precoParaCalculo.getFim().isBefore(precoParaCalculoQueSeEnquadraNaSaida.getInicio())
                ).collect(Collectors.toList());
            for (PrecoParaCalculo precoParaCalculo : listaDePrecosAposAEntrada)
                valor += (long) (precoParaCalculo.getValor() * (ChronoUnit.MILLIS.between(precoParaCalculo.getInicio(), precoParaCalculo.getFim())/3600000.0));

            valor += (long) (precoParaCalculoQueSeEnquadraNaSaida.getValor() * (ChronoUnit.MILLIS.between(precoParaCalculoQueSeEnquadraNaSaida.getInicio(), LocalTime.from(saida.atZone(ZoneId.of("UTC"))))/3600000.0));

            return valor;
        }
    }

}
