package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.funcionarios.Funcionario;
import laboratorios.laboratorio2.funcionarios.FuncionarioAssalariado;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioAssalariadoTest {
    @Test
    public void deveCriarUmFuncionarioAssalariadoCorretamente() {
        // Arrange
        Funcionario funcionario = new Funcionario("12345678", "Teste", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioAssalariado funcionarioAssalariado = new FuncionarioAssalariado(funcionario, 2000);
        String rendimentoEsperado = "R$ 2.000,00";
        String nomeEsperado = "Teste";

        // Act
        String rendimentos = funcionarioAssalariado.getRendimentos();
        String nome = funcionario.getNome();

        // Assert
        Assert.assertEquals(rendimentos, rendimentoEsperado);
        Assert.assertEquals(nome, nomeEsperado);
    }
}
