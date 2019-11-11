package br.com.estacionamento.services;

import br.com.estacionamento.exceptions.DataEntradaInvalida;
import br.com.estacionamento.exceptions.EntradaRepetida;
import br.com.estacionamento.models.EntradaESaida;
import br.com.estacionamento.repositories.EntradaESaidaRepository;
import br.com.estacionamento.services.precos.TabelaDePrecos;
import br.com.estacionamento.exceptions.SaidaInvalida;
import br.com.estacionamento.exceptions.SaidaRepetida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
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
                    .getTopByVeiculoIdOrderByIdDesc(entradaESaida.getVeiculo().getId());

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
                    .getTopByVeiculoIdOrderByIdDesc(entradaESaida.getVeiculo().getId());


            // Carro tentando sair sem entrar, sem entrar, o que não faz sentido
            if(saidaNaoRepetida == null)
                return new SaidaInvalida();
            else if(saidaNaoRepetida.getTipo().equals("SAIDA"))
                return new SaidaRepetida();
            else {
                entradaESaida = this.entradaESaidaRepository.save(entradaESaida);
                precoService.getPreco(entradaESaida.getVeiculo().getPlaca());
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
    public EntradaESaida getTopByTipoAndVeiculoIdOrderByDataHoraAsc(String tipo, Long idVeiculo) {
        return this.entradaESaidaRepository.getTopByTipoAndVeiculoIdOrderByIdDesc(tipo, idVeiculo);
    }

    @Transactional
    public EntradaESaida getTopByTipoAndVeiculoIdAndDataHoraAfterOrderByDataHoraAsc(String tipo, Long idVeiculo, OffsetDateTime dataHora) {
        return this.entradaESaidaRepository.getTopByTipoAndVeiculoIdAndDataHoraBeforeOrderByIdDesc(tipo, idVeiculo, dataHora);
    }

}
