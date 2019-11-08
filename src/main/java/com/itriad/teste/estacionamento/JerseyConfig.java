package com.itriad.teste.estacionamento;

import com.itriad.teste.estacionamento.resources.EntradaESaidaResource;
import com.itriad.teste.estacionamento.resources.PrecoResource;
import com.itriad.teste.estacionamento.resources.VeiculoResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

import static com.itriad.teste.estacionamento.JerseyConfig.API_PATH;


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

