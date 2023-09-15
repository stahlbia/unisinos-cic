package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;
import laboratorios.laboratorio2.folhaPagamento.FolhaPagamento;
import laboratorios.laboratorio2.funcionarios.*;
import org.junit.Assert;
import org.junit.Test;

public class FolhaDePagamentoTest {
    DadosPessoais dadosPessoais1 = new DadosPessoais("12345678", "Teste", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
    DadosPessoais dadosPessoais2 = new DadosPessoais("87654321", "Teste2", "da Silva", "987.654.321-00", 40, 'A', "teste2.dasilva@gmail.com", "(51) 9 6677-8899");
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
        String nome = funcionario.getDadosPessoais().getNome();

        // Assert
        Assert.assertEquals(nomeEsperado, nome);
    }

    @Test
    public void deveRetornarAFolhaDePagamentoCompleta() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, false);
        String folhaPagamentoCompletaEsperada = """
                ----- FOLHA DE PAGAMENTO -----
                
                1 - Teste1 daSilva1: R$ 300,00
                2 - Teste2 daSilva2: R$ 3.000,00
                3 - Teste3 daSilva3: R$ 3.300,00
                4 - Teste4 daSilva4: R$ 400,00
                5 - Teste5 daSilva5: R$ 300,00
                6 - Teste6 daSilva6: R$ 3.000,00
                7 - Teste7 daSilva7: R$ 3.300,00
                8 - Teste8 daSilva8: R$ 400,00
                9 - Teste9 daSilva9: R$ 300,00
                """;

        // Act
        String folhaPagamentoCompleta = folhaPagamento.getFolhaDePagamentoCompleta();

        // Assert
        Assert.assertEquals(folhaPagamentoCompletaEsperada, folhaPagamentoCompleta);
    }

    @Test
    public void deveRetornarAFolhaDePagamentoAssalariados() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, false);
        String folhaPagamentoAssalariadosEsperada = """
                ----- FOLHA DE PAGAMENTO: ASSALARIADOS -----
                
                1 - Teste1 daSilva1: R$ 300,00
                2 - Teste5 daSilva5: R$ 300,00
                3 - Teste9 daSilva9: R$ 300,00
                """;

        // Act
        String folhaDePagamentoAssalariados = folhaPagamento.getFolhaDePagamentoAssalariados();

        // Assert
        Assert.assertEquals(folhaPagamentoAssalariadosEsperada, folhaDePagamentoAssalariados);
    }

    @Test
    public void deveRetornarAFolhaDePagamentoComissionados() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, false);
        String folhaPagamentoComissionadosdosEsperada = """
                ----- FOLHA DE PAGAMENTO: COMISSIONADOS -----
                
                1 - Teste2 daSilva2: R$ 3.000,00
                2 - Teste3 daSilva3: R$ 3.300,00
                3 - Teste6 daSilva6: R$ 3.000,00
                4 - Teste7 daSilva7: R$ 3.300,00
                """;

        // Act
        String folhaDePagamentoComissionados = folhaPagamento.getFolhaDePagamentoComissionados();

        // Assert
        Assert.assertEquals(folhaPagamentoComissionadosdosEsperada, folhaDePagamentoComissionados);
    }

    @Test
    public void deveRetornarAFolhaDePagamentoHoristas() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, false);
        String folhaPagamentoHoristasEsperada = """
                ----- FOLHA DE PAGAMENTO: HORISTAS -----
                
                1 - Teste4 daSilva4: R$ 400,00
                2 - Teste8 daSilva8: R$ 400,00
                """;

        // Act
        String folhaDePagamentoHoristas = folhaPagamento.getFolhaDePagamentoHoristas();

        // Assert
        Assert.assertEquals(folhaPagamentoHoristasEsperada, folhaDePagamentoHoristas);
    }

    @Test
    public void deveRetornarAListaDeFuncionarios() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, false);
        int tamListaFuncionariosEsperada = 9;

        // Act
        int tamListaFuncionarios = folhaPagamento.getListaFuncionarios().length;

        // Assert
        Assert.assertEquals(tamListaFuncionariosEsperada, tamListaFuncionarios);
    }

    @Test
    public void deveAtualizarAListaDeFuncionarios() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, false);
        String nomeEsperado = "Funcionario Do Ano";

        // Act
        folhaPagamento.getListaFuncionarios()[0].getDadosPessoais().setNome("Funcionario Do Ano");
        String nome = folhaPagamento.getListaFuncionarios()[0].getDadosPessoais().getNome();

        // Assert
        Assert.assertEquals(nomeEsperado, nome);
    }

    @Test
    public void deveConseguirAdicionarUmFuncionario() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9);
        FuncionarioAssalariado funcionarioAssalariado = new FuncionarioAssalariado(dadosPessoais1, 2000);

        // Act
        boolean retorno = folhaPagamento.addFuncionario(funcionarioAssalariado);

        // Assert
        Assert.assertTrue(retorno);
    }

    @Test
    public void deveNaoConseguirAdicionarUmFuncionario() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(9, false);
        FuncionarioAssalariado funcionarioAssalariado = new FuncionarioAssalariado(dadosPessoais1, 2000);

        // Act
        boolean retorno = folhaPagamento.addFuncionario(funcionarioAssalariado);

        // Assert
        Assert.assertFalse(retorno);
    }

    @Test
    public void deveConseguirCriarComUmaListaJaPronta() {
        // Arrange
        FuncionarioHorista funcionario1 = new FuncionarioHorista(dadosPessoais1, 10.0, 30);
        FuncionarioComissionado funcionario2 = new FuncionarioComissionado(dadosPessoais2, 0.3, 10000);
        Funcionario[] listaFuncionarios = {funcionario1, funcionario2};
        FolhaPagamento folhaPagamento = new FolhaPagamento(listaFuncionarios);
        Funcionario[] listaEsperada = {funcionario1, funcionario2};

        // Act
        Funcionario[] lista = folhaPagamento.getListaFuncionarios();

        // Assert
        Assert.assertArrayEquals(listaEsperada, lista);
    }
    
    @Test
    public void deveConseguirEditarUmLista() {
        // Arrange
        FolhaPagamento folhaPagamento = new FolhaPagamento(2, true);
        FuncionarioHorista funcionario1 = new FuncionarioHorista(dadosPessoais1, 10.0, 30);
        FuncionarioComissionado funcionario2 = new FuncionarioComissionado(dadosPessoais2, 0.3, 10000);
        Funcionario[] listaFuncionarios = {funcionario1, funcionario2};
        Funcionario[] listaEsperada = {funcionario1, funcionario2};
        
        // Act
        folhaPagamento.setListaFuncionarios(listaFuncionarios);
        Funcionario[] lista = folhaPagamento.getListaFuncionarios();
        
        // Assert
        Assert.assertArrayEquals(listaEsperada, lista);
    }
}
