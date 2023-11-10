package laboratorios.laboratorio1.testes;

import laboratorios.laboratorio1.salaCinema.Assento;
import org.junit.Assert;
import org.junit.Test;

public class AssentoTest {
    @Test
    public void deveCriarUmAssentoComoLivre() {
        // Arrange
        Assento assento = new Assento();

        // Act
        boolean status = assento.isOcupado();

        // Assert
        Assert.assertFalse(status);
    }

    @Test
    public void deveEditarUmAssentoParaOcupado() {
        // Arrange
        Assento assento = new Assento();

        // Act
        assento.reservar();
        boolean status = assento.isOcupado();

        // Assert
        Assert.assertTrue(status);
    }
}
