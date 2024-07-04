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
Tempo levado para rodar o algoritmo: 38.61 min

### Scenario: sorted
| Array Size | Bubble Sort | Insertion Sort | Selection Sort | Heap Sort | Shell Sort | Merge Sort | Quick Sort |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 128 | 652000.00 | 22666.67 | 510375.00 | 407875.00 | 99625.00 | 248428.57 | 1305142.86 |
| 256 | 2506333.33 | 41500.00 | 1899125.00 | 941875.00 | 225625.00 | 522111.11 | 4781444.44 |
| 512 | 9496500.00 | 73285.71 | 7725750.00 | 2023857.14 | 508111.11 | 986571.43 | 17503714.29 |
| 1024 | 39670500.00 | 168444.44 | 29858375.00 | 4241500.00 | 1202888.89 | 2260000.00 | 69724000.00 |
| 2048 | 157387666.67 | 344875.00 | 116722166.67 | 9549111.11 | 2632000.00 | 4939714.29 | 275583142.86 |
| 4096 | 628203500.00 | 659625.00 | 449596000.00 | 22133571.43 | 5661833.33 | 10430200.00 | 1082753714.29 |
| 8192 | 2530895857.14 | 1229428.57 | 1794261714.29 | 48605000.00 | 13427166.67 | 21819857.14 | 4405806000.00 |
| 16384 | 10289461500.00 | 2296857.14 | 7089994777.78 | 101457857.14 | 26867428.57 | 46270500.00 | 17877097222.22 |

### Scenario: reversed
| Array Size | Bubble Sort | Insertion Sort | Selection Sort | Heap Sort | Shell Sort | Merge Sort | Quick Sort |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 128 | 1682500.00 | 1022888.89 | 515750.00 | 342428.57 | 167428.57 | 239500.00 | 833625.00 |
| 256 | 6628000.00 | 3921428.57 | 1788285.71 | 763428.57 | 367500.00 | 502555.56 | 3110888.89 |
| 512 | 25968250.00 | 15776714.29 | 8290600.00 | 1692555.56 | 915285.71 | 1029750.00 | 12225444.44 |
| 1024 | 107261222.22 | 62621375.00 | 31964714.29 | 3569625.00 | 1900375.00 | 2042571.43 | 49170833.33 |
| 2048 | 431140777.78 | 248578666.67 | 120658375.00 | 9073000.00 | 4393000.00 | 5130500.00 | 188629285.71 |
| 4096 | 1752864571.43 | 986707625.00 | 464831714.29 | 20007833.33 | 10074750.00 | 10898166.67 | 723077714.29 |
| 8192 | 7112821000.00 | 4011627428.57 | 1865266444.44 | 43008285.71 | 23392285.71 | 21769375.00 | 2872720571.43 |
| 16384 | 28806811625.00 | 16075550285.71 | 7440618600.00 | 88883444.44 | 46131142.86 | 45028750.00 | 11539782571.43 |

### Scenario: random_unique
| Array Size | Bubble Sort | Insertion Sort | Selection Sort | Heap Sort | Shell Sort | Merge Sort | Quick Sort |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 128 | 1174666.67 | 536285.71 | 515714.29 | 391000.00 | 211333.33 | 275833.33 | 162400.00 |
| 256 | 4696500.00 | 2153571.43 | 1898666.67 | 877500.00 | 581250.00 | 577333.33 | 364375.00 |
| 512 | 19499571.43 | 8861800.00 | 7947333.33 | 1842857.14 | 1551428.57 | 1257375.00 | 801222.22 |
| 1024 | 77524888.89 | 33024333.33 | 30470875.00 | 3879000.00 | 4540333.33 | 2681166.67 | 1828875.00 |
| 2048 | 306545666.67 | 130748125.00 | 116408555.56 | 9442555.56 | 12019500.00 | 5862428.57 | 3505666.67 |
| 4096 | 1209511555.56 | 515194750.00 | 459860000.00 | 22056857.14 | 29789800.00 | 12724750.00 | 8153142.86 |
| 8192 | 4900560222.22 | 2038118250.00 | 1863029857.14 | 45306250.00 | 77362222.22 | 25741285.71 | 17763333.33 |
| 16384 | 19793286777.78 | 8137714428.57 | 7669245333.33 | 95769666.67 | 169911400.00 | 53401400.00 | 36016750.00 |

### Scenario: random_repeated
| Array Size | Bubble Sort | Insertion Sort | Selection Sort | Heap Sort | Shell Sort | Merge Sort | Quick Sort |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 128 | 1234444.44 | 576750.00 | 516333.33 | 383666.67 | 232125.00 | 276333.33 | 176888.89 |
| 256 | 4513125.00 | 1995500.00 | 1944000.00 | 891500.00 | 521142.86 | 597777.78 | 388111.11 |
| 512 | 19574111.11 | 8651833.33 | 7736571.43 | 1957833.33 | 1633000.00 | 1277142.86 | 814000.00 |
| 1024 | 78509222.22 | 33030285.71 | 30521125.00 | 4289375.00 | 3416333.33 | 2439714.29 | 1640600.00 |
| 2048 | 304535000.00 | 127666000.00 | 115899333.33 | 10065833.33 | 13992428.57 | 5676200.00 | 3950000.00 |
| 4096 | 1219007500.00 | 512473285.71 | 454173833.33 | 21480666.67 | 28282375.00 | 12868000.00 | 8262857.14 |
| 8192 | 4903319833.33 | 2019305833.33 | 1798189857.14 | 46645000.00 | 87383000.00 | 25585500.00 | 16334666.67 |
| 16384 | 19799060666.67 | 8227943666.67 | 7314635000.00 | 94459888.89 | 180534000.00 | 52551000.00 | 36023285.71 |