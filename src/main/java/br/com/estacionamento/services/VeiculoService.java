package br.com.estacionamento.services;

import br.com.estacionamento.models.Veiculo;
import br.com.estacionamento.repositories.VeiculoRepository;
import br.com.estacionamento.services.precos.TabelaDePrecos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Configurable
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional
    public Veiculo save(Veiculo veiculo) {
        System.out.println("@#@#@#@#@& 1111");
        Veiculo aux = this.veiculoRepository.getByPlaca(veiculo.getPlaca());
        System.out.println("@#@#@#@#@& 2222");

        if(aux == null)
            return this.veiculoRepository.save(veiculo);
        else {
            aux.setModelo(veiculo.getModelo());
            aux.setCor(veiculo.getCor());
            return this.veiculoRepository.save(aux);
        }
    }

}
