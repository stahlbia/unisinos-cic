package laboratorios.laboratorio2.funcionarios;

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
    public double getRendimentos() {
        return (super.getRendimentos() + salarioFixo);
    }

    // Get e Set
    public double getSalarioFixo() {
        return salarioFixo;
    }

    public void setSalarioFixo(double salarioFixo) {
        this.salarioFixo = salarioFixo;
    }
}
