package laboratorios.laboratorio2.folhaPagamento;

import laboratorios.laboratorio2.funcionarios.*;

import java.text.DecimalFormat;
import java.util.Random;

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

    public FolhaPagamento(int qtdFuncionarios, boolean gerarFuncionariosAleatorio) {
        this.listaFuncionarios = new Funcionario[qtdFuncionarios];
        if (gerarFuncionariosAleatorio) {
            Random gerador = new Random();
            for (int i = 1; i <= qtdFuncionarios; i++) {
                String n = String.valueOf(i);
                Funcionario funcionario = new Funcionario(
                        n+n+n+n+n+n+n+n,
                        "Teste" + n,
                        "daSilva" + n,
                        n+n+n + "." + n+n+n + "." + n+n+n + "-" + n+n,
                        30+i,
                        'A',
                        "funcionario" + n + "@email.com",
                        "(" + n+n + ") " + n + " " + n+n+n+n + "-" + n+n+n+n

                );
                int tipoFuncionario = gerador.nextInt(4);
                switch (tipoFuncionario) {
                    case 0 -> this.addFuncionario(new FuncionarioAssalariado(
                            funcionario,
                            300
                    ));
                    case 1 -> this.addFuncionario(new FuncionarioComissionado(
                            funcionario,
                            0.3,
                            10000
                    ));
                    case 2 -> this.addFuncionario(new FuncionarioComissionadoBaseSalario(
                            funcionario,
                            0.3,
                            10000,
                            300
                    ));
                    case 3 -> this.addFuncionario(new FuncionarioHorista(
                            funcionario,
                            10,
                            40
                    ));
                    default -> this.addFuncionario(funcionario);
                }
            }
        }
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
                double valorSalario = funcionario.getRendimentos();
                DecimalFormat df = new DecimalFormat("#,##0.00");
                String salario = df.format(valorSalario);
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
                DecimalFormat df = new DecimalFormat("##0.00");
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
                DecimalFormat df = new DecimalFormat("##0.00");
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
                DecimalFormat df = new DecimalFormat("##0.00");
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
