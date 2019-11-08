package com.itriad.teste.estacionamento.services;

import com.itriad.teste.estacionamento.dto.PrecoDTO;
import com.itriad.teste.estacionamento.exceptions.RegistrosInsuficientesParaCalcularPreco;
import com.itriad.teste.estacionamento.models.EntradaESaida;
import com.itriad.teste.estacionamento.models.Preco;
import com.itriad.teste.estacionamento.models.Veiculo;
import com.itriad.teste.estacionamento.repositories.PrecoRepository;
import com.itriad.teste.estacionamento.services.precos.TabelaDePrecos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrecoService {

    @Autowired
    private PrecoRepository precoRepository;

    @Autowired
    private EntradaESaidaService entradaESaidaService;

    private TabelaDePrecos tabelaDePrecos = new TabelaDePrecos();

    @Transactional
    public Object getPreco(Long idVeiculo) {

        Preco preco = new Preco();
        EntradaESaida ultimaEntrada = this.entradaESaidaService.getTopByTipoAndVeiculoIdOrderByDataHoraDesc("ENTRADA", idVeiculo), ultimaSaida = null;

        if(ultimaEntrada != null)
            ultimaSaida = this.entradaESaidaService.getTopByTipoAndVeiculoIdOrderByDataHoraDesc("SAIDA", idVeiculo);

        if(ultimaEntrada != null && ultimaSaida != null && ultimaEntrada.getDataHora().isBefore(ultimaSaida.getDataHora())){
            preco.setEntrada(ultimaEntrada);
            preco.setSaida(ultimaSaida);
            preco.setVeiculo(new Veiculo());
            preco.getVeiculo().setId(idVeiculo);
            preco.setValor(this.tabelaDePrecos.getValor(ultimaEntrada.getDataHora(), ultimaSaida.getDataHora()));

            Preco precoAux = this.precoRepository.getByEntradaIdAndSaidaId(ultimaEntrada.getId(), ultimaSaida.getId());
            if(precoAux == null)
                return this.precoRepository.save(preco);
            else
                return precoAux;
        }else
            return new RegistrosInsuficientesParaCalcularPreco();

    }

    @Transactional
    public List<PrecoDTO> getRelatorio() {
        List<Preco> todosPrecos = this.precoRepository.getAll();
        Map<Instant, Long> aux = new HashMap<>();
        List<PrecoDTO> precoDTO = new ArrayList<>();

        for (Preco preco : todosPrecos) {
            Instant dia = preco.getEntrada().getDataHora().truncatedTo(ChronoUnit.DAYS);

            if(aux.containsKey(dia))
                aux.put(dia, aux.get(dia) + preco.getValor());
            else
                aux.put(dia, preco.getValor());
        }

        for (Instant dia : aux.keySet())
            precoDTO.add(new PrecoDTO(dia, aux.get(dia)));

        return precoDTO;

    }
}
