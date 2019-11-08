package br.com.estacionamento.resources;

import br.com.estacionamento.models.EntradaESaida;
import br.com.estacionamento.services.EntradaESaidaService;
import br.com.estacionamento.services.VeiculoService;
import br.com.estacionamento.services.precos.TabelaDePrecos;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

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
