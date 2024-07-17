package br.com.alura.LiterAlura.Repository;

import br.com.alura.LiterAlura.Models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAutoresRepository extends JpaRepository <Autor,Long> {
    Optional<Autor> findByNomeContaining(String nomeAutor);

    @Query("select a from Autor a where a.anoNascimento <= :ano and a.anoFalecimento >= :ano")
    List<Autor> autoresVivosEmUmDeterminadoAno(int ano);
}
