package laboratorios.laboratorio2.funcionarios;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;

public class FuncionarioComissionado implements Funcionario{
    // Atributos
    private DadosPessoais dadosPessoais;
    private double taxaComissao;
    private double vendasBrutas;

    // Construtor
    public FuncionarioComissionado(DadosPessoais dadosPessoais, double taxaComissao, double vendasBrutas) {
        this.dadosPessoais = new DadosPessoais(
                dadosPessoais.getIdFuncionario(),
                dadosPessoais.getNome(),
                dadosPessoais.getSobrenome(),
                dadosPessoais.getCpf(),
                dadosPessoais.getIdade(),
                dadosPessoais.getSexo(),
                dadosPessoais.getEmail(),
                dadosPessoais.getTelefone()
        );
        this.taxaComissao = taxaComissao;
        this.vendasBrutas = vendasBrutas;
    }

    // Métodos
    @Override
    public double getRendimentos() {
        return vendasBrutas * taxaComissao;
    }

    // Get e Set
    @Override
    public DadosPessoais getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public double getTaxaComissao() {
        return taxaComissao;
    }

    public void setTaxaComissao(double taxaComissao) {
        this.taxaComissao = taxaComissao;
    }

    public double getVendasBrutas() {
        return vendasBrutas;
    }

    public void setVendasBrutas(double vendasBrutas) {
        this.vendasBrutas = vendasBrutas;
    }
}
