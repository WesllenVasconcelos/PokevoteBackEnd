package br.com.wescorp.pokevote.repository;

import br.com.wescorp.pokevote.model.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotationRepository extends JpaRepository<Votacao, Integer> {

    List<Votacao> findByTituloContaining (String title);
}
