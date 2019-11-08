package br.com.estacionamento.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(schema = "public", name = "preco", uniqueConstraints = @UniqueConstraint(columnNames = {"id_entrada", "id_saida"}))
public class Preco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = EntradaESaida.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_entrada")
    @NotNull(message = "É obrigatório cadastrar uma entrada")
    private EntradaESaida entrada;

    @ManyToOne(targetEntity = EntradaESaida.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_saida")
    @NotNull(message = "É obrigatório cadastrar uma saída")
    private EntradaESaida saida;

    @ManyToOne(targetEntity = Veiculo.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_veiculo")
    @NotNull(message = "É obrigatório cadastrar um veículo")
    private Veiculo veiculo;

    @NotNull(message = "É obrigatório cadastrar um valor")
    @Column(name = "valor", nullable = false)
    private Long valor;

}
