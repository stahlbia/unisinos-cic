package exerciciosAula.meuIMBD.produtoAudiovisual;

public class Filme extends ProdutoAudiovisual{
    // Atributos
    private final int duracaoMs;

    // Construtor
    public Filme(String id, String nome, String ano, String diretor, String[] principaisAtores, String genero, int duracaoMs) {
        super(id, nome, ano, diretor, principaisAtores, genero);
        this.duracaoMs = duracaoMs;
    }

    // Métodos

    // Get e Set
    public int getDuracaoMs() {
        return duracaoMs;
    }
}
