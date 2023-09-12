package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;
import laboratorios.laboratorio2.funcionarios.FuncionarioComissionado;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioComissionadoTest {
    @Test
    public void deveCriarUmFuncionarioAssalariadoCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioComissionado funcionarioComissionado = new FuncionarioComissionado(dadosPessoais, 0.3, 10000);
        double rendimentoEsperado = 3000.0;
        String nomeEsperado = "Teste";

        // Act
        double rendimentos = funcionarioComissionado.getRendimentos();
        String nome = funcionarioComissionado.getDadosPessoais().getNome();

        // Assert
        Assert.assertEquals(rendimentos, rendimentoEsperado, 0);
        Assert.assertEquals(nome, nomeEsperado);
    }
}
