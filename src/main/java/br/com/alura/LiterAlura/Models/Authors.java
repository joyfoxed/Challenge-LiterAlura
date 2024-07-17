package br.com.alura.LiterAlura.Models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Authors(String name,
                      @JsonAlias("birth_year") Long anoNascimento,
                      @JsonAlias("death_year") Long anoFalecimento) {;
}
