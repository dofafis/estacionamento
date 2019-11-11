package br.com.estacionamento.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(schema = "public", name = "entrada_e_saida")
public class EntradaESaida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo", length = 15, nullable = true)
    private String tipo;

    @NotNull(message = "O campo 'dataHora' no formato 'yyyy-mm-ddThh:mm:ss.mmmZ' é obrigatório")
    @Column(name = "data_hora", nullable = false)
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = DefaultInstantDeserializer.class)
    private OffsetDateTime dataHora;

    @ManyToOne(targetEntity = Veiculo.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_veiculo")
    @NotNull(message = "É obrigatório cadastrar um veículo")
    private Veiculo veiculo;

}
