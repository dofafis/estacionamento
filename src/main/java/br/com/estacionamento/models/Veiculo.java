package br.com.estacionamento.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(schema = "public", name="veiculo", uniqueConstraints = @UniqueConstraint(columnNames = {"placa"}))
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo 'placa' é obrigatório")
    @Length(message = "A placa deve ter no máximo 20 caracteres")
    @Column(name = "placa", length = 20, nullable = false)
    private String placa;

    @NotNull(message = "O campo 'cor' é obrigatório")
    @Length(message = "A cor deve ter no máximo 30 caracteres")
    @Column(name = "cor", length = 30, nullable = false)
    private String cor;

    @NotNull(message = "O campo 'modelo' é obrigatório")
    @Length(message = "O modelo deve ter no máximo 100 caracteres")
    @Column(name = "modelo", length = 100, nullable = false)
    private String modelo;

}
