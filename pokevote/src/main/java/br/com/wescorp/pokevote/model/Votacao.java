package br.com.wescorp.pokevote.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Table(name = "Votacoes")
public class Votacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private int votacao_id;

    @Column(length = 100)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_inicial;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_final;

    @ManyToOne
    @JoinColumn(name = "criador_id", nullable = false)
    private Pessoa pessoa;
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
