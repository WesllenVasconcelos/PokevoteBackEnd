package br.com.wescorp.pokevote.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Pessoas")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)

    private String nome;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String senha;

    @OneToMany
    @JoinColumn(name = "votacoes_id")
    private List<Votacao> votacoes_votadas;

    public void addVoted_votatios(Votacao votacao){
        votacoes_votadas.add(votacao);
    }
}
