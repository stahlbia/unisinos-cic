package laboratorios.laboratorio1.interacoes;

import laboratorios.laboratorio1.salaCinema.SalaCinema;

import java.util.Scanner;

import static java.lang.System.out;

public class Opcoes {
    // Atributos
    static Scanner scanner = new Scanner(System.in);
    SalaCinema sala = new SalaCinema();

    // Métodos
    public int escolher() {
        out.println("Escolha uma das opções:");
        out.println("1 - Reservar");
        out.println("2 - Cancelar");
        out.println("3 - Mostrar Sala");
        out.println("4 - Mostrar Quantidade de Lugares");
        out.println("5 - Finalizar Compra");

        return Integer.parseInt(scanner.nextLine());
    }

    public boolean reservar() {
        out.println("Gostaria de reservar um assento em qual fileira?");
        int fileira = scanner.nextLine().charAt(0) - 65;
        out.println("Qual o número do assento?");
        int cadeira = Integer.parseInt(scanner.nextLine());

        return sala.reservar(fileira, cadeira);
    }

    public boolean cancelar() {
        out.println("Gostaria de cancelar a reserva em qual fileira?");
        int fileira = scanner.nextLine().charAt(0);
        out.println("Qual o número do assento que gostaria de cancelar?");
        int cadeira = scanner.nextInt();

        return sala.cancelar(fileira, cadeira);
    }

    public boolean mostrarSala() {
        out.println("Sala do Cinema:");
        out.println(sala.mostrarMapa());
        return true;
    }

    public boolean mostrarQtdLugaresLivres() {
        out.print("Quantidade de lugares livres na sessão: ");
        out.println(sala.calcularQuantidadeAssentosLivres());
        return true;
    }
}
