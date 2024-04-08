# Trabalho de Implementação de Árvore Binária de Pesquisa (BST)

Trabalho 1 – Algoritmos e Programação: Estruturas de Dados

Objetivo: O objetivo deste trabalho é implementar as principais operações de uma Árvore Binária de Pesquisa (BST) e gerar visualizações da árvore utilizando a linguagem DOT (Graphviz).
Modalidade: Trabalho individual ou em duplas.

### Instruções:

1. Implementar as seguintes funções principais de uma BST:
• Inserção de um novo nodo.
• Busca por um valor específico na árvore.
• Caminhamentos: pré-ordem, em ordem e pós-ordem.
• Remoção de um nodo.
2. Utilizar como base os esqueletos de código disponíveis em: no repositório de exemplos da turma. Escolha entre as linguagens de programação C++ ou Java para realizar a implementação.
3. Gerar arquivos de visualização da árvore a partir do caminhamento pré-ordem. Para isso, utilize a linguagem DOT (Graphviz).

Entrega: O trabalho deverá ser entregue até a data especificada pelo professor, conforme as instruções fornecidas em sala de aula e no ambiente virtual de aprendizagem. No repositório do projeto, copie seus diretórios \dotFiles e \svgFiles com os exemplos que você gerou para testar.

Observações: Certifique-se de seguir as boas práticas de programação, comentar o código adequadamente e realizar testes para verificar o funcionamento correto das implementações.

### Comando para Baixar o Repositório:
```
git clone -b algoritmos-estrutura-dados/binary-tree https://github.com/stahlbia/algoritmos-unisinos.git --single-branch
```

### Procedimento para Visualização dos Arquivos .dot (Passado pela Professora):

1. Baixe e instale o GraphViz a partir do site oficial.
2. No diretório \bin do GraphViz, crie dois diretórios:
• \dotFiles (para armazenar os arquivos .dot gerados).
• \svgFiles (para armazenar os arquivos .svg gerados).
3. Utilizando um terminal (como cmd, Powershell, bash etc.), navegue até o diretório \bin do
GraphViz.
4. Execute o seguinte comando para gerar a visualização em formato SVG a partir do arquivo .dot
gerado:
.\dot -Tsvg dotFiles\ArvoreBinGerado.dot -o SVGFiles\ArvoreBinGerado.svg
5. Gerar pelo menos 5 exemplos de árvores, a partir de testes dos comandos de inserção e remoção
de nodos.

### Procedimento para Visualização dos Arquivos .dot (alternativo):

1. Instalar extensão **[Graphviz Interactive Preview](https://marketplace.visualstudio.com/items?itemName=tintinweb.graphviz-interactive-preview)** no VSCode
2. Abrir o arquivo .dot no VSCode e clicar ‘ctrl+shift+p’ e pesquisar por "Graphviz Interactive: Preview Graphviz / Dot (beside)"
3. A extensão irá gerar os gráficos, e após da primeira vez configurado, com dois cliques no arquivo .dot ele abre automaticamente