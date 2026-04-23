# Exercício Grau A – Seleção e Transformações em Objetos 3D

## Equipe

- Ana Beatriz Stahl

---

## Descrição do Projeto

Este programa foi desenvolvido como parte da disciplina *Processamento Gráfico: Computação Gráfica e Aplicações* da Unisinos (2026/1).
O objetivo é demonstrar a exibição de múltiplos modelos 3D carregados de arquivos `.obj`, com suporte a seleção individual de objetos e aplicação interativa de transformações (translação, rotação e escala) via teclado.
A câmera é controlada por teclado e mouse, permitindo navegar livremente pela cena.

---

## Estrutura do Projeto

| Arquivo      | Descrição                                                                                      |
| ------------ | ---------------------------------------------------------------------------------------------- |
| `main.cpp`   | Ponto de entrada; loop principal, carregamento dos modelos, renderização e tratamento de input |
| `Camera.h`   | Declaração da classe de câmera FPS                                                             |
| `Camera.cpp` | Implementação da câmera (movimento por teclado e rotação por mouse)                            |

Os shaders (vertex e fragment) estão embutidos em `main.cpp` como strings GLSL.

---

## Informações Técnicas

- **Linguagem:** C++17
- **API Gráfica:** OpenGL (GLSL #version 410 core)
- **Dependências:** GLFW 3.4, GLAD, GLM (baixadas automaticamente via CMake FetchContent)
- **Compilador:** GCC/MinGW (Windows) ou Clang/GCC (macOS/Linux)
- **Build system:** CMake 3.10+
- **Plataforma-alvo:** macOS

---

## Instruções de Compilação

### Pré-requisitos

- [CMake 3.10+](https://cmake.org/download/)
- Compilador C++17 (GCC via MSYS2/MinGW no Windows, ou Clang/GCC no macOS/Linux)
- [Git](https://git-scm.com/downloads) (necessário para o CMake FetchContent baixar as dependências)
- Arquivos da GLAD já presentes no repositório em `include/glad/` e `common/glad.c`

### Passos

A partir da **raiz do repositório**:

```sh
cmake -S . -B build
cmake --build build
```

O executável `ExercicioOBJ` (ou `ExercicioOBJ.exe` no Windows) será gerado dentro de `build/`.

### Executando

O programa usa caminhos relativos para carregar os modelos `.obj`, portanto **deve ser executado a partir do diretório `build/`**:

```sh
cd build
./ExercicioOBJ       # macOS / Linux
ExercicioOBJ.exe     # Windows
```

---

## Exemplo de Uso / Controles

| Tecla/Ação          | Efeito                                                          |
| ------------------- | --------------------------------------------------------------- |
| `TAB`               | Seleciona o próximo objeto (cíclico)                            |
| `X`                 | Ativa/desativa rotação contínua no eixo X do objeto selecionado |
| `Y`                 | Ativa/desativa rotação contínua no eixo Y do objeto selecionado |
| `Z`                 | Ativa/desativa rotação contínua no eixo Z do objeto selecionado |
| Seta `←` / Seta `→` | Translada o objeto selecionado no eixo X                        |
| Seta `↑` / Seta `↓` | Translada o objeto selecionado no eixo Z                        |
| `Q` / `E`           | Translada o objeto selecionado no eixo Y                        |
| `+` / `=`           | Aumenta a escala uniforme do objeto selecionado                 |
| `-`                 | Diminui a escala uniforme do objeto selecionado                 |
| `W` `A` `S` `D`     | Move a câmera (frente/esquerda/trás/direita)                    |
| Mouse               | Rotaciona a câmera (look around)                                |
| `P`                 | Alterna entre projeção perspectiva e ortográfica                |
| `ESC`               | Fecha a aplicação                                               |

O objeto atualmente selecionado é destacado com um contorno wireframe amarelo.
O título da janela exibe o nome e o índice do objeto selecionado.

Os três modelos carregados são:

- `Suzanne.obj` — posicionado à esquerda
- `SuzanneSubdiv1.obj` — posicionado ao centro
- `Cube.obj` — posicionado à direita

---

## Screenshots / Vídeo de Gameplay

<details>
  <summary>🎬 Vídeo</summary>
  
  ![complete game demo in a video](../../docs/video/complete-demo.mp4)

</details>

---

## Checklist de Requisitos

- [x] Leitura de arquivos `.obj` (carregamento hardcoded de 3 modelos)
- [x] Exibição de mais de um objeto na tela, organizados em linha
- [x] Seleção cíclica de objetos via tecla (`TAB`)
- [x] Rotação no eixo X, Y e Z do objeto selecionado
- [x] Translação nos eixos X, Y e Z do objeto selecionado
- [x] Escala uniforme do objeto selecionado
- [x] Destaque visual do objeto selecionado (wireframe amarelo por cima — desafio)
- [x] Câmera FPS com teclado e mouse

---

## Referências

- Repositório de exemplos da disciplina — [fellowsheep/CGCCHIB](https://github.com/fellowsheep/CGCCHIB)
- [LearnOpenGL](https://learnopengl.com) — tutoriais de OpenGL moderna
- [GLFW Input Guide](https://www.glfw.org/docs/latest/input_guide.html) — tratamento de eventos de teclado e mouse
- [GLM Documentation](https://glm.g-truc.net/0.9.9/index.html) — biblioteca de matemática para OpenGL
