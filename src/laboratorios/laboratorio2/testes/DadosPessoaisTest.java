package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;
import org.junit.Assert;
import org.junit.Test;

public class DadosPessoaisTest {
    String idEsperado = "12345678";
    String nomeEsperado = "Teste";
    String sobrenomeEsperado = "da Silva";
    String cpfEsperado = "123.456.789-10";
    int idadeEsperada = 30;
    char sexoEsperado = 'B';
    String emailEsperado = "teste.dasilva@gmail.com";
    String telefoneEsperado = "(51) 9 9988-7766";
    @Test
    public void deveConseguirRetornarOsDados() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais(idEsperado, nomeEsperado, sobrenomeEsperado, cpfEsperado, idadeEsperada, sexoEsperado, emailEsperado, telefoneEsperado);

        // Act
        String id = dadosPessoais.getIdFuncionario();
        String nome = dadosPessoais.getNome();
        String sobrenome = dadosPessoais.getSobrenome();
        String cpf = dadosPessoais.getCpf();
        int idade = dadosPessoais.getIdade();
        char sexo = dadosPessoais.getSexo();
        String email = dadosPessoais.getEmail();
        String telefone = dadosPessoais.getTelefone();

        // Assert
        Assert.assertEquals(idEsperado, id);
        Assert.assertEquals(nomeEsperado, nome);
        Assert.assertEquals(sobrenomeEsperado, sobrenome);
        Assert.assertEquals(cpfEsperado, cpf);
        Assert.assertEquals(idadeEsperada, idade);
        Assert.assertEquals(sexoEsperado, sexo);
        Assert.assertEquals(emailEsperado, email);
        Assert.assertEquals(telefoneEsperado, telefone);
    }

    @Test
    public void deveConseguirEditarOsDados() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("87654321", "Teste2", "da Silva2", "987.654.321-00", 40, 'A', "teste2.dasilva@gmail.com", "(51) 9 6677-8899");

        // Act
        dadosPessoais.setIdFuncionario("12345678");
        dadosPessoais.setNome("Teste");
        dadosPessoais.setSobrenome("da Silva");
        dadosPessoais.setCpf("123.456.789-10");
        dadosPessoais.setIdade(30);
        dadosPessoais.setSexo('B');
        dadosPessoais.setEmail("teste.dasilva@gmail.com");
        dadosPessoais.setTelefone("(51) 9 9988-7766");

        String id = dadosPessoais.getIdFuncionario();
        String nome = dadosPessoais.getNome();
        String sobrenome = dadosPessoais.getSobrenome();
        String cpf = dadosPessoais.getCpf();
        int idade = dadosPessoais.getIdade();
        char sexo = dadosPessoais.getSexo();
        String email = dadosPessoais.getEmail();
        String telefone = dadosPessoais.getTelefone();

        // Assert
        Assert.assertEquals(idEsperado, id);
        Assert.assertEquals(nomeEsperado, nome);
        Assert.assertEquals(sobrenomeEsperado, sobrenome);
        Assert.assertEquals(cpfEsperado, cpf);
        Assert.assertEquals(idadeEsperada, idade);
        Assert.assertEquals(sexoEsperado, sexo);
        Assert.assertEquals(emailEsperado, email);
        Assert.assertEquals(telefoneEsperado, telefone);
    }
}
