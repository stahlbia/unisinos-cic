#include <iostream>
#include <vector> //para as listas dinamicas
#include <algorithm> //para ordenação como sort
#include <string>

using namespace std;

struct Trilha {
    int origem, destino, custo; //sendo origem de onde estamos saindo destinos onde vamos chegar e quanto aquela trilha vai custar
    
    //guardando os nomes para visualizar o resultado final
    string nomeOrigem;
    string nomeDestino;
    
};

struct InfoArea{
    int areaPai; //representante da area "pai"
    int tamanho;
};

class Parque{
private:
    int totalDeLocais;
    int totalTrilhasPossiveis;
    
    vector<Trilha> listaDeTrilhas; //guardar todas trilhas totalTrilhasPossiveis
    vector<string> nomesDosLocais; //guardar nome de cada local

    static bool compararTrilhasPeloCusto(const Trilha& trilha1, const Trilha& trilha2) {
        return trilha1.custo < trilha2.custo;
    }
    
    //union-find: keep track of which vertices are connected in the MST to ensure that no cycles are formed
    //Find: Determine which set a particular element belongs to.
    int encontraRepresentante(vector<InfoArea>& areas, int idLocal){
        if(areas[idLocal].areaPai != idLocal){ //checar se o local não é seu representante
            areas[idLocal].areaPai = encontraRepresentante(areas, areas[idLocal].areaPai);
        }
        return areas[idLocal].areaPai;
    }

    //Union: Merge two sets into one.
    void unirAreas(vector<InfoArea>& areas, int localA, int localB) {
        int representanteA = encontraRepresentante(areas, localA);
        int representanteB = encontraRepresentante(areas, localB);

        //so unimos se eles tiverem areas diferentes
        if (representanteA != representanteB) {
            //area menor se junta a maior (eficencia)
            if (areas[representanteA].tamanho < areas[representanteB].tamanho) {
                areas[representanteA].areaPai = representanteB;
            } else if (areas[representanteA].tamanho > areas[representanteB].tamanho) {
                areas[representanteB].areaPai = representanteA;
            } else {
                //se elas tem o mesmo tamanho é indiferente
                areas[representanteB].areaPai = representanteA;
                areas[representanteA].tamanho++;
            }
        }
    }

    void imprimirTrilhasConstruidas(vector<Trilha>& redeFinalDeTrilhas) {
        cout << "\n--- Plano de Construcao Final das Trilhas do Parque ---\n";
        int custoTotal = 0;
        for (const auto& trilha : redeFinalDeTrilhas) {
            cout << "Construir: " << trilha.nomeOrigem
                    << "  <----->  " << trilha.nomeDestino
                    << "  (Custo: " << trilha.custo << ")\n";
            custoTotal += trilha.custo;
        }
        cout << "------------------------------------------------------\n";
        cout << "Custo total para conectar todos os locais do parque: " << custoTotal << "\n";
    }

public:
    //construtor que vai ser executado automaticamente quando um Parque novo for criado, inicializa os valores
    Parque(int locais, const vector<string>& nomes) {
        totalDeLocais = locais;
        nomesDosLocais = nomes;
        totalTrilhasPossiveis = 0; //começar no 0 para ir adicionando
    }
    
    //uma ação que o parque pode executar ou seja adicionar trilha ao vetor lista de trilhas
    void adicionarTrilhaPossivel(int idOrigem, int idDestino, int custoDaTrilha){
        Trilha novaTrilha;
        novaTrilha.origem = idOrigem;
        novaTrilha.destino = idDestino;
        novaTrilha.custo = custoDaTrilha;
        novaTrilha.nomeOrigem = nomesDosLocais[idOrigem];
        novaTrilha.nomeDestino = nomesDosLocais[idDestino];
        
        listaDeTrilhas.push_back(novaTrilha); //adicionar trilha na lista usando push_back
        totalTrilhasPossiveis++; //atualiza o counter das trilhas
    }

    int getTotalDeLocais() const {
        return totalDeLocais;
    }

