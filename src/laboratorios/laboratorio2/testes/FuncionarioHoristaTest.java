package laboratorios.laboratorio2.testes;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;
import laboratorios.laboratorio2.funcionarios.FuncionarioHorista;
import org.junit.Assert;
import org.junit.Test;

public class FuncionarioHoristaTest {
    @Test
    public void deveCriarUmFuncionarioAssalariadoCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste1", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioHorista funcionarioHorista = new FuncionarioHorista(dadosPessoais, 10.0, 30);
        String nomeEsperado = "Teste1";
        double rendimentoEsperado = 300.0;
        double salarioPorHoraEsperado = 10.0;
        double horasTrabalhadasEsperado = 30;


        // Act
        String nome = funcionarioHorista.getDadosPessoais().getNome();
        double rendimentos = funcionarioHorista.getRendimentos();
        double salarioPorHora = funcionarioHorista.getSalarioPorHora();
        double horasTrabalhasdas = funcionarioHorista.getHorasTrabalhadas();

        // Assert
        Assert.assertEquals(nomeEsperado, nome);
        Assert.assertEquals(rendimentoEsperado, rendimentos, 0);
        Assert.assertEquals(salarioPorHoraEsperado, salarioPorHora, 0);
        Assert.assertEquals(horasTrabalhadasEsperado, horasTrabalhasdas, 0);
    }

    @Test
    public void deveEditarOsValoresCorretamente() {
        // Arrange
        DadosPessoais dadosPessoais = new DadosPessoais("12345678", "Teste2", "da Silva", "123.456.789-10", 30, 'B', "teste.dasilva@gmail.com", "(51) 9 9988-7766");
        FuncionarioHorista funcionarioHorista = new FuncionarioHorista(dadosPessoais, 10.0, 30);
        String nomeEsperado = "Teste2";
        double rendimentoEsperado = 600.0;
        double salarioPorHoraEsperado = 15.0;
        double horasTrabalhadasEsperado = 40;


        // Act
        funcionarioHorista.setSalarioPorHora(15.0);
        funcionarioHorista.setHorasTrabalhadas(40);

        String nome = funcionarioHorista.getDadosPessoais().getNome();
        double rendimentos = funcionarioHorista.getRendimentos();
        double salarioPorHora = funcionarioHorista.getSalarioPorHora();
        double horasTrabalhasdas = funcionarioHorista.getHorasTrabalhadas();

        // Assert
        Assert.assertEquals(nomeEsperado, nome);
        Assert.assertEquals(rendimentoEsperado, rendimentos, 0);
        Assert.assertEquals(salarioPorHoraEsperado, salarioPorHora, 0);
        Assert.assertEquals(horasTrabalhadasEsperado, horasTrabalhasdas, 0);
    }
}
