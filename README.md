# Ana Beatriz Stahl

Lista de exercícios 2

Questão 3: Utilizando a câmera 2D do exercício anterior, desenhe algo na tela. O que acontece
quando posicionamos os objetos? Por que é útil essa configuração?

    Quando posicionamos um objeto, suas coordenadas são mapeadas diretamente para
    essa janela de visualização. Por exemplo, se você definir um vértice com a
    coordenada (400, 300), ele será desenhado no centro da janela, já que esta janela
    tem 800 pixels de largura e 600 pixels de altura. Cada unidade em seu código é
    mapeada diretamente para um pixel da tela, o que torna o posicionamento dos objetos
    mais intuitivo no contexto de gráficos 2D.
    Essa configuração é útil porque transforma o sistema de coordenadas da janela para
    um modelo cartesiano tradicional, onde é fácil visualizar e posicionar os objetos. Para
    desenvolvedores que estão trabalhando com gráficos 2D, como jogos ou interfaces,
    essa configuração de câmera ortográfica é prática, pois elimina a necessidade de
    transformar coordenadas manualmente. Além disso, ela simplifica o desenvolvimento,
    permitindo que os objetos sejam posicionados com base em pixels da janela, o que é
    comum em interfaces gráficas.
    