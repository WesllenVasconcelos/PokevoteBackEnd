package br.com.wescorp.pokevote.repository;

import br.com.wescorp.pokevote.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    List<Pessoa> findByNomeContaining(String nome);

    Optional<Pessoa> findByEmail(String email);
    Boolean existsByEmail (String email);


}
