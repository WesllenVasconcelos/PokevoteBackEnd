package br.com.wescorp.pokevote.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Pokemons")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pokemon_id;

    @Column
    private int id_api;

    @Column
    private long votos;

    @ManyToOne(optional = false)
    private Votacao votacao;

    public void setVotacao(Votacao votacao) {
        this.votacao = votacao;
    }


}
