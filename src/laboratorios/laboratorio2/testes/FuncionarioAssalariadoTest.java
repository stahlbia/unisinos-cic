package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;
import laboratorios.laboratorio2.funcionarios.FuncionarioAssalariado;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioAssalariadoTest {
    @Test
    public void deveCriarUmFuncionarioAssalariadoCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste1", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioAssalariado funcionarioAssalariado = new FuncionarioAssalariado(dadosPessoais, 2000);
        double rendimentoEsperado = 2000.0;
        String nomeEsperado = "Teste1";

        // Act
        double rendimentos = funcionarioAssalariado.getRendimentos();
        String nome = funcionarioAssalariado.getDadosPessoais().getNome();

        // Assert
        Assert.assertEquals(rendimentos, rendimentoEsperado, 0);
        Assert.assertEquals(nome, nomeEsperado);
    }

    @Test
    public void deveMudarEditarSalarioCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste2", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioAssalariado funcionarioAssalariado = new FuncionarioAssalariado(dadosPessoais, 2000);
        double rendimentoEsperado = 3000.0;

        // Act
        funcionarioAssalariado.setSalarioSemanal(3000.0);
        double rendimentos = funcionarioAssalariado.getRendimentos();

        // Assert
        Assert.assertEquals(rendimentos, rendimentoEsperado, 0);
    }
}
