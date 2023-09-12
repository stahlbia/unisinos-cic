package laboratorios.laboratorio2.funcionarios;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;

public class FuncionarioAssalariado implements Funcionario {
    // Atributos
    private DadosPessoais dadosPessoais;
    private double salarioSemanal;

    // Construtor
    public FuncionarioAssalariado(DadosPessoais dadosPessoais, double salarioSemanal) {
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
        this.salarioSemanal = salarioSemanal;
    }

    // Métodos
    @Override
    public double getRendimentos() {
        return salarioSemanal;
    }

    // Get e Set
    @Override
    public DadosPessoais getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public void setSalarioSemanal(double salarioSemanal) {
        this.salarioSemanal = salarioSemanal;
    }
}
