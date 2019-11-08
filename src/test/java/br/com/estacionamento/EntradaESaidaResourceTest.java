package br.com.estacionamento;

import br.com.estacionamento.models.EntradaESaida;
import br.com.estacionamento.models.Veiculo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.time.Instant;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntradaESaidaResourceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void entradaSucesso() {
        EntradaESaida entradaESaida = new EntradaESaida();
        entradaESaida.setDataHora(Instant.parse("2019-11-08T13:50:00.000Z"));
        entradaESaida.setVeiculo(new Veiculo());
        entradaESaida.getVeiculo().setModelo("HB20");
        entradaESaida.getVeiculo().setCor("AZUL");
        entradaESaida.getVeiculo().setPlaca("NON2323");
        ResponseEntity<EntradaESaida> response = restTemplate.postForEntity("http://localhost:" + port + "/api/entrada", entradaESaida, EntradaESaida.class);
        assert response.getStatusCode().is2xxSuccessful();
        assert response.hasBody();
        assert response.getBody().getTipo().contains("ENTRADA");
    }

    @Test
    void entradaSemDataEHora() {
        EntradaESaida entradaESaida = new EntradaESaida();
        //entradaESaida.setDataHora(Instant.parse("2019-11-08T13:50:00.000Z"));
        entradaESaida.setVeiculo(new Veiculo());
        entradaESaida.getVeiculo().setModelo("HB20");
        entradaESaida.getVeiculo().setCor("AZUL");
        entradaESaida.getVeiculo().setPlaca("NON2323");
        ResponseEntity<Object> response = restTemplate.postForEntity("http://localhost:" + port + "/api/entrada", entradaESaida, Object.class);
        assert response.getStatusCode().is4xxClientError();
        assert response.getBody().toString().contains("O campo 'dataHora' no formato 'yyyy-mm-ddThh:mm:ss.mmmZ' é obrigatório");
    }

    @Test
    void saidaSucesso() {
        EntradaESaida entradaESaida = new EntradaESaida();
        entradaESaida.setDataHora(Instant.parse("2019-11-08T13:50:00.000Z"));
        entradaESaida.setVeiculo(new Veiculo());
        entradaESaida.getVeiculo().setModelo("HB20");
        entradaESaida.getVeiculo().setCor("AZUL");
        entradaESaida.getVeiculo().setPlaca("NON2323");
        ResponseEntity<EntradaESaida> response = restTemplate.postForEntity("http://localhost:" + port + "/api/saida", entradaESaida, EntradaESaida.class);
        assert response.getStatusCode().is2xxSuccessful();
        assert response.hasBody();
        assert response.getBody().getTipo().contains("SAIDA");
    }

    @Test
    void saidaSemDataEHora() {
        EntradaESaida entradaESaida = new EntradaESaida();
        //entradaESaida.setDataHora(Instant.parse("2019-11-08T13:50:00.000Z"));
        entradaESaida.setVeiculo(new Veiculo());
        entradaESaida.getVeiculo().setModelo("HB20");
        entradaESaida.getVeiculo().setCor("AZUL");
        entradaESaida.getVeiculo().setPlaca("NON2323");
        ResponseEntity<Object> response = restTemplate.postForEntity("http://localhost:" + port + "/api/saida", entradaESaida, Object.class);
        assert response.getStatusCode().is4xxClientError();
        assert response.getBody().toString().contains("O campo 'dataHora' no formato 'yyyy-mm-ddThh:mm:ss.mmmZ' é obrigatório");
    }

}
