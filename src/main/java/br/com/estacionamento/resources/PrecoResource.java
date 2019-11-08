package br.com.estacionamento.resources;

import br.com.estacionamento.services.PrecoService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/preco")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PrecoResource {

    @Autowired
    PrecoService precoService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public void preco(@Suspended AsyncResponse asyncResponse, @QueryParam("id_veiculo") Long idVeiculo) {
        asyncResponse.resume(this.precoService.getPreco(idVeiculo));
    }

    @GET
    @Path("/relatorio")
    @Produces(MediaType.APPLICATION_JSON)
    public void relatorio(@Suspended AsyncResponse asyncResponse) {
        asyncResponse.resume(this.precoService.getRelatorio());
    }

}
