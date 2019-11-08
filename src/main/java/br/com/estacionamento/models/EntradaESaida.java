package br.com.estacionamento.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Data
@Table(schema = "public", name = "entrada_e_saida")
public class EntradaESaida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo 'tipo' é obrigatório.")
    @Column(name = "tipo", length = 15, nullable = false)
    private String tipo;

    @NotNull(message = "O campo 'dataHora' no formato 'yyyy-mm-ddThh:mm:ss.mmmZ' é obrigatório")
    @Column(name = "data_hora", nullable = false)
    private Instant dataHora;

    @ManyToOne(targetEntity = Veiculo.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_veiculo")
    @NotNull(message = "É obrigatório cadastrar um veículo")
    private Veiculo veiculo;

}
