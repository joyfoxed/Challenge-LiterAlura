package br.com.alura.LiterAlura;

import br.com.alura.LiterAlura.Principal.Principal;
import br.com.alura.LiterAlura.Repository.IAutoresRepository;
import br.com.alura.LiterAlura.Repository.ILivrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired //Injeção de dependência na classe gerenciada pelo Spring
	private ILivrosRepository livrosRepository;

	@Autowired
	private IAutoresRepository autoresRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(livrosRepository,autoresRepository);
		principal.exibirMenu();
	}
}
