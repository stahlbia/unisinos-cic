# ANÁLISE COMPARATIVA DOS MÉTODOS DE ORDENAÇÃO

## Objetivo
O objetivo deste trabalho é realizar uma análise comparativa de tempo dos principais métodos
de ordenação apresentados em aula. Os métodos a serem estudados são: Bubble Sort, Insertion Sort,
Selection Sort, Heap Sort, Shell Sort, Merge Sort e Quick Sort.

## Instruções
1. Os estudantes devem implementar os algoritmos de ordenação mencionados.
2. Para cada método de ordenação, os estudantes devem realizar testes de desempenho em
diferentes cenários. Os testes devem ser conduzidos em arrays de diferentes tamanhos e em quatro
cenários distintos:
    - Array ordenado em ordem crescente, sem valores repetidos;
    - Array ordenado em ordem decrescente, sem valores repetidos;
    - Array aleatória sem valores repetidos; e
    - Array aleatória com valores repetidos.
3. Após a geração dos cenários (em memória ou em arquivo), deverá ser analisado para cada método
de ordenação e tamanho “n” do array (primeira coluna da Tabela 1), observando que os métodos
devem receber o mesmo array original. O tempo de processamento deverá ser computado em
nanossegundos.
4. Os resultados dos testes devem ser registrados em tabelas para cada cenário, contendo os tempos
de execução de cada método de ordenação para diferentes tamanhos de array.
5. Deverá ser realizado 10 execuções armazenando o tempo de processamento para cada cenário,
método e tamanho de array. A partir daí, calcula-se a média destas 10 execuções. Feito isso, calcula-
UNIVERSIDADE DO VALE DO RIO DOS SINOS
Escola Politécnica
Ciência da Computação
se com base nestas 10 execuções a variância da amostra e, consequentemente, o desvio padrão.
Com base no desvio padrão, será desconsiderado os tempos destas 10 execuções que estejam fora
da média ± desvio padrão. Por fim, calcula-se o tempo médio somente dos valores que estiverem
dentro desta faixa.

## Resultado
Tempo levado para rodar o algoritmo: 10.33 min

### Scenario: sorted
| Array Size | Bubble Sort | Insertion Sort | Selection Sort | Heap Sort | Shell Sort | Merge Sort | Quick Sort |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 128 | 661625.00 | 24375.00 | 532875.00 | 415750.00 | 104500.00 | 253000.00 | 1323222.22 |
| 256 | 2538833.33 | 42000.00 | 1946666.67 | 974875.00 | 232000.00 | 529375.00 | 4889400.00 |
| 512 | 10266400.00 | 77444.44 | 8647375.00 | 2218500.00 | 566750.00 | 1170750.00 | 19425555.56 |
| 1024 | 43702285.71 | 155125.00 | 33683142.86 | 4829714.29 | 1239500.00 | 2520285.71 | 77216166.67 |
| 2048 | 177184857.14 | 332750.00 | 127434125.00 | 10394777.78 | 2602142.86 | 5466857.14 | 315987000.00 |
| 4096 | 672967666.67 | 669000.00 | 478095500.00 | 22839285.71 | 6304000.00 | 11195333.33 | 1149433166.67 |
| 8192 | 2681853125.00 | 1239250.00 | 1873683500.00 | 49752571.43 | 13830857.14 | 23138200.00 | 4609824333.33 |

### Scenario: reversed
| Array Size | Bubble Sort | Insertion Sort | Selection Sort | Heap Sort | Shell Sort | Merge Sort | Quick Sort |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 128 | 1688000.00 | 1062444.44 | 542750.00 | 350555.56 | 175000.00 | 246000.00 | 872400.00 |
| 256 | 6729714.29 | 4231000.00 | 2045666.67 | 809428.57 | 391111.11 | 515571.43 | 3159777.78 |
| 512 | 27893000.00 | 16156200.00 | 8166500.00 | 1851333.33 | 984166.67 | 1123666.67 | 13305444.44 |
| 1024 | 107977888.89 | 63681142.86 | 33195000.00 | 3634250.00 | 2072250.00 | 2570142.86 | 50883571.43 |
| 2048 | 456889200.00 | 259919250.00 | 125005000.00 | 9132428.57 | 4916166.67 | 4872400.00 | 193597222.22 |
| 4096 | 1866411714.29 | 1046710800.00 | 494302250.00 | 21356200.00 | 10324857.14 | 10672571.43 | 765711625.00 |
| 8192 | 7541053625.00 | 4274328500.00 | 1986597125.00 | 44542625.00 | 23058000.00 | 21922571.43 | 3052390666.67 |

### Scenario: random_unique
| Array Size | Bubble Sort | Insertion Sort | Selection Sort | Heap Sort | Shell Sort | Merge Sort | Quick Sort |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 128 | 1204500.00 | 569777.78 | 540000.00 | 397428.57 | 222333.33 | 292333.33 | 173625.00 |
| 256 | 4679375.00 | 2198000.00 | 1984428.57 | 913625.00 | 571555.56 | 604333.33 | 371750.00 |
| 512 | 20016000.00 | 8727857.14 | 8637500.00 | 2044857.14 | 1573625.00 | 1317000.00 | 817571.43 |
| 1024 | 78008125.00 | 33790000.00 | 30915000.00 | 4175333.33 | 4113833.33 | 2858400.00 | 1699857.14 |
| 2048 | 307480142.86 | 129785285.71 | 120855375.00 | 10358142.86 | 11978285.71 | 6033166.67 | 3894875.00 |
| 4096 | 1270937250.00 | 536825500.00 | 481148875.00 | 22257142.86 | 38561500.00 | 12429800.00 | 8371888.89 |
| 8192 | 5184225142.86 | 2149518666.67 | 2008340571.43 | 47201600.00 | 81286000.00 | 25941857.14 | 18405600.00 |

### Scenario: random_repeated
| Array Size | Bubble Sort | Insertion Sort | Selection Sort | Heap Sort | Shell Sort | Merge Sort | Quick Sort |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 128 | 1193000.00 | 535000.00 | 530000.00 | 395000.00 | 224555.56 | 278222.22 | 167888.89 |
| 256 | 4662777.78 | 2121375.00 | 1957250.00 | 906875.00 | 480333.33 | 588000.00 | 405750.00 |
| 512 | 20285142.86 | 9083285.71 | 8582333.33 | 2041166.67 | 1678750.00 | 1323000.00 | 835500.00 |
| 1024 | 81075750.00 | 35940888.89 | 32109250.00 | 4254750.00 | 3751500.00 | 2740500.00 | 1815333.33 |
| 2048 | 316859428.57 | 133848000.00 | 122218222.22 | 9799000.00 | 12939200.00 | 6153200.00 | 4153857.14 |
| 4096 | 1296786571.43 | 544883000.00 | 483625000.00 | 21913666.67 | 31583375.00 | 13376500.00 | 9886125.00 |
| 8192 | 5138340833.33 | 2150127500.00 | 1914917555.56 | 48110142.86 | 93161571.43 | 26873142.86 | 19319666.67 |
