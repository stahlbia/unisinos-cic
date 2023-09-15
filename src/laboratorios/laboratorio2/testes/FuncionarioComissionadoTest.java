package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;
import laboratorios.laboratorio2.funcionarios.FuncionarioComissionado;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioComissionadoTest {
    @Test
    public void deveCriarUmFuncionarioAssalariadoCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste1", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioComissionado funcionarioComissionado = new FuncionarioComissionado(dadosPessoais, 0.3, 10000);
        String nomeEsperado = "Teste1";
        double rendimentoEsperado = 3000.0;
        double taxaComissaoEsperada = 0.3;
        double vendasBrutasEsperada = 10000.0;

        // Act
        String nome = funcionarioComissionado.getDadosPessoais().getNome();
        double rendimentos = funcionarioComissionado.getRendimentos();
        double taxaComissao = funcionarioComissionado.getTaxaComissao();
        double vendasBrutas = funcionarioComissionado.getVendasBrutas();

        // Assert
        Assert.assertEquals(nomeEsperado, nome);
        Assert.assertEquals(rendimentoEsperado, rendimentos, 0);
        Assert.assertEquals(taxaComissaoEsperada, taxaComissao, 0);
        Assert.assertEquals(vendasBrutasEsperada, vendasBrutas, 0);
    }

    @Test
    public void deveEditarOsValoresCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste2", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioComissionado funcionarioComissionado = new FuncionarioComissionado(dadosPessoais, 0.3, 10000);
        double rendimentoEsperado = 2000.0;
        double taxaComissaoEsperada = 0.4;
        double vendasBrutasEsperada = 5000.0;

        // Act
        funcionarioComissionado.setTaxaComissao(0.4);
        funcionarioComissionado.setVendasBrutas(5000.0);
        double rendimentos = funcionarioComissionado.getRendimentos();
        double taxaComissao = funcionarioComissionado.getTaxaComissao();
        double vendasBrutas = funcionarioComissionado.getVendasBrutas();

        // Assert
        Assert.assertEquals(rendimentos, rendimentoEsperado, 0);
        Assert.assertEquals(taxaComissaoEsperada, taxaComissao, 0);
        Assert.assertEquals(vendasBrutasEsperada, vendasBrutas, 0);
    }
}
