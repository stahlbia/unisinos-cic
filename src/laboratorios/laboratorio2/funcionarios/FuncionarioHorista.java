package laboratorios.laboratorio2.funcionarios;

import java.text.DecimalFormat;

public class FuncionarioHorista extends Funcionario{
    // Atributos
    private double salarioPorHora;
    private double horasTrabalhadas;

    // Construtor
    public FuncionarioHorista(Funcionario dadosPessoais, double salarioPorHora, double horasTrabalhadas) {
        super(
                dadosPessoais.getIdFuncionario(),
                dadosPessoais.getNome(), dadosPessoais.getSobrenome(),
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
    public Double getRendimentos() {
        DecimalFormat df = new DecimalFormat("#.###,00");
        if (horasTrabalhadas <= 40) {
            return Double.valueOf(df.format(horasTrabalhadas * salarioPorHora));
        }
        return Double.valueOf(df.format(40 * salarioPorHora + (horasTrabalhadas - 40) * salarioPorHora * 1.5));
    }

    // Get e Set
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
