# Ana Beatriz Stahl

## Respostas Lista de Exercícios 1

### Questões Teóricas:

1. **O que é a GLSL? Quais os dois tipos de shaders são obrigatórios no pipeline programável
da versão atual que trabalhamos em aula e o que eles processam?** <br>
  GLSL significa OpenGL Shading Language, é adaptado para uso com gráficos e contém recursos úteis voltados especificamente para manipulação de vetores e matrizes. Os tipos de shaders obrigatórios no pipeline programável são o Vertex e o Fragment, sendo o Vertex responsável por processar cada vértice separadamente e o Fragment por processar cada fragmento separadamente.

3. **O que são primitivas gráficas? Como fazemos o armazenamento dos vértices na OpenGL?** <br>
  Primitivas gráficas são os componentes básicos usados para construir e representar imagens e formas na tela. O armazenamento dos vértices pode ser feito através de arrays, buffers, VAOs, VBOs e EBOs.

3. **Explique o que é VBO, VAO e EBO, e como se relacionam (se achar mais fácil, pode fazer
um gráfico representando a relação entre eles).** <br>
  São três tipos de objetos que ajudam a otimizar e organizar o armazenamento e a gestão dos dados dos vértices. VBOs armazenam dados dos vértices, EBOs armazenam índices para reutilizar vértices e definir a ordem deles, e VAOs encapsulam a configuração dos VBOs e EBOs para facilitar o gerenciamento e a renderização.
