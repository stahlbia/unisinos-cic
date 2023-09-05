package laboratorios.laboratorio2.funcionarios;

import java.text.DecimalFormat;

public class FuncionarioComissionadoBaseSalario extends FuncionarioComissionado{
    // Atributos
    private double salarioFixo;

    // Construtor
    public FuncionarioComissionadoBaseSalario(Funcionario dadosPessoais, double taxaComissao, double vendasBrutas, double salarioFixo) {
        super(dadosPessoais, taxaComissao, vendasBrutas);
        this.salarioFixo = salarioFixo;
    }

    // Métodos

    @Override
    public Double getRendimentos() {
        DecimalFormat df = new DecimalFormat("#.###,00");
        return Double.valueOf(df.format(super.getRendimentos() + salarioFixo));
    }

    // Get e Set
    public double getSalarioFixo() {
        return salarioFixo;
    }

    public void setSalarioFixo(double salarioFixo) {
        this.salarioFixo = salarioFixo;
    }
}
