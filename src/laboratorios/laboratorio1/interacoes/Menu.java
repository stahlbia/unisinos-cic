package laboratorios.laboratorio1.interacoes;

import static java.lang.System.*;

public class Menu {
    // Atributos
    static Opcoes opcoes = new Opcoes();

    // Métodos
    public static void main(String[] args) {
        boolean manterLoop = true;
        out.println("Bem-vindo aos Cinemas XP!");
        while (manterLoop) {
            int escolha = opcoes.escolher();
            switch (escolha) {
                case 1 -> opcoes.reservar();
                case 2 -> opcoes.cancelar();
                case 3 -> opcoes.mostrarSala();
                case 4 -> opcoes.mostrarQtdLugaresLivres();
                case 5 -> manterLoop = false;
                default -> out.println("Resposta Não Encontrada!");
            }
        }
    }

}
