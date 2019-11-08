package br.com.estacionamento.repositories;

import br.com.estacionamento.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends CrudRepository<Veiculo, Long>, JpaRepository<Veiculo, Long> {

    Veiculo getByPlaca(String placa);

    Veiculo getById(Long id);
}
