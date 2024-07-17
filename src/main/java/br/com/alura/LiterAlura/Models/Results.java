package br.com.alura.LiterAlura.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Results(String title,
                      List<String> languages,
                      List<Authors> authors,
                      @JsonAlias("download_count") Long downloads) {
}
