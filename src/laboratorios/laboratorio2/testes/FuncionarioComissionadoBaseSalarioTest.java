package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.funcionarios.Funcionario;
import laboratorios.laboratorio2.funcionarios.FuncionarioComissionadoBaseSalario;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioComissionadoBaseSalarioTest {
    @Test
    public void deveCriarUmFuncionarioAssalariadoCorretamente() {
        // Arrange
        Funcionario funcionario = new Funcionario("12345678", "Teste", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioComissionadoBaseSalario funcionarioComissionadoBaseSalario = new FuncionarioComissionadoBaseSalario(funcionario, 0.3, 10000, 2000);
        Double rendimentoEsperado = 5000.0;
        String nomeEsperado = "Teste";

        // Act
        Double rendimentos = funcionarioComissionadoBaseSalario.getRendimentos();
        String nome = funcionario.getNome();

        // Assert
        Assert.assertEquals(rendimentos, rendimentoEsperado, 0);
        Assert.assertEquals(nome, nomeEsperado);
    }
}
