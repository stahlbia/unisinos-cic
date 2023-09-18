package laboratorios.laboratorio1.salaCinema;

public class Assento {
    // Atributos
    private boolean ocupado;

    // Métodos
    @Override
    public String toString() {
        if (ocupado) {
            return "■";
        }
        return "▢";
    }

    // Get e Set
    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}
