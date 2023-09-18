package laboratorios.laboratorio1.salaCinema;

public class SalaCinema {
    // Atributos
    private Assento[][] assentos;
    private static final int QUANTIDADE_FILEIRAS = 12;
    private static final int QUANTIDADE_CADEIRAS = 14;

    private static final String[] ALFABETO = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

    // Construtor
    public SalaCinema() {
        this.assentos = new Assento[QUANTIDADE_FILEIRAS][QUANTIDADE_CADEIRAS];
        for (int i = 0; i < QUANTIDADE_FILEIRAS; i++) {
            for (int j = 0; j < QUANTIDADE_CADEIRAS; j++) {
                assentos[i][j] = new Assento();
            }
        }
    }

    // Métodos
    public boolean reservar(int fileira, int cadeira){
        if (!assentos[fileira][cadeira].isOcupado()){
            assentos[fileira][cadeira].setOcupado(true);
            return true;
        }
        return false;
    }

    public boolean cancelar(int fileira, int cadeira){
        if (assentos[fileira][cadeira].isOcupado()){
            assentos[fileira][cadeira].setOcupado(false);
            return true;
        }
        return false;
    }

    public String mostrarMapa(){
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < QUANTIDADE_FILEIRAS; i++) {
            resultado.append(ALFABETO[i]).append("\t");
            for (int j = 0; j < QUANTIDADE_CADEIRAS; j++) {
                resultado.append(assentos[i][j]).append("\t");
                if (j == (QUANTIDADE_CADEIRAS-2)/2){
                    resultado.append(" \t");
                }
            }
            resultado.append(ALFABETO[i]).append("\t");
            resultado.append("\n");
        }
        resultado.append(" \t");
        for (int j = 1; j <= QUANTIDADE_CADEIRAS; j++) {
            resultado.append(j).append("\t");
            if (j == (QUANTIDADE_CADEIRAS)/2){
                resultado.append(" \t");
            }
        }
        resultado.append("\n");
        resultado.append("\n");
        return String.valueOf(resultado);
    }

    public int calcularQuantidadeAssentosLivres(){
        int assentosLivres = 0;
        for (int i = 0; i < QUANTIDADE_FILEIRAS; i++) {
            for (int j = 0; j < QUANTIDADE_CADEIRAS; j++) {
                if (!assentos[i][j].isOcupado())
                    assentosLivres++;
            }
        }
        return assentosLivres;
    }

    public int calcularQuantidadeAssentosOcupados(){
        int assentosOcupados = 0;
        for (int i = 0; i < QUANTIDADE_FILEIRAS; i++) {
            for (int j = 0; j < QUANTIDADE_CADEIRAS; j++) {
                if (assentos[i][j].isOcupado())
                    assentosOcupados++;
            }
        }
        return assentosOcupados;
    }

    // Get e Set
    public Assento[][] getAssentos() {
        return assentos;
    }

    public void setAssentos(Assento[][] assentos) {
        this.assentos = assentos;
    }

    public int getQuantidadeFileiras() {
        return QUANTIDADE_FILEIRAS;
    }

    public int getQuantidadeCadeiras() {
        return QUANTIDADE_CADEIRAS;
    }
}
