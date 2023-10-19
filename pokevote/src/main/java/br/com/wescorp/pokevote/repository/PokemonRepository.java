package br.com.wescorp.pokevote.repository;

import br.com.wescorp.pokevote.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    @Query(value = "SELECT * FROM pokemons WHERE votacao_votacao_id = :id", nativeQuery = true)
    List<Pokemon> pokemonsDaVotacao(int id);
}
