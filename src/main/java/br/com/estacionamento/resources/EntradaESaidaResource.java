package br.com.estacionamento.resources;

import br.com.estacionamento.models.EntradaESaida;
import br.com.estacionamento.models.Veiculo;
import br.com.estacionamento.services.EntradaESaidaService;
import br.com.estacionamento.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Validated
public class EntradaESaidaResource {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private EntradaESaidaService entradaESaidaService;

    @POST
    @Path("/entrada")
    @Produces(MediaType.APPLICATION_JSON)
    public void entrada(@Suspended AsyncResponse asyncResponse, @Valid EntradaESaida entradaESaida) {
        Veiculo veiculo = entradaESaida.getVeiculo();
        veiculo = this.veiculoService.save(veiculo);

        entradaESaida.setVeiculo(veiculo);

        entradaESaida.setTipo("ENTRADA");

        asyncResponse.resume(this.entradaESaidaService.save(entradaESaida));

    }

    @POST
    @Path("/saida")
    @Produces(MediaType.APPLICATION_JSON)
    public void saida(@Suspended AsyncResponse asyncResponse, @Valid EntradaESaida entradaESaida) {

        entradaESaida.setVeiculo(this.veiculoService.save(entradaESaida.getVeiculo()));

        entradaESaida.setTipo("SAIDA");

        asyncResponse.resume(this.entradaESaidaService.save(entradaESaida));

    }
}
