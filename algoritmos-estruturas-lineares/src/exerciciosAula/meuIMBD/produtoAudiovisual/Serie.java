package exerciciosAula.meuIMBD.produtoAudiovisual;

public class Serie extends ProdutoAudiovisual{
    // Atributos
    int[][] temporadas;

    // Construtor
    public Serie(String id, String nome, String ano, String diretor, String[] principaisAtores, Generos genero, int[][] temporadas) {
        super(id, nome, ano, diretor, principaisAtores, genero);
        this.temporadas = temporadas;
    }

    // Métodos

    // Get e Set
    public int[][] getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int[][] temporadas) {
        this.temporadas = temporadas;
    }
}
