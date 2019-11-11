package br.com.estacionamento.repositories;

import br.com.estacionamento.models.EntradaESaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface EntradaESaidaRepository extends CrudRepository<EntradaESaida, Long>, JpaRepository<EntradaESaida, Long> {

    List<EntradaESaida> getEntradaESaidaByVeiculoId(Long idVeiculo);

    EntradaESaida getTopByTipoAndVeiculoIdOrderByIdDesc(String tipo, Long idVeiculo);

    EntradaESaida getTopByVeiculoIdOrderByIdDesc(Long idVeiculo);

    EntradaESaida getTopByTipoAndVeiculoIdAndDataHoraBeforeOrderByIdDesc(String tipo, Long idVeiculo, OffsetDateTime dataHora);

    @Query("select e from EntradaESaida e")
    List<EntradaESaida> getAll();

}
