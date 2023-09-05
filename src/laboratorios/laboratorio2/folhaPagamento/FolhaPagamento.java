package laboratorios.laboratorio2.folhaPagamento;

import laboratorios.laboratorio2.funcionarios.Funcionario;
import laboratorios.laboratorio2.funcionarios.FuncionarioAssalariado;
import laboratorios.laboratorio2.funcionarios.FuncionarioComissionado;
import laboratorios.laboratorio2.funcionarios.FuncionarioHorista;

public class FolhaPagamento {
    // Atributos
    private Funcionario[] listaFuncionarios;

    // Construtor
    public FolhaPagamento() {
    }

    public FolhaPagamento(Funcionario[] listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }

    // Métodos
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
                String salario = "R$ " + funcionario.getRendimentos();
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
                String salario = "R$ " + funcionario.getRendimentos();
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
                String salario = "R$ " + funcionario.getRendimentos();
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
                String salario = "R$ " + funcionario.getRendimentos();
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
