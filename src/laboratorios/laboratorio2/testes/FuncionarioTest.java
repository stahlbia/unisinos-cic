package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.funcionarios.Funcionario;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioTest {
    @Test
    public void deveCriarUmFuncionarioGenericoCorretamente() {
        // Arrange
        Funcionario funcionario = new Funcionario("12345678", "Teste", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        Double rendimentoEsperado = 0.00;
        String nomeEsperado = "Teste";

        // Act
        Double rendimentos = funcionario.getRendimentos();
        String nome = funcionario.getNome();

        // Assert
        System.out.println(rendimentos);
        System.out.println(nome);
        //Assert.assertEquals(rendimentos, rendimentoEsperado);
        //Assert.assertEquals(nome, nomeEsperado);
    }
}
