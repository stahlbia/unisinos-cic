package exerciciosAula.meuIMBD.produtoAudiovisual;

public class Filme extends ProdutoAudiovisual{
    // Atributos
    private final int duracaoMim;

    // Construtor
    public Filme(String id, String nome, String ano, String diretor, String[] principaisAtores, Generos genero, int duracaoMim) {
        super(id, nome, ano, diretor, principaisAtores, genero);
        this.duracaoMim = duracaoMim;
    }

    // Métodos

    // Get e Set
    public int getDuracaoMs() {
        return duracaoMim;
    }
}
