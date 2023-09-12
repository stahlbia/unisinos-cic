package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;
import laboratorios.laboratorio2.funcionarios.FuncionarioComissionadoBaseSalario;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioComissionadoBaseSalarioTest {
    @Test
    public void deveCriarUmFuncionarioAssalariadoCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioComissionadoBaseSalario funcionarioComissionadoBaseSalario = new FuncionarioComissionadoBaseSalario(dadosPessoais, 0.3, 10000, 2000);
        double rendimentoEsperado = 5000.0;
        String nomeEsperado = "Teste";

        // Act
        double rendimentos = funcionarioComissionadoBaseSalario.getRendimentos();
        String nome = funcionarioComissionadoBaseSalario.getDadosPessoais().getNome();

        // Assert
        Assert.assertEquals(rendimentos, rendimentoEsperado, 0);
        Assert.assertEquals(nome, nomeEsperado);
    }
}
