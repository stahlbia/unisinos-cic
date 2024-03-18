package exerciciosAula.meuIMBD.streamings;

import exerciciosAula.meuIMBD.produtoAudiovisual.ProdutoAudiovisual;

public class Streaming {
    // Atributos
    private String nome;
    private ProdutoAudiovisual[] catalogoCompleto;
    private ProdutoAudiovisual[] maratonarFinalSemana;
    private ProdutoAudiovisual[] top10;
    private ProdutoAudiovisual[] exclusivos;

    // Construtor
    public Streaming(String nome) {
        this.nome = nome;
    }

    public Streaming(String nome, ProdutoAudiovisual[] catalogoCompleto, ProdutoAudiovisual[] maratonarFinalSemana, ProdutoAudiovisual[] top10, ProdutoAudiovisual[] exclusivos) {
        this.nome = nome;
        this.catalogoCompleto = catalogoCompleto;
        this.maratonarFinalSemana = maratonarFinalSemana;
        this.top10 = top10;
        this.exclusivos = exclusivos;
    }

    // Métodos

    // Get e Set
    public String getNome() {
        return nome;
    }
}
