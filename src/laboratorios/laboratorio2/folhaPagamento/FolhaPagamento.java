package laboratorios.laboratorio2.folhaPagamento;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;
import laboratorios.laboratorio2.funcionarios.*;

import java.text.DecimalFormat;
import java.util.Random;

public class FolhaPagamento {
    // Atributos
    private Funcionario[] listaFuncionarios;
    private int qtdFuncionarios;
    private final DecimalFormat df = new DecimalFormat("R$ #,##0.00");
    private final Random gerador = new Random();

    // Construtor
    public FolhaPagamento(int qtdFuncionarios) {
        this.listaFuncionarios = new Funcionario[qtdFuncionarios];
        this.qtdFuncionarios = qtdFuncionarios;
    }

    public FolhaPagamento(Funcionario[] listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }

    public FolhaPagamento(int qtdFuncionarios, boolean gerarFuncionariosAleatorio) {
        this.listaFuncionarios = new Funcionario[qtdFuncionarios];
        this.qtdFuncionarios = qtdFuncionarios;
        if (gerarFuncionariosAleatorio) {
            gerarFuncionariosAleatoriamente();
        } else {
            gerarFuncionarios();
        }
    }

    // Métodos
    public void gerarFuncionariosAleatoriamente() {
        for (int i = 1; i <= qtdFuncionarios; i++) {
            String n = String.valueOf(i);
            DadosPessoais dadosPessoais = new DadosPessoais(
                    n+n+n+n+n+n+n+n,
                    "Teste" + n,
                    "daSilva" + n,
                    n+n+n + "." + n+n+n + "." + n+n+n + "-" + n+n,
                    20+i,
                    'A',
                    "funcionario" + n + "@email.com",
                    "(" + n+n + ") " + n + " " + n+n+n+n + "-" + n+n+n+n
            );
            int tipoFuncionario = gerador.nextInt(4);
            switch (tipoFuncionario) {
                case 0 -> this.addFuncionario(new FuncionarioAssalariado(
                        dadosPessoais,
                        300
                ));
                case 1 -> this.addFuncionario(new FuncionarioComissionado(
                        dadosPessoais,
                        0.3,
                        10000
                ));
                case 2 -> this.addFuncionario(new FuncionarioComissionadoBaseSalario(
                        dadosPessoais,
                        0.3,
                        10000,
                        300
                ));
                case 3 -> this.addFuncionario(new FuncionarioHorista(
                        dadosPessoais,
                        10,
                        40
                ));
                default -> this.addFuncionario(null);
            }
        }
    }

    public void gerarFuncionarios() {
        Integer[] opcoes = {0, 1, 2, 3};
        Integer[] ordem = new Integer[qtdFuncionarios];
        int aux = 0;
        for (int j = 1; j <= qtdFuncionarios; j++) {
            ordem[j-1] = opcoes[j-1-(opcoes.length*aux)];
            if (j % 4 == 0) {
                aux += 1;
            }
        }
        for (int i = 1; i <= qtdFuncionarios; i++) {
            String n = String.valueOf(i);
            DadosPessoais dadosPessoais = new DadosPessoais(
                    n+n+n+n+n+n+n+n,
                    "Teste" + n,
                    "daSilva" + n,
                    n+n+n + "." + n+n+n + "." + n+n+n + "-" + n+n,
                    20+i,
                    'A',
                    "funcionario" + n + "@email.com",
                    "(" + n+n + ") " + n + " " + n+n+n+n + "-" + n+n+n+n
            );
            switch (ordem[i-1]) {
                case 0 -> this.addFuncionario(new FuncionarioAssalariado(
                        dadosPessoais,
                        300
                ));
                case 1 -> this.addFuncionario(new FuncionarioComissionado(
                        dadosPessoais,
                        0.3,
                        10000
                ));
                case 2 -> this.addFuncionario(new FuncionarioComissionadoBaseSalario(
                        dadosPessoais,
                        0.3,
                        10000,
                        300
                ));
                case 3 -> this.addFuncionario(new FuncionarioHorista(
                        dadosPessoais,
                        10,
                        40
                ));
                default -> this.addFuncionario(null);
            }
        }
    }

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
            if (funcionario.getDadosPessoais().getNome().equals(nomeFuncionario)) {
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
                String salario = df.format(valorSalario);
                folhaPagamento.append(aux).append(" - ").append(funcionario.getDadosPessoais().getNome()).append(" ").append(funcionario.getDadosPessoais().getSobrenome()).append(": ").append(salario).append("\n");
                aux++;
            }
        }
        return folhaPagamento.toString();
    }

    public String getFolhaDePagamentoAssalariados() {
        StringBuilder folhaPagamento = new StringBuilder("----- FOLHA DE PAGAMENTO: ASSALARIADOS -----\n\n");
        int aux = 1;
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario instanceof FuncionarioAssalariado) {
                String salario = df.format(funcionario.getRendimentos());
                folhaPagamento.append(aux).append(" - ").append(funcionario.getDadosPessoais().getNome()).append(" ").append(funcionario.getDadosPessoais().getSobrenome()).append(": ").append(salario).append("\n");
                aux++;
            }
        }
        return folhaPagamento.toString();
    }

    public String getFolhaDePagamentoComissionados() {
        StringBuilder folhaPagamento = new StringBuilder("----- FOLHA DE PAGAMENTO: COMISSIONADOS -----\n\n");
        int aux = 1;
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario instanceof FuncionarioComissionado) {
                String salario = df.format(funcionario.getRendimentos());
                folhaPagamento.append(aux).append(" - ").append(funcionario.getDadosPessoais().getNome()).append(" ").append(funcionario.getDadosPessoais().getSobrenome()).append(": ").append(salario).append("\n");
                aux++;
            }
        }
        return folhaPagamento.toString();
    }

    public String getFolhaDePagamentoHoristas() {
        StringBuilder folhaPagamento = new StringBuilder("----- FOLHA DE PAGAMENTO: HORISTAS -----\n\n");
        int aux = 1;
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario instanceof FuncionarioHorista) {
                String salario = df.format(funcionario.getRendimentos());
                folhaPagamento.append(aux).append(" - ").append(funcionario.getDadosPessoais().getNome()).append(" ").append(funcionario.getDadosPessoais().getSobrenome()).append(": ").append(salario).append("\n");
                aux++;
            }
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
