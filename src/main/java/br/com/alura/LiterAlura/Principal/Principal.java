package br.com.alura.LiterAlura.Principal;

import br.com.alura.LiterAlura.Models.Autor;
import br.com.alura.LiterAlura.Models.Dados;
import br.com.alura.LiterAlura.Models.Livro;
import br.com.alura.LiterAlura.Repository.IAutoresRepository;
import br.com.alura.LiterAlura.Repository.ILivrosRepository;
import br.com.alura.LiterAlura.Service.ConsumoApi;
import br.com.alura.LiterAlura.Service.ConverteDados;

import java.util.*;

public class Principal {

    private Scanner scanner= new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private ILivrosRepository livrosRepository;
    private IAutoresRepository autoresRepository;
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();
    private String menu = "\n-----------------\n"
            +"Escolha o número de sua opção:\n"
            +"1 - Buscas livro pelo título.\n"
            +"2 - Listar livros registrados.\n"
            +"3 - Listar Autores registrados.\n"
            +"4 - Listar livros em um determinado idioma.\n"
            +"5 - Listar Autores vivos em um determinado ano.\n"
            +"0 - Sair";

    public Principal(ILivrosRepository livrosRepository,IAutoresRepository autoresRepository) {
        this.livrosRepository = livrosRepository;
        this.autoresRepository = autoresRepository;
    }

    public void exibirMenu(){
        var opcao = -1;
        do {
            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 0:
                    System.out.println("Saindo....");
                    break;
                case 1:
                    buscarLivros();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarLivrosEmUmDeterminadoIdioma();
                    break;
                case 5:
                    listarAutoresVivosEmUmDeterminadoAno();
                    break;
                default:
                    System.out.println("Opção ínvalida! Digite uma opção valida.");
                    break;
            }

        }while (opcao!=0);
    }

    private void listarAutoresVivosEmUmDeterminadoAno() {
        System.out.println("Digite o ano que deseja pesquisar: ");
        var anoDePesquisa = scanner.nextInt();
        scanner.nextLine();
        List<Autor> autoresEncontrados = autoresRepository.autoresVivosEmUmDeterminadoAno(anoDePesquisa);
        if (autoresEncontrados.size() > 0){
            autoresEncontrados.forEach(System.out::println);
        }else{
            System.out.println("Nenhum autor vivo no ano "+anoDePesquisa);
        }
    }

    private void listarLivrosEmUmDeterminadoIdioma() {
        var idiomaEcolhido = "Insira o idioma para realizar a busca\n"
                +"en - inglês\n"
                +"pt - português\n";
        System.out.println(idiomaEcolhido);

        var idiomaEscolhido = scanner.nextLine();
        List<Livro> livrosBuscados = livrosRepository.findByIdiomasContainingIgnoreCase(idiomaEscolhido);

        if (livrosBuscados.size() !=0){
            livrosBuscados.forEach(System.out::println);
        }else {
            System.out.println("Não existe livro desse idioma no nosso banco de dados!");
        }
    }

    private void listarAutoresRegistrados() {

        autores = autoresRepository.findAll();
        System.out.println("Autores: ");
        autores.stream().forEach(System.out::println);
    }

    private void listarLivrosRegistrados() {
        try {
            livros = livrosRepository.findAll();
            livros.stream().
                    sorted(Comparator.comparing(Livro::getTitle))
                    .forEach(System.out::println);
        }catch (NullPointerException e){
            System.out.println("Erro: Sem livros registrados! "+e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void buscarLivros() {
        System.out.println("Digite o nome do livro: ");
        var nomeLivro = scanner.nextLine();
        var json = consumoApi.comunicaoApi(nomeLivro);
        Dados dados = converteDados.obterDados(json, Dados.class);
        Livro livro = new Livro(dados);
        Autor autor = new Autor(dados.results().get(0).authors().get(0));
        Optional<Livro> livroBuscado = livrosRepository.findByTitleContaining(livro.getTitle());
        Optional<Autor> autorBuscado = autoresRepository.findByNomeContaining(autor.getNome());
        if (autorBuscado.isPresent()){
            var autorEncontrado = autorBuscado.get();
            autorEncontrado.setLivros(livro);
            autoresRepository.save(autorEncontrado);
        }else{
            autor.setLivros(livro);
            autoresRepository.save(autor);
        }

        if (livroBuscado.isPresent()){
            System.out.println("Livro já cadastrado no Banco");
        }else {
            livrosRepository.save(livro);
        }
        System.out.println(livro);
    }
}
