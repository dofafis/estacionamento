package br.com.estacionamento.repositories;

import br.com.estacionamento.models.Preco;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecoRepository extends CrudRepository<Preco, Long> {

    Preco getByEntradaIdAndSaidaId(Long idEntrada, Long idSaida);

    @Query("select p from Preco p")
    List<Preco> getAll();

}
















