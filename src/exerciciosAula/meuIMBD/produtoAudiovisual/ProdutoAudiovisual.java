package exerciciosAula.meuIMBD.produtoAudiovisual;

public abstract class ProdutoAudiovisual {
    // Atributos
    private String id;
    private String nome;
    private String ano;
    private String diretor;
    private String[] principaisAtores;
    private String genero;

    // Construtor
    public ProdutoAudiovisual(String id, String nome, String ano, String diretor, String[] principaisAtores, String genero) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.diretor = diretor;
        this.principaisAtores = principaisAtores;
        this.genero = genero;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
