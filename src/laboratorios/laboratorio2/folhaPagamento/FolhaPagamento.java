package laboratorios.laboratorio2.folhaPagamento;

import laboratorios.laboratorio2.funcionarios.Funcionario;
import laboratorios.laboratorio2.funcionarios.FuncionarioAssalariado;
import laboratorios.laboratorio2.funcionarios.FuncionarioComissionado;
import laboratorios.laboratorio2.funcionarios.FuncionarioHorista;

import java.text.DecimalFormat;

public class FolhaPagamento {
    // Atributos
    private Funcionario[] listaFuncionarios;

    // Construtor
    public FolhaPagamento(int qtdFuncionarios) {
        this.listaFuncionarios = new Funcionario[qtdFuncionarios];
    }

    public FolhaPagamento(Funcionario[] listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }

    // Métodos
    public boolean addFuncionario(Funcionario funcionario) {
        for (int i = 0; i < listaFuncionarios.length; i++) {
            if (listaFuncionarios[i] == null) {
                listaFuncionarios[i] = funcionario;
                return true;
            }
        }
        return false;
    }

    public Funcionario getFuncionarioNome(String nomeFuncionario) {
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario.getNome().equals(nomeFuncionario)) {
                return funcionario;
            }
        }
        return null;
    }

    public String getFolhaDePagamentoCompleta() {
        StringBuilder folhaPagamento = new StringBuilder("----- FOLHA DE PAGAMENTO -----\n\n");
        int aux = 1;
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario != null) {
                DecimalFormat df = new DecimalFormat("R$ #,##0.00");
                String salario = df.format(funcionario.getRendimentos());
                folhaPagamento.append(aux).append(" - ").append(funcionario.getNome()).append(" ").append(funcionario.getSobrenome()).append(": ").append(salario).append("\n");
            }
            aux++;
        }
        return folhaPagamento.toString();
    }

    public String getFolhaDePagamentoAssalariados() {
        StringBuilder folhaPagamento = new StringBuilder("----- FOLHA DE PAGAMENTO: ASSALARIADOS -----\n\n");
        int aux = 1;
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario instanceof FuncionarioAssalariado) {
                DecimalFormat df = new DecimalFormat("R$ #,##0.00");
                String salario = df.format(funcionario.getRendimentos());
                folhaPagamento.append(aux).append(" - ").append(funcionario.getNome()).append(" ").append(funcionario.getSobrenome()).append(": ").append(salario).append("\n");
            }
            aux++;
        }
        return folhaPagamento.toString();
    }

    public String getFolhaDePagamentoComissionados() {
        StringBuilder folhaPagamento = new StringBuilder("----- FOLHA DE PAGAMENTO: COMISSIONADOS -----\n\n");
        int aux = 1;
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario instanceof FuncionarioComissionado) {
                DecimalFormat df = new DecimalFormat("R$ #,##0.00");
                String salario = df.format(funcionario.getRendimentos());
                folhaPagamento.append(aux).append(" - ").append(funcionario.getNome()).append(" ").append(funcionario.getSobrenome()).append(": ").append(salario).append("\n");
            }
            aux++;
        }
        return folhaPagamento.toString();
    }

    public String getFolhaDePagamentoHoristas() {
        StringBuilder folhaPagamento = new StringBuilder("----- FOLHA DE PAGAMENTO: HORISTAS -----\n\n");
        int aux = 1;
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario instanceof FuncionarioHorista) {
                DecimalFormat df = new DecimalFormat("R$ #,##0.00");
                String salario = df.format(funcionario.getRendimentos());
                folhaPagamento.append(aux).append(" - ").append(funcionario.getNome()).append(" ").append(funcionario.getSobrenome()).append(": ").append(salario).append("\n");
            }
            aux++;
        }
        return folhaPagamento.toString();
    }

    // Get e Set
    public Funcionario[] getListaFuncionarios() {
        return listaFuncionarios;
    }

    public void setListaFuncionarios(Funcionario[] listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }
}
