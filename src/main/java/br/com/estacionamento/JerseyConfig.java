package br.com.estacionamento;

import br.com.estacionamento.resources.EntradaESaidaResource;
import br.com.estacionamento.resources.PrecoResource;
import br.com.estacionamento.resources.VeiculoResource;
import br.com.estacionamento.services.EntradaESaidaService;
import br.com.estacionamento.services.PrecoService;
import br.com.estacionamento.services.VeiculoService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

import static br.com.estacionamento.JerseyConfig.API_PATH;


@Component
@Primary
@ApplicationPath(API_PATH)
public class JerseyConfig extends ResourceConfig {

    public static final String API_PATH = "/api";

    public JerseyConfig() {

        register(EntradaESaidaResource.class);
        register(VeiculoResource.class);
        register(PrecoResource.class);

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

    }

}

