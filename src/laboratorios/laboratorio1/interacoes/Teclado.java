package laboratorios.laboratorio1.interacoes;

import java.util.Scanner;

public class Teclado {
    private static final Scanner scanner = new Scanner(System.in);
    String invalidoMsg = ConsoleColors.RED + "Valor inválido!" + ConsoleColors.RESET;


    /**
     * Verifica se valor é um inteiro
     * @return boolean
     */
    private boolean isInt(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Lê um inteiro e printa uma mensagem antes
     * @return int
     */
    public int leInt (String msg) {
        while (true) {
            System.out.print(msg);
            String s = scanner.nextLine();
            if (isInt(s)) {
                return Integer.parseInt(s);
            }
            System.out.println(invalidoMsg);
        }
    }

    /**
     * Lê um caracter e printa uma mensagem antes
     * @return char
     */
    public char leChar(String msg) {
        while (true) {
            System.out.print(msg);
            String s = scanner.nextLine();
            if (s != null) {
                return s.charAt(0);
            }
            System.out.println(invalidoMsg);
        }
    }
}