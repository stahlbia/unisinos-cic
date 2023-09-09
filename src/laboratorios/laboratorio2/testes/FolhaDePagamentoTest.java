package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.folhaPagamento.FolhaPagamento;
import laboratorios.laboratorio2.funcionarios.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class FolhaDePagamentoTest {
    @Test
    public void deveCriarUmaFolhaDePagamentoCom10Funcionarios() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(10);
        Random gerador = new Random();
        int qtdFuncionariosEsperado = 10;
        for (int i = 1; i <= qtdFuncionariosEsperado; i++) {
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
                case 0 -> folhaPagamento.addFuncionario(new FuncionarioAssalariado(
                        funcionario,
                        300
                ));
                case 1 -> folhaPagamento.addFuncionario(new FuncionarioComissionado(
                        funcionario,
                        0.3,
                        10000
                ));
                case 2 -> folhaPagamento.addFuncionario(new FuncionarioComissionadoBaseSalario(
                        funcionario,
                        0.3,
                        10000,
                        300
                ));
                case 3 -> folhaPagamento.addFuncionario(new FuncionarioHorista(
                        funcionario,
                        10,
                        40
                ));
                default -> folhaPagamento.addFuncionario(funcionario);
            }
        }

        // Act
        int qtdFuncionarios = folhaPagamento.getListaFuncionarios().length;

        // Assert
        Assert.assertEquals(qtdFuncionariosEsperado, qtdFuncionarios);
    }

    @Test
    public void deveRetornarUmFuncionarioPeloNome() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(10);
        Random gerador = new Random();
        int qtdFuncionarios = 10;
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
                case 0 -> folhaPagamento.addFuncionario(new FuncionarioAssalariado(
                        funcionario,
                        300
                ));
                case 1 -> folhaPagamento.addFuncionario(new FuncionarioComissionado(
                        funcionario,
                        0.3,
                        10000
                ));
                case 2 -> folhaPagamento.addFuncionario(new FuncionarioComissionadoBaseSalario(
                        funcionario,
                        0.3,
                        10000,
                        300
                ));
                case 3 -> folhaPagamento.addFuncionario(new FuncionarioHorista(
                        funcionario,
                        10,
                        40
                ));
                default -> folhaPagamento.addFuncionario(funcionario);
            }
        }
        String nomeEsperado = "Teste1";

        // Act
        Funcionario funcionario = folhaPagamento.getFuncionarioNome("Teste1");
        String nome = funcionario.getNome();

        // Assert
        Assert.assertEquals(nomeEsperado, nome);
    }

    @Test
    public void deveRetornarAFolhaDePagamentoCompleta() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(10);
        Random gerador = new Random();
        int qtdFuncionarios = 10;
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
                case 0 -> folhaPagamento.addFuncionario(new FuncionarioAssalariado(
                        funcionario,
                        300
                ));
                case 1 -> folhaPagamento.addFuncionario(new FuncionarioComissionado(
                        funcionario,
                        0.3,
                        10000
                ));
                case 2 -> folhaPagamento.addFuncionario(new FuncionarioComissionadoBaseSalario(
                        funcionario,
                        0.3,
                        10000,
                        300
                ));
                case 3 -> folhaPagamento.addFuncionario(new FuncionarioHorista(
                        funcionario,
                        10,
                        40
                ));
                default -> folhaPagamento.addFuncionario(funcionario);
            }
        }
        String folhaPagamentoCompletaEsperada = "";

        // Act
        String folhaPagamentoCompleta = folhaPagamento.getFolhaDePagamentoCompleta();

        // Assert
        Assert.assertEquals(folhaPagamentoCompletaEsperada, folhaPagamentoCompleta);
    }

    @Test
    public void deveRetornarAFolhaDePagamentoAssalariados() {
        // Arrange


        // Act


        // Assert

    }

    @Test
    public void deveRetornarAFolhaDePagamentoComissionados() {
        // Arrange


        // Act


        // Assert

    }

    @Test
    public void deveRetornarAFolhaDePagamentoHoristas() {
        // Arrange


        // Act


        // Assert

    }

    @Test
    public void deveRetornarAListaDeFuncionarios() {
        // Arrange


        // Act


        // Assert

    }

    @Test
    public void deveAtualizarAListaDeFuncionarios() {
        // Arrange


        // Act


        // Assert

    }
}
