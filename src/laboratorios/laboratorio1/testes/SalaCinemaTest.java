package laboratorios.laboratorio1.testes;

import laboratorios.laboratorio1.salaCinema.SalaCinema;
import org.junit.Assert;
import org.junit.Test;

public class SalaCinemaTest {
    @Test
    public void deveCriarUmaSalaDeCinemaComAssentosLivres() {
        // Arrange
        SalaCinema sala = new SalaCinema();

        // Act
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 14; j++) {
                // Assert
                Assert.assertFalse(sala.getAssentos()[i][j].isOcupado());
            }
        }
    }

    @Test
    public void deveReservarUmAssentoCorretamente() {
        // Arrange
        SalaCinema sala = new SalaCinema();

        // Act
        boolean retorno = sala.reservar(2, 2);

        // Assert
        Assert.assertTrue(retorno);
    }

    @Test
    public void deveCancelarUmaRevervaCorretamente() {
        // Arrange
        SalaCinema sala = new SalaCinema();

        // Act
        sala.reservar(2, 2);
        boolean retorno = sala.cancelar(2, 2);

        // Assert
        Assert.assertTrue(retorno);
    }

    @Test
    public void deveRetornarAQtdDeAssentosLivresCorretamente() {
        // Arrange
        SalaCinema sala = new SalaCinema();
        int qtdEsperada = 166;

        // Act
        sala.reservar(2, 2);
        sala.reservar(3, 3);
        int retorno = sala.calcularQuantidadeAssentosLivres();

        // Assert
        Assert.assertEquals(qtdEsperada, retorno);
    }

    @Test
    public void deveRetornarAQtdDeAssentosOcupadosCorretamente() {
        // Arrange
        SalaCinema sala = new SalaCinema();
        int qtdEsperada = 2;

        // Act
        sala.reservar(2, 2);
        sala.reservar(3, 3);
        int retorno = sala.calcularQuantidadeAssentosOcupados();

        // Assert
        Assert.assertEquals(qtdEsperada, retorno);
    }
}
