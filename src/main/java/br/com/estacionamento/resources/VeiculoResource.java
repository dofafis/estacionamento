package br.com.estacionamento.resources;

import br.com.estacionamento.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class VeiculoResource {

    @Autowired
    VeiculoService veiculoService;

}