    void construirTrilhas(){ // algoritmo de Kruskal -> O(E log E + E log V)
        vector<Trilha> redeFinalDeTrilhas;
        
        sort(listaDeTrilhas.begin(), listaDeTrilhas.end(), Parque::compararTrilhasPeloCusto);

        vector<InfoArea> areas(totalDeLocais);
        for(int i = 0; i< totalDeLocais; ++i){
            areas[i].areaPai = i; //representante da propria area
            areas[i].tamanho = 0;
        }
        
        cout <<"Analisando trilhas que sao candidatas para encontrar a rede mais economica...\n\n";
        
        for (const auto& trilhaCandidata : listaDeTrilhas){
            //const auto para pegar cada item da lista de cada vez e chamar de trilha candidatas
            
            int areaDaOrigem = encontraRepresentante(areas, trilhaCandidata.origem);
            int areaDoDestino = encontraRepresentante(areas, trilhaCandidata.destino);
            
            //verificação para ver se a origem e o destino da trilha já estão na mesma area conectada
            //se sim não devemos construir outra trilha
            if (areaDaOrigem != areaDoDestino){
                redeFinalDeTrilhas.push_back(trilhaCandidata); //adiciona a trilha a rede
                unirAreas(areas, trilhaCandidata.origem, trilhaCandidata.destino);
                cout << "Aprovada Trilha: " << trilhaCandidata.nomeOrigem << " <-> "
                        << trilhaCandidata.nomeDestino << " (Custo: " << trilhaCandidata.custo << ")\n";
            } else {
                //se ja estao na mesma área, pulamos essa trilha.
                cout << "Descartada Trilha: " << trilhaCandidata.nomeOrigem << " <-> "
                        << trilhaCandidata.nomeDestino << " (Custo: " << trilhaCandidata.custo << ") - Formaria um caminho redundante.\n";
            }
            
            //se ja temos (TotalDeLocais - 1) trilhas, já conectamos todo mundo demos o break.
            if (redeFinalDeTrilhas.size() == totalDeLocais - 1) {
                break;
            }
        }
        
        imprimirTrilhasConstruidas(redeFinalDeTrilhas);
    }
};

int main() {
    //definimos locais de interesse do parque
    // Os números (0, 1, 2...) são os IDs que o programa usa internamente.
    vector<string> locaisDoParque = {
        "Bosque de platanos",  // ID: 0
        "Clareira",            // ID: 1
        "Gruta",               // ID: 2
        "Campo de flores",     // ID: 3
        "Cachoeira"            // ID: 4
    };
    
    //criar objeto do parque
    Parque meuParque(locaisDoParque.size(), locaisDoParque);

    cout << "Planejando a rede de trilhas para o novo parque!\n";
    cout << "Temos " << meuParque.getTotalDeLocais() << " locais para conectar.\n\n";

    //adicionando as trilhas e custos
    // (ID do local A, ID do local B, Custo)
    meuParque.adicionarTrilhaPossivel(0, 1, 10); // Bosque <-> Clareira (Custo 10)
    meuParque.adicionarTrilhaPossivel(0, 3, 12); // Bosque <-> Campo de flores (Custo 12)
    meuParque.adicionarTrilhaPossivel(1, 2, 9);  // Clareira <-> Gruta (Custo 9)
    meuParque.adicionarTrilhaPossivel(1, 3, 6);  // Clareira <-> Campo de flores (Custo 6)
    meuParque.adicionarTrilhaPossivel(2, 3, 3);  // Gruta <-> Campo de flores (Custo 3)
    meuParque.adicionarTrilhaPossivel(2, 4, 11); // Gruta <-> Cachoeira (Custo 11)
    meuParque.adicionarTrilhaPossivel(3, 4, 7);  // Campo de flores <-> Cachoeira (Custo 7)

    cout << "Objetivo: Conectar todos os locais com o menor custo possivel, sem desperdicio!\n";
    cout << "Aplicando o Algoritmo de Kruskal para encontrar a melhor solucao...\n\n";
    
    meuParque.construirTrilhas();

    return 0;
}