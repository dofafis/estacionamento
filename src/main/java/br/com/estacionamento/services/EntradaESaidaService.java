package br.com.estacionamento.services;

import br.com.estacionamento.exceptions.DataEntradaInvalida;
import br.com.estacionamento.exceptions.EntradaRepetida;
import br.com.estacionamento.models.EntradaESaida;
import br.com.estacionamento.repositories.EntradaESaidaRepository;
import br.com.estacionamento.services.precos.TabelaDePrecos;
import br.com.estacionamento.exceptions.SaidaInvalida;
import br.com.estacionamento.exceptions.SaidaRepetida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Configurable
public class EntradaESaidaService {

    @Autowired
    private EntradaESaidaRepository entradaESaidaRepository;

    @Autowired
    private PrecoService precoService;

    private TabelaDePrecos tabelaDePrecos = new TabelaDePrecos();

    @Transactional
    public Object save(EntradaESaida entradaESaida) {
        if(entradaESaida.getTipo().equals("ENTRADA")){
            EntradaESaida entradaNaoRepetida = this.entradaESaidaRepository
                    .getTopByVeiculoIdOrderByDataHoraDesc(entradaESaida.getVeiculo().getId());
            // Carro entrando duas vezes, sem sair, o que não faz sentido
            if(entradaNaoRepetida != null && entradaNaoRepetida.getTipo().equals("ENTRADA"))
                return new EntradaRepetida();
            else {
                Boolean dataEntradaEhValida = this.tabelaDePrecos.dataDeEntradaEhValida(entradaESaida.getDataHora());
                if(dataEntradaEhValida)
                    return this.entradaESaidaRepository.save(entradaESaida);
                else
                    return new DataEntradaInvalida();
            }
        }else {
            EntradaESaida saidaNaoRepetida = this.entradaESaidaRepository
                    .getTopByVeiculoIdOrderByDataHoraDesc(entradaESaida.getVeiculo().getId());

            // Carro tentando sair sem entrar, sem entrar, o que não faz sentido
            if(saidaNaoRepetida == null)
                return new SaidaInvalida();
            else if(saidaNaoRepetida.getTipo().equals("SAIDA"))
                return new SaidaRepetida();
            else {
                entradaESaida = this.entradaESaidaRepository.save(entradaESaida);
                precoService.getPreco(entradaESaida.getVeiculo().getId());
                return entradaESaida;
            }
        }
    }

    @Transactional
    public void delete(EntradaESaida entradaESaida) {
        this.entradaESaidaRepository.delete(entradaESaida);
    }

    @Transactional
    public List<EntradaESaida> getEntradasESaidasByVeiculo(Long veiculoId) {
        return this.entradaESaidaRepository.getEntradaESaidaByVeiculoId(veiculoId);
    }

    @Transactional
    public List<EntradaESaida> getAll() {
        return this.entradaESaidaRepository.getAll();
    }

    @Transactional
    public EntradaESaida getTopByTipoAndVeiculoIdOrderByDataHoraDesc(String tipo, Long idVeiculo) {
        return this.entradaESaidaRepository.getTopByTipoAndVeiculoIdOrderByDataHoraDesc(tipo, idVeiculo);
    }

    @Transactional
    public EntradaESaida getTopByTipoAndVeiculoIdAndDataHoraAfterOrderByDataHoraDesc(String tipo, Long idVeiculo, Instant dataHora) {
        return this.entradaESaidaRepository.getTopByTipoAndVeiculoIdAndDataHoraBeforeOrderByDataHoraDesc(tipo, idVeiculo, dataHora);
    }

}
