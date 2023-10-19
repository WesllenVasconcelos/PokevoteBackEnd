package br.com.wescorp.pokevote.controller;


import br.com.wescorp.pokevote.model.Pessoa;
import br.com.wescorp.pokevote.model.Pokemon;
import br.com.wescorp.pokevote.model.Votacao;
import br.com.wescorp.pokevote.repository.PessoaRepository;
import br.com.wescorp.pokevote.repository.VotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/votacao")
public class VotationController {

    @Autowired
    VotationRepository votationRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @GetMapping
    public ResponseEntity<List<Votacao>> findAll(@RequestParam(required = false) String name){
        List<Votacao> votacaos = new ArrayList<Votacao>();

        if(name == null)
            votationRepository.findAll().forEach(votacaos::add);
        else
            votationRepository.findByTituloContaining(name).forEach(votacaos::add);
        if(votacaos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(votacaos, HttpStatus.OK);
    }

    @PostMapping("/{idPessoa}")
    public ResponseEntity<Votacao> createVotation(@PathVariable int idPessoa, @RequestBody Votacao votacao){

        Pessoa _pessoa = pessoaRepository.findById(idPessoa)
                        .orElseThrow(()->new
                                ResourceAccessException("Não há pessoa com esse "+ idPessoa));

        votacao.setPessoa(_pessoa);

        votationRepository.save(votacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(votacao);
    }







}
