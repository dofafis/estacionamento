package com.itriad.teste.estacionamento.resources;

import com.itriad.teste.estacionamento.models.EntradaESaida;
import com.itriad.teste.estacionamento.models.Veiculo;
import com.itriad.teste.estacionamento.services.EntradaESaidaService;
import com.itriad.teste.estacionamento.services.VeiculoService;
import com.itriad.teste.estacionamento.services.precos.TabelaDePrecos;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.time.ZoneId;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class EntradaESaidaResource {

    @Autowired
    VeiculoService veiculoService;

    @Autowired
    EntradaESaidaService entradaESaidaService;

    private TabelaDePrecos tabelaDePrecos = new TabelaDePrecos();

    @POST
    @Path("/entrada")
    @Produces(MediaType.APPLICATION_JSON)
    public void entrada(@Suspended AsyncResponse asyncResponse, EntradaESaida entradaESaida) {
        entradaESaida.setVeiculo(this.veiculoService.save(entradaESaida.getVeiculo()));

        entradaESaida.setTipo("ENTRADA");

        asyncResponse.resume(this.entradaESaidaService.save(entradaESaida));

    }

    @POST
    @Path("/saida")
    @Produces(MediaType.APPLICATION_JSON)
    public void saida(@Suspended AsyncResponse asyncResponse, EntradaESaida entradaESaida) {

        entradaESaida.setVeiculo(this.veiculoService.save(entradaESaida.getVeiculo()));

        entradaESaida.setTipo("SAIDA");

        asyncResponse.resume(this.entradaESaidaService.save(entradaESaida));

    }
}
