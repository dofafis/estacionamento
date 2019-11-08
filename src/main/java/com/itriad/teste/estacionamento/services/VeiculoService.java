package com.itriad.teste.estacionamento.services;

import com.itriad.teste.estacionamento.models.EntradaESaida;
import com.itriad.teste.estacionamento.models.Veiculo;
import com.itriad.teste.estacionamento.repositories.VeiculoRepository;
import com.itriad.teste.estacionamento.services.precos.TabelaDePrecos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeiculoService {

    @Autowired
    VeiculoRepository veiculoRepository;

    @Autowired
    EntradaESaidaService entradaESaidaService;

    private TabelaDePrecos tabelaDePrecos = new TabelaDePrecos();

    @Transactional
    public Veiculo save(Veiculo veiculo) {
        Veiculo aux = this.veiculoRepository.getByPlaca(veiculo.getPlaca());
        if(aux == null)
            return this.veiculoRepository.save(veiculo);
        else {
            aux.setModelo(veiculo.getModelo());
            aux.setCor(veiculo.getCor());
            return this.veiculoRepository.save(aux);
        }
    }

    @Transactional
    public void delete(Veiculo veiculo) {
        this.veiculoRepository.delete(veiculo);
    }

    @Transactional
    public Veiculo getById(Long id) {
        return this.veiculoRepository.getById(id);
    }

    @Transactional
    public Veiculo getByPlaca(String placa) {
        return this.veiculoRepository.getByPlaca(placa);
    }

}
