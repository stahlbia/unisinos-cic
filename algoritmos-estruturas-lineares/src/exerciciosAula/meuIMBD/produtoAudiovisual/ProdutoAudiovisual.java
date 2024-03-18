package exerciciosAula.meuIMBD.produtoAudiovisual;

import exerciciosAula.meuIMBD.streamings.Streaming;

public abstract class ProdutoAudiovisual {
    // Atributos
    private String id;
    private String nome;
    private String ano;
    private String diretor;
    private String[] principaisAtores;
    private Generos genero;
    private Streaming[] streamingsPresentes;

    // Construtor
    protected ProdutoAudiovisual(String id, String nome, String ano, String diretor, String[] principaisAtores, Generos genero, Streaming[] streamingsPresentes) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.diretor = diretor;
        this.principaisAtores = principaisAtores;
        this.genero = genero;
        this.streamingsPresentes = streamingsPresentes;
    }

    // Get e Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String[] getPrincipaisAtores() {
        return principaisAtores;
    }

    public void setPrincipaisAtores(String[] principaisAtores) {
        this.principaisAtores = principaisAtores;
    }

    public Generos getGenero() {
        return genero;
    }

    public void setGenero(Generos genero) {
        this.genero = genero;
    }
}
