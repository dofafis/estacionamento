package br.com.estacionamento.resources;

import br.com.estacionamento.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@RestController
public class VeiculoResource {

    @Autowired
    private VeiculoService veiculoService;

}
