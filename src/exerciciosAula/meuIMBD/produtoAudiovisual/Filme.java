package exerciciosAula.meuIMBD.produtoAudiovisual;

public class Filme extends ProdutoAudiovisual{
    // Atributos
    private int duracao_ms;

    // Construtor
    public Filme(String id, String nome, String ano, String diretor, String[] principaisAtores, String genero, int duracao_ms) {
        super(id, nome, ano, diretor, principaisAtores, genero);
        this.duracao_ms = duracao_ms;
    }

    // Métodos

    // Get e Set
}
