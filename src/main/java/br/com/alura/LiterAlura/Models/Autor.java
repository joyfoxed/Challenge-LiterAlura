package br.com.alura.LiterAlura.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera o id incrementalmente na ordem que for enserido no banco
    private Long id;
    @Column(unique = true)
    private String nome;
    private Long anoNascimento;
    private Long anoFalecimento;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(Authors authors) {
        this.nome = authors.name();
        this.anoNascimento = authors.anoNascimento();
        this.anoFalecimento = authors.anoFalecimento();

    }

    public Autor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Livro livro) {
        this.livros.add(livro);
        livro.setAutor(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Long anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Long getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Long anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    @Override
    public String toString() {
        return  "\nNome: " + nome +
                "\nAnoNascimento: " + anoNascimento +
                "\nAnoFalecimento: " + anoFalecimento +
                "\nLivros: " + livros.stream().map(l -> l.getTitle()).collect(Collectors.toList()) +
                "\n-------------------------------------\n";
    }
}
