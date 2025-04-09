#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

using namespace std;
const int TABLE_SIZE = 8;

struct aluno
{
    int matricula;
    char nome[30];
    float n1, n2, n3;
};

typedef struct hashNode
{
    struct aluno data;
    struct hashNode *next;
} HashNode;

typedef struct hashTable
{
    int TABLE_SIZE;
    int qtd;
    HashNode **itens;
} Hash;

Hash * createHash()
{
    Hash* ha = (Hash *) malloc(sizeof(Hash));
    if(ha != NULL)
    {
        ha->TABLE_SIZE = TABLE_SIZE;
        ha->itens = (HashNode **) malloc(TABLE_SIZE * sizeof(HashNode *));

        if (ha->itens == NULL)
        {
            free(ha);
            return NULL;
        }

        ha->qtd = 0;
        for (int i = 0; i < ha->TABLE_SIZE; i++)
        {
            ha->itens[i] = NULL;
        }
    }

    return ha;
}

void deleteHash(Hash *ha)
{
    if (ha == NULL) return;

    for (int i = 0; i < ha->TABLE_SIZE; i++)
    {
        HashNode *current = ha->itens[i];
        while (current != NULL)
        {
            HashNode *temp = current;
            current = current->next;
            free(temp);
        }
    }

    free(ha->itens);
    free(ha);
}

int divisionMethod(int chave)
{
    return chave % TABLE_SIZE;
}

// Insere um aluno
int insert_SeparateChaining(Hash *ha, struct aluno al)
{
    if (ha == NULL) return 0;

    int pos = divisionMethod(al.matricula);

    // Cria um novo nodo para o alunot
    HashNode *newNode = (HashNode *) malloc(sizeof(HashNode));
    if (newNode == NULL) return 0;

    newNode->data = al;
    newNode->next = ha->itens[pos];  // Insere no início da lista encadeada
    ha->itens[pos] = newNode;
    ha->qtd++;

    return 1;
}

// Função para procurar por um aluno a partir de sua matrícula
int search_SeparateChaining(Hash *ha, int mat, struct aluno *al)
{
    if (ha == NULL) return 0;

    int pos = divisionMethod(mat);
    HashNode *current = ha->itens[pos];

    // Percorre a lista encadeada do índice específico
    while (current != NULL)
    {
        if (current->data.matricula == mat)
        {
            *al = current->data;
            return 1;
        }
        current = current->next;
    }

    return 0;
}

/**
 * createHashFull: Esse método deverá receber 2 parâmetros, sendo eles um array de alunos e a quantidade de elementos
 * contidos neste array. Sua função deverá criar o Hash já contendo estes elementos, tratando as colisões de forma correta.
 */
Hash * createHashFull(struct aluno *alunos, int n)
{
    Hash* ha = (Hash *) malloc(sizeof(Hash));
    if(ha != NULL)
    {
        ha->TABLE_SIZE = TABLE_SIZE;
        ha->itens = (HashNode **) malloc(TABLE_SIZE * sizeof(HashNode *));

        if (ha->itens == NULL)
        {
            free(ha);
            return NULL;
        }

        ha->qtd = 0;
        for (int i = 0; i < ha->TABLE_SIZE; i++)
        {
            ha->itens[i] = NULL;
        }

        for (int i = 0; i < n; i++)
        {
            insert_SeparateChaining(ha, alunos[i]);
        }
    }

    return ha;
}

/**
 * insertMultiple_SeparateChaining: Assim como o método createHashFull, esse método também deverá receber 2 parâmetros,
 * sendo eles um array de alunos e a quantidade de elementos contidos neste array. Sua função deverá inserir todos os elementos
 * deste array dentro do Hash, tratando as colisões de forma correta.
 */

int insertMultiple_SeparateChaining(Hash *ha, struct aluno *alunos, int n) 
{
    for (int i = 0; i < n; i++)
    {
        if (!insert_SeparateChaining(ha, alunos[i]))
            return 0;
    }

    return 1;
}

// Imprime nossa HashTable
void printHash(Hash *ha)
{
    printf("HASH TABLE:\n");
    if (ha == NULL) return;

    for (int i = 0; i < ha->TABLE_SIZE; i++)
    {
        printf("[%d]: ", i);
        HashNode *current = ha->itens[i];
        while (current != NULL)
        {
            printf("-> [%d, %s] ", current->data.matricula, current->data.nome);
            current = current->next;
        }
        printf("\n");
    }
    printf("\n");
}

int main()
{
    Hash* h = createHash();

    // Cria um aluno e o insere
    struct aluno aluno1 = {123, "Dio", 10, 9, 8};
    insert_SeparateChaining(h, aluno1);

    printf("Imprimindo o HashTable depois da primeira insercao...");
    printHash(h);

    // Cria um aluno e o insere forçando uma colisão no índice 3
    struct aluno aluno2 = {11, "Humberto", 3, 2, 1};
    insert_SeparateChaining(h, aluno2);

    printf("\nImprimindo o HashTable depois da segunda insercao...");
    printHash(h);

    struct aluno resultado;
    // Verifica a existência de um aluno com matrícula 123
    if (search_SeparateChaining(h, 123, &resultado))
        printf("\nEncontrado: %s, Notas: %.1f, %.1f, %.1f\n", resultado.nome, resultado.n1, resultado.n2, resultado.n3);
    else
        printf("\nAluno nao encontrado....\n");

    // Verifica a existência de um aluno com matrícula 11
    if (search_SeparateChaining(h, 11, &resultado))
        printf("Encontrado: %s, Notas: %.1f, %.1f, %.1f\n", resultado.nome, resultado.n1, resultado.n2, resultado.n3);
    else
        printf("Student not found.\n");

    // Procura por matrícula inexistente
    if (search_SeparateChaining(h, 40, &resultado))
        printf("Encontrado: %s, Notas: %.1f, %.1f, %.1f\n", resultado.nome, resultado.n1, resultado.n2, resultado.n3);
    else
        printf("Aluno nao encontrado....\n");

    // Adiciona multiplos alunos
    struct aluno alunos[5] = {
        {8, "Aluno 1", 7.0, 8.0, 9.0},
        {19, "Aluno 2", 6.0, 7.0, 8.0},
        {29, "Aluno 3", 5.0, 6.0, 7.0},
        {15, "Aluno 4", 4.0, 5.0, 6.0},
        {14, "Aluno 5", 3.0, 4.0, 5.0}
    };
    insertMultiple_SeparateChaining(h, alunos, 5);
    printf("\nImprimindo o HashTable depois da insercao de multiplos alunos...");
    printHash(h);

    // Cria novo hash com varios alunos de uma vez
    Hash* h2 = createHashFull(alunos, 5);
    printf("\nImprimindo novo HashTable com criacao de varios alunos...");
    printHash(h2);
    
    // Libera a memória
    deleteHash(h);

    return 0;
}