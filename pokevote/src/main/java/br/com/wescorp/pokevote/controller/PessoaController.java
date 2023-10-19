package br.com.wescorp.pokevote.controller;

import br.com.wescorp.pokevote.model.Pessoa;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private VotationRepository votationRepository;

    @GetMapping()
    public ResponseEntity<List<Pessoa>> findAll(@RequestParam(required = false) String nome){
        List<Pessoa> pessoas = new ArrayList<Pessoa>();

        if(nome == null)
            pessoaRepository.findAll().forEach(pessoas::add);
        else
            pessoaRepository.findByNomeContaining(nome).forEach(pessoas::add);

        if(pessoas.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Pessoa>> findById(@PathVariable int id){

        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        // Se o produto for encontrado.
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa);
        }

        return ResponseEntity.notFound().build();

    }


    @PostMapping
    public ResponseEntity<Pessoa> createPerson(@RequestBody Pessoa pessoa){

        if(!pessoaRepository.existsByEmail(pessoa.getEmail())){
            pessoaRepository.save(pessoa);
        }else{

            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pessoa);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePerson(@PathVariable int id, @RequestBody Pessoa pessoa){
        Pessoa _pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Not Person with id " + id));

        _pessoa.setNome(pessoa.getNome());
        _pessoa.setEmail(pessoa.getEmail());
        _pessoa.setSenha(pessoa.getSenha());

        return new ResponseEntity<>(pessoaRepository.save(_pessoa),HttpStatus.OK);
    }

    @PatchMapping("/{id}/{idVotacao}")
    public ResponseEntity<Pessoa> registerVote(@PathVariable int id, @PathVariable int idVotacao){

        Pessoa _pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new
                        ResourceAccessException("Not Person with id " + id));

        Votacao _votacao = votationRepository.findById(idVotacao)
                        .orElseThrow(() -> new
                                ResourceAccessException("Não existe Votação com esse id" + idVotacao));

        _pessoa.addVoted_votatios(_votacao);
        pessoaRepository.save(_pessoa);

        return new ResponseEntity<>(pessoaRepository.save(_pessoa),HttpStatus.OK);

    }




}
