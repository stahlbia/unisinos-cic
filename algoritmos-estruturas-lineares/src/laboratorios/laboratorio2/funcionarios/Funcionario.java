package laboratorios.laboratorio2.funcionarios;

import laboratorios.laboratorio2.dadosPessoais.DadosPessoais;

public interface Funcionario {
    default double getRendimentos() {
        return 0;
    }
    default DadosPessoais getDadosPessoais() {
        return null;
    }
}