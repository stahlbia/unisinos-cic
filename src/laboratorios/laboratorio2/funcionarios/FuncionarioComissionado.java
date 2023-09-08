package laboratorios.laboratorio2.funcionarios;

import java.text.DecimalFormat;

public class FuncionarioComissionado extends Funcionario{
    // Atributos
    private double taxaComissao;
    private double vendasBrutas;

    // Construtor
    public FuncionarioComissionado(Funcionario dadosPessoais, double taxaComissao, double vendasBrutas) {
        super(
                dadosPessoais.getIdFuncionario(),
                dadosPessoais.getNome(), dadosPessoais.getSobrenome(),
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
    public String getRendimentos() {
        DecimalFormat df = new DecimalFormat("R$ #,###.00");
        return df.format(vendasBrutas * taxaComissao);
    }

    // Get e Set
}
