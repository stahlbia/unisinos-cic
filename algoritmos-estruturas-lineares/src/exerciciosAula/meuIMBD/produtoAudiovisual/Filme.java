package exerciciosAula.meuIMBD.produtoAudiovisual;

import exerciciosAula.meuIMBD.streamings.Streaming;

public class Filme extends ProdutoAudiovisual{
    // Atributos
    private int duracaoMim;

    // Construtor
    public Filme(String id, String nome, String ano, String diretor, String[] principaisAtores, Generos genero, Streaming[] streamingsPresentes, int duracaoMim) {
        super(id, nome, ano, diretor, principaisAtores, genero, streamingsPresentes);
        this.duracaoMim = duracaoMim;
    }

    public Filme(String id, String nome, String ano, String diretor, String[] principaisAtores, Generos genero, Streaming[] streamingsPresentes) {
        super(id, nome, ano, diretor, principaisAtores, genero, streamingsPresentes);
    }


    // Métodos

    // Get e Set
    public int getDuracaoMs() {
        return duracaoMim;
    }
}
