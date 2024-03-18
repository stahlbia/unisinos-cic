package laboratorios.laboratorio1.interacoes;

import laboratorios.laboratorio1.salaCinema.SalaCinema;
import java.util.Scanner;

import static java.lang.System.out;

public class Opcoes {
    // Atributos
    Scanner scanner;
    Teclado teclado;
    SalaCinema sala = new SalaCinema();

    // Construtor
    public Opcoes() {
        scanner = new Scanner(System.in);
        teclado = new Teclado();
    }

    // Métodos
    public int escolher() {
        out.println(ConsoleColors.BLUE_BOLD + "\n========== Menu ==========" + ConsoleColors.RESET);
        out.println("1. Reservar um assento");
        out.println("2. Cancelar uma reserva");
        out.println("3. Mostrar mapa de assentos");
        out.println("4. Mostrar total de lugares livres/ocupados");
        out.println("5. Sair do programa");
        return teclado.leInt("Escolha uma opção: ");
    }

    public void reservar() {
        int fileira = escolherFileira();
        int cadeira = escolherCadeira();

        boolean resultado = sala.reservar(fileira, cadeira);
        if (resultado) out.println(ConsoleColors.GREEN + "\nAssento reservado com sucesso!" + ConsoleColors.RESET);
        else out.println(ConsoleColors.RED + "\nAssento já está ocupado ou é inválido!" + ConsoleColors.RESET);
    }

    public void cancelar() {
        int fileira = escolherFileira();
        int cadeira = escolherCadeira();

        boolean resultado = sala.cancelar(fileira, cadeira);
        if (resultado) out.println(ConsoleColors.GREEN + "\nReserva cancelada com sucesso!" + ConsoleColors.RESET);
        else out.println(ConsoleColors.RED + "\nAssento não está ocupado ou é inválido!" + ConsoleColors.RESET);
    }

    public void mostrarSala() {
        out.println(sala.mostrarMapa());
    }

    public void mostrarQtdLugaresLivres() {
        out.println(
            ConsoleColors.YELLOW +
            "\nQuantidade de lugares livres na sessão: " +
            sala.calcularQuantidadeAssentosLivres() +
            ConsoleColors.RESET
        );
        out.println(
            ConsoleColors.YELLOW +
            "Quantidade de lugares ocupados na sessão: " +
            sala.calcularQuantidadeAssentosOcupados() +
            ConsoleColors.RESET
        );
    }

    private int escolherFileira() { // A = 65 L = 76
        while (true) {
            int fileira = teclado.leChar("Escolha uma fileira: ") - 65;
            if (0 <= fileira && fileira < sala.getQuantidadeFileiras()) {
                return fileira;
            }
            out.println("Fileira inválida!");
        }
    }

    private int escolherCadeira() {
        while (true) {
            int cadeira = teclado.leInt("Escolha uma cadeira: ") - 1;
            if (0 <= cadeira && cadeira < sala.getQuantidadeCadeiras()) {
                return cadeira;
            }
            out.println("Cadeira inválida!");
        }
    }
}
