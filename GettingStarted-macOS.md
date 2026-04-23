# Configuração do Ambiente no macOS

Este guia explica como configurar e executar os projetos OpenGL do repositório em um Mac com Apple Silicon (M1/M2/M3) ou Intel.

> **Atenção:** O macOS suporta OpenGL até a versão **4.1**. Os projetos originalmente usavam `#version 450` nos shaders e não ativavam o perfil Core do GLFW — ambos incompatíveis com macOS. As correções necessárias já estão aplicadas neste repositório.

---

## 1. Pré-requisitos

### Xcode Command Line Tools

Fornece o compilador Clang e o Git. Execute no terminal e siga as instruções na tela:

```sh
xcode-select --install
```

Verifique a instalação:

```sh
clang --version
git --version
```

### Homebrew

Gerenciador de pacotes para macOS. Caso não tenha instalado:

```sh
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

Após a instalação, siga as instruções exibidas no terminal para adicionar o Homebrew ao `PATH` (necessário especialmente no Apple Silicon).

### CMake

```sh
brew install cmake
```

Verifique:

```sh
cmake --version
```

---

## 2. Compilando o Projeto

Clone o repositório e entre na pasta:

```sh
git clone <url-do-repositório>
cd unisinos-cic
```

Crie o diretório de build e compile:

```sh
mkdir -p build && cd build
cmake ..
cmake --build .
```

Na primeira execução, o CMake baixa automaticamente as dependências **GLFW**, **GLM** e **stb_image** via `FetchContent`. Aguarde o processo concluir.

---

## 3. Executando os Programas

A partir da pasta `build/`, execute qualquer um dos projetos:

```sh
./Hello3D
./HelloCamera
./HelloOBJ
./HelloPhong
./ExercicioOBJ
```

---

## 4. Diferenças em relação ao Windows

### Por que o macOS exibe "OpenGL version supported 2.1" sem as correções?

Sem as dicas de contexto do GLFW, o macOS cria um contexto OpenGL legado (2.1). Para obter OpenGL 4.1 Core Profile é necessário configurar explicitamente o GLFW antes de criar a janela.

### Correções aplicadas em todos os arquivos `main.cpp`

**1. Ativar o Core Profile e Forward Compatibility no GLFW:**

```cpp
glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
#ifdef __APPLE__
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
#endif
```

O `GLFW_OPENGL_FORWARD_COMPAT` é obrigatório no macOS para usar o Core Profile. Sem ele, a criação do contexto falha ou permanece em modo legado.

**2. Versão dos shaders GLSL de `450` para `410 core`:**

```glsl
// Antes (não suportado no macOS):
#version 450

// Depois (máximo suportado no macOS):
#version 410 core
```

O macOS suporta GLSL até a versão 4.10. O uso de `#version 450` causava falha na compilação dos shaders e consequente `segmentation fault`.

---

## 5. Integração com VS Code (opcional)

Instale as extensões:

- **C/C++ Extension Pack** — IntelliSense e depuração
- **CMake Tools** — integração com o sistema de build

Abra a pasta raiz do repositório com `File → Open Folder`. O VS Code detectará o `CMakeLists.txt` automaticamente.

Para compilar pela interface, pressione `Ctrl+Shift+P` (ou `Cmd+Shift+P`) e execute:

1. `CMake: Select a Kit` — escolha `Clang` ou `Apple Clang`
2. `CMake: Configure`
3. `CMake: Build`

---

## 6. Adicionando Novos Exercícios

Crie o arquivo fonte em `src/NomeDoExercicio/main.cpp` e adicione o nome à lista no `CMakeLists.txt`:

```cmake
set(EXERCISES
    Hello3D
    HelloCamera
    HelloOBJ
    HelloPhong
    ExercicioOBJ
    NomeDoExercicio
)
```

Execute `cmake --build .` novamente para compilar o novo executável.

Lembre-se de usar `#version 410 core` nos shaders para manter compatibilidade com o macOS.
