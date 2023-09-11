package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.folhaPagamento.FolhaPagamento;
import laboratorios.laboratorio2.funcionarios.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class FolhaDePagamentoTest {
    @Test
    public void deveCriarUmaFolhaDePagamentoCom9Funcionarios() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, true);
        int qtdFuncionariosEsperado = 9;

        // Act
        int qtdFuncionarios = folhaPagamento.getListaFuncionarios().length;

        // Assert
        Assert.assertEquals(qtdFuncionariosEsperado, qtdFuncionarios);
    }

    @Test
    public void deveRetornarUmFuncionarioPeloNome() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, true);
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
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, true);
        String folhaPagamentoCompletaEsperada = "";

        // Act
        String folhaPagamentoCompleta = folhaPagamento.getFolhaDePagamentoCompleta();

        // Assert
        System.out.println(folhaPagamentoCompleta);
        //Assert.assertEquals(folhaPagamentoCompletaEsperada, folhaPagamentoCompleta);
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
