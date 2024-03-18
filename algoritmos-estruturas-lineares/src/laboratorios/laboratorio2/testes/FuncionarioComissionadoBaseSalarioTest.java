package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;
import laboratorios.laboratorio2.funcionarios.FuncionarioComissionadoBaseSalario;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioComissionadoBaseSalarioTest {
    @Test
    public void deveCriarUmFuncionarioAssalariadoCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste1", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioComissionadoBaseSalario funcionarioComissionadoBaseSalario = new FuncionarioComissionadoBaseSalario(dadosPessoais, 0.3, 10000, 2000);
        String nomeEsperado = "Teste1";
        double rendimentoEsperado = 5000.0;
        double taxaComissaoEsperada = 0.3;
        double vendasBrutasEsperada = 10000.0;
        double salarioFixoEsperado = 2000.0;

        // Act
        String nome = funcionarioComissionadoBaseSalario.getDadosPessoais().getNome();
        double rendimentos = funcionarioComissionadoBaseSalario.getRendimentos();
        double taxaComissao = funcionarioComissionadoBaseSalario.getTaxaComissao();
        double vendasBrutas = funcionarioComissionadoBaseSalario.getVendasBrutas();
        double salarioFixo = funcionarioComissionadoBaseSalario.getSalarioFixo();

        // Assert
        Assert.assertEquals(nomeEsperado, nome);
        Assert.assertEquals(rendimentoEsperado, rendimentos, 0);
        Assert.assertEquals(taxaComissaoEsperada, taxaComissao, 0);
        Assert.assertEquals(vendasBrutasEsperada, vendasBrutas, 0);
        Assert.assertEquals(salarioFixoEsperado, salarioFixo, 0);
    }

    @Test
    public void deveEditarOsValoresCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste2", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioComissionadoBaseSalario funcionarioComissionadoBaseSalario = new FuncionarioComissionadoBaseSalario(dadosPessoais, 0.3, 10000, 2000);
        String nomeEsperado = "Teste2";
        double rendimentoEsperado = 7800.0;
        double taxaComissaoEsperada = 0.4;
        double vendasBrutasEsperada = 12000.0;
        double salarioFixoEsperado = 3000.0;

        // Act
        funcionarioComissionadoBaseSalario.setTaxaComissao(0.4);
        funcionarioComissionadoBaseSalario.setVendasBrutas(12000.0);
        funcionarioComissionadoBaseSalario.setSalarioFixo(3000.0);

        String nome = funcionarioComissionadoBaseSalario.getDadosPessoais().getNome();
        double rendimentos = funcionarioComissionadoBaseSalario.getRendimentos();
        double taxaComissao = funcionarioComissionadoBaseSalario.getTaxaComissao();
        double vendasBrutas = funcionarioComissionadoBaseSalario.getVendasBrutas();
        double salarioFixo = funcionarioComissionadoBaseSalario.getSalarioFixo();

        // Assert
        Assert.assertEquals(nomeEsperado, nome);
        Assert.assertEquals(rendimentoEsperado, rendimentos, 0);
        Assert.assertEquals(taxaComissaoEsperada, taxaComissao, 0);
        Assert.assertEquals(vendasBrutasEsperada, vendasBrutas, 0);
        Assert.assertEquals(salarioFixoEsperado, salarioFixo, 0);
    }
}
