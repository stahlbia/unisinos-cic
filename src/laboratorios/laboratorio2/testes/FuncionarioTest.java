package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.funcionarios.Funcionario;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioTest {
    @Test
    public void deveCriarUmFuncionarioGenericoCorretamente() {
        // Arrange
        Funcionario funcionario = new Funcionario("12345678", "Teste", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        String rendimentoEsperado = "R$ 0,00";
        String nomeEsperado = "Teste";

        // Act
        String rendimentos = funcionario.getRendimentos();
        String nome = funcionario.getNome();

        // Assert
        Assert.assertEquals(rendimentoEsperado, rendimentos);
        Assert.assertEquals(nomeEsperado, nome);
    }
    @Test
    public void devePegarAsInformacoesCorretas() {
        // Arrange
        Funcionario funcionario = new Funcionario("12345678", "Teste", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        String idEsperado = "12345678";
        String nomeEsperado = "Teste";
        String sobrenomeEsperado = "da Silva";
        String cpfEsperado = "123.456.789-10";
        int idadeEsperada = 30;
        char sexoEsperado = 'B';
        String emailEsperado = "teste.dasilva@gmail.com";
        String telefoneEsperado = "(51) 9 9988-7766";

        // Act
        String id = funcionario.getIdFuncionario();
        String nome = funcionario.getNome();
        String sobrenome = funcionario.getSobrenome();
        String cpf = funcionario.getCpf();
        int idade = funcionario.getIdade();
        char sexo = funcionario.getSexo();
        String email = funcionario.getEmail();
        String telefone = funcionario.getTelefone();

        // Assert
        Assert.assertEquals(id, idEsperado);
        Assert.assertEquals(nome, nomeEsperado);
        Assert.assertEquals(sobrenome, sobrenomeEsperado);
        Assert.assertEquals(cpf, cpfEsperado);
        Assert.assertEquals(idade, idadeEsperada);
        Assert.assertEquals(sexo, sexoEsperado);
        Assert.assertEquals(email, emailEsperado);
        Assert.assertEquals(telefone, telefoneEsperado);
    }

    @Test
    public void deveMudarAsInformacoesCorretamente() {
        // Arrange
        Funcionario funcionario = new Funcionario("12345678", "Teste", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        String idEsperado = "87654321";
        String nomeEsperado = "Silva";
        String sobrenomeEsperado = "do Teste";
        String cpfEsperado = "098.765.432-11";
        int idadeEsperada = 15;
        char sexoEsperado = 'A';
        String emailEsperado = "silva.doteste@gmail.com";
        String telefoneEsperado = "(15) 9 6677-8899";

        // Act pt1
        funcionario.setIdFuncionario("87654321");
        funcionario.setNome("Silva");
        funcionario.setSobrenome("do Teste");
        funcionario.setCpf("098.765.432-11");
        funcionario.setIdade(15);
        funcionario.setSexo('A');
        funcionario.setEmail("silva.doteste@gmail.com");
        funcionario.setTelefone("(15) 9 6677-8899");

        // Act pt2
        String id = funcionario.getIdFuncionario();
        String nome = funcionario.getNome();
        String sobrenome = funcionario.getSobrenome();
        String cpf = funcionario.getCpf();
        int idade = funcionario.getIdade();
        char sexo = funcionario.getSexo();
        String email = funcionario.getEmail();
        String telefone = funcionario.getTelefone();

        // Assert
        Assert.assertEquals(id, idEsperado);
        Assert.assertEquals(nome, nomeEsperado);
        Assert.assertEquals(sobrenome, sobrenomeEsperado);
        Assert.assertEquals(cpf, cpfEsperado);
        Assert.assertEquals(idade, idadeEsperada);
        Assert.assertEquals(sexo, sexoEsperado);
        Assert.assertEquals(email, emailEsperado);
        Assert.assertEquals(telefone, telefoneEsperado);
    }
}
