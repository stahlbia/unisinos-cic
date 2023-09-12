package laboratorios.laboratorio2.funcionarios;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;

public class FuncionarioHorista implements Funcionario{
    // Atributos
    private DadosPessoais dadosPessoais;
    private double salarioPorHora;
    private double horasTrabalhadas;

    // Construtor
    public FuncionarioHorista(DadosPessoais dadosPessoais, double salarioPorHora, double horasTrabalhadas) {
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
        this.salarioPorHora = salarioPorHora;
        this.horasTrabalhadas = horasTrabalhadas;
    }

    // Métodos

    @Override
    public double getRendimentos() {
        if (horasTrabalhadas <= 40) {
            return horasTrabalhadas * salarioPorHora;
        }
        return 40 * salarioPorHora + (horasTrabalhadas - 40) * salarioPorHora * 1.5;
    }

    // Get e Set
    @Override
    public DadosPessoais getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public double getSalarioPorHora() {
        return salarioPorHora;
    }

    public void setSalarioPorHora(double salarioPorHora) {
        this.salarioPorHora = salarioPorHora;
    }

    public double getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(double horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }
}
