package br.com.alura.LiterAlura.Models;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Id auto incremental
    private Long id;
    @Column(unique = true)
    private String title;

    private String nomeAutor;
    @ManyToOne()
    private Autor autor;
    private String idiomas;
    private Long downloads;

    public Livro(Dados dados) {
        this.title = dados.results().get(0).title();
        this.nomeAutor = dados.results().get(0).authors().get(0).name();
        this.idiomas = dados.results().get(0).languages().get(0);
        this.downloads = dados.results().get(0).downloads();
    }

    public Livro() {
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "\n----------LIVRO----------" +
                "\nTítulo: " + title +
                "\nAutor: " + nomeAutor +
                "\nIdioma: " + idiomas +
                "\nNúmero de downloads: " + downloads+
                "\n-----------------------";
    }
}
