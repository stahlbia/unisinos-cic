package laboratorios.laboratorio2.funcionarios;

import java.text.DecimalFormat;

public class FuncionarioAssalariado extends Funcionario{
    // Atributos
    private double salarioSemanal;

    // Construtor
    public FuncionarioAssalariado(Funcionario dadosPessoais, double salarioSemanal) {
        super(
                dadosPessoais.getIdFuncionario(),
                dadosPessoais.getNome(), dadosPessoais.getSobrenome(),
                dadosPessoais.getCpf(),
                dadosPessoais.getIdade(),
                dadosPessoais.getSexo(),
                dadosPessoais.getEmail(),
                dadosPessoais.getTelefone()
        );
        this.salarioSemanal = salarioSemanal;
    }

    // Métodos
    @Override
    public Double getRendimentos() {
        DecimalFormat df = new DecimalFormat("#.###,00");
        return Double.valueOf(df.format(salarioSemanal));
    }

    // Get e Set
    public void setSalarioSemanal(double salarioSemanal) {
        this.salarioSemanal = salarioSemanal;
    }
}
