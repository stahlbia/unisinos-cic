package laboratorios.laboratorio1.salaCinema;

public class Assento {
    // Atributos
    private boolean ocupado;

    // Construtor
    public Assento() {
        this.ocupado = false;
    }

    // Métodos
    @Override
    public String toString() {
        if (ocupado) {
            return "■";
        }
        return "▢";
    }
    public void reservar() {
        this.ocupado = true;
    }

    public void cancelar() {
        this.ocupado = false;
    }

    // Get e Set
    public boolean isOcupado() {
        return ocupado;
    }
}
