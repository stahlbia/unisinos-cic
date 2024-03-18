package exerciciosAula.meuIMBD.interacoes;

import exerciciosAula.meuIMBD.streamings.Streaming;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.out;

public class Opcoes {
    // Atributos
    Scanner scanner;
    Teclado teclado;
    Streaming[] streamings;
    Streaming streamingEscolhido;

    // Construtor
    public Opcoes() {
        scanner = new Scanner(System.in);
        teclado = new Teclado();
        streamings = new Streaming[]{new Streaming("Netflix")};
    }

    // Métodos
    public void escolherStreaming() {
        while (true) {
            out.println(ConsoleColors.BLUE_BOLD + "\n========== Streamings Assinados ==========" + ConsoleColors.RESET);
            for (int i = 0; i < streamings.length; i++) {
                out.println(i + 1 + ". " + streamings[i].getNome());
            }
            int resposta = teclado.leInt("Escolha uma opção: ");
            try {
                streamingEscolhido = streamings[resposta - 1];
                break;
            } catch (ArrayIndexOutOfBoundsException e) {
                out.println(ConsoleColors.RED + "Resposta Inválida!" + ConsoleColors.RESET);
            }
        }
    }

    public void mostrarCatalogoCompleto() {}

    public void mostrarRecomendacoesFinalSemana() {}

    public void mostrarTop10() {}

    public void mostrarExclusivos() {}
}
