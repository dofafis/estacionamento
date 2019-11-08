package com.itriad.teste.estacionamento.repositories;

import com.itriad.teste.estacionamento.models.EntradaESaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EntradaESaidaRepository extends CrudRepository<EntradaESaida, Long>, JpaRepository<EntradaESaida, Long> {

    List<EntradaESaida> getEntradaESaidaByVeiculoId(Long idVeiculo);

    EntradaESaida getTopByTipoAndVeiculoIdOrderByDataHoraDesc(String tipo, Long idVeiculo);

    EntradaESaida getTopByVeiculoIdOrderByDataHoraDesc(Long idVeiculo);

    EntradaESaida getTopByTipoAndVeiculoIdAndDataHoraBeforeOrderByDataHoraDesc(String tipo, Long idVeiculo, Instant dataHora);

    @Query("select e from EntradaESaida e")
    List<EntradaESaida> getAll();

}
