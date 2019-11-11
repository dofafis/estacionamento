package br.com.estacionamento;

import br.com.estacionamento.models.EntradaESaida;
import br.com.estacionamento.models.Veiculo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.OffsetDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntradaESaidaResourceTest {

    @LocalServerPort
    private int port;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    private TestRestTemplate restTemplate;

    @PostConstruct
    public void initialize() {
        RestTemplate customTemplate = restTemplateBuilder
                .rootUri("http://localhost:"+this.port)
                .build();
        this.restTemplate = new TestRestTemplate(customTemplate,
                null, null, //I don't use basic auth, if you do you can set user, pass here
                TestRestTemplate.HttpClientOption.ENABLE_COOKIES); // I needed cookie support in this particular test, you may not have this need
    }

    @Test
    public void entradaSucesso() {
        EntradaESaida entradaESaida = new EntradaESaida();
        entradaESaida.setDataHora(OffsetDateTime.parse("2019-11-08T13:50:00.000Z"));
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
    public void entradaSemDataEHora() {
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
    public void saidaSucesso() {
        EntradaESaida entradaESaida = new EntradaESaida();
        entradaESaida.setDataHora(OffsetDateTime.parse("2019-11-08T13:50:00.000Z"));
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
    public void saidaSemDataEHora() {
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
