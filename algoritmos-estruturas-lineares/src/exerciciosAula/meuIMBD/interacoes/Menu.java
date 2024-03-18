package exerciciosAula.meuIMBD.interacoes;

import static java.lang.System.*;

public class Menu {
    // Atributos
    static Opcoes opcoes = new Opcoes();

    // Métodos
    public static void main(String[] args) {
        boolean manterLoop = true;
        out.println(ConsoleColors.BLACK_BOLD + "Bem-Vindo a Sua Plataforma de Streamings!" +ConsoleColors.RESET);
        while (manterLoop) {
            opcoes.escolherStreaming();
            switch (5) {
                case 5 -> manterLoop = false;
                default -> out.println("Resposta Não Encontrada!");
            }
        }
    }
}
