package br.com.wescorp.pokevote.controller;

import br.com.wescorp.pokevote.model.Pokemon;
import br.com.wescorp.pokevote.model.Votacao;
import br.com.wescorp.pokevote.repository.PokemonRepository;
import br.com.wescorp.pokevote.repository.VotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private VotationRepository votationRepository;

    @GetMapping
    public ResponseEntity<List<Pokemon>> listarTodos(){

        List<Pokemon> pokemons = new ArrayList<Pokemon>();

        pokemonRepository.findAll().forEach(pokemons::add);

        if(pokemons.isEmpty())

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        else  return new ResponseEntity<>(pokemons, HttpStatus.OK);

    }

    @PostMapping("/{idVotação}")
    public ResponseEntity<Pokemon> criar(@PathVariable int idVotação, @RequestBody Pokemon pokemon){

        Votacao _votacao = votationRepository.findById(idVotação)
                        .orElseThrow(()->new
                                ResourceAccessException("Não há votação com esse id"+idVotação));
        pokemon.setVotacao(_votacao);

        pokemonRepository.save(pokemon);

        return ResponseEntity.status(HttpStatus.CREATED).body(pokemon);
    }

    //votar!!

    @PatchMapping("/{id}")
    public ResponseEntity<Pokemon> votar(@PathVariable int id){
        Pokemon _pokemon = pokemonRepository.findById(id)
                .orElseThrow(()->new ResourceAccessException("Não há pokemon com esse "+ id));

        _pokemon.setVotos(_pokemon.getVotos()+1);

        return new ResponseEntity<>(pokemonRepository.save(_pokemon),HttpStatus.OK);
    }

    @GetMapping("/{idVotacao}")

    public ResponseEntity<List<Pokemon>> listarPorVotacao(@PathVariable int idVotacao){

        List<Pokemon> pokemons = new ArrayList<Pokemon>();

        pokemonRepository.pokemonsDaVotacao(idVotacao).forEach(pokemons::add);

        if(pokemons.isEmpty())

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        else  return new ResponseEntity<>(pokemons, HttpStatus.OK);

    }




}
