package br.com.alura.LiterAlura.Repository;

import br.com.alura.LiterAlura.Models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ILivrosRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTitleContaining(String titulo);
    List<Livro> findByIdiomasContainingIgnoreCase(String idioma);
}
