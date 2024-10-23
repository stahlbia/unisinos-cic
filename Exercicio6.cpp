#include <iostream>
#include <cmath>
#include <vector>
#include <string>
#include <assert.h>

#define M_PI 3.14159265358979323846 // Definindo M_PI

// GLAD
#include <glad/glad.h>

// GLFW
#include <GLFW/glfw3.h>

// Protótipo da função de callback de teclado
void key_callback(GLFWwindow* window, int key, int scancode, int action, int mode);

// Protótipos das funções
int setupShader();
int setupCircleGeometry(int segments, float radius);
int setupOctagonGeometry(float radius);
int setupPentagonGeometry(float radius);
int setupPacManGeometry(float radius);
int setupPizzaSliceGeometry(float radius);

// Dimensões da janela
const GLuint WIDTH = 800, HEIGHT = 600;

// Código fonte do Vertex Shader (em GLSL)
const GLchar* vertexShaderSource = "#version 400\n"
"layout (location = 0) in vec3 position;\n"
"void main()\n"
"{\n"
"    gl_Position = vec4(position.x, position.y, position.z, 1.0);\n"
"}\0";

// Código fonte do Fragment Shader (em GLSL)
const GLchar* fragmentShaderSource = "#version 400\n"
"uniform vec4 inputColor;\n"
"out vec4 color;\n"
"void main()\n"
"{\n"
"    color = inputColor;\n"
"}\n\0";

int main()
{
    // Inicialização da GLFW
	glfwInit();

	// Criação da janela GLFW
	GLFWwindow* window = glfwCreateWindow(WIDTH, HEIGHT, "Desenha Formas Geométricas! -- Ana Beatriz", nullptr, nullptr);
	glfwMakeContextCurrent(window);

	// Fazendo o registro da função de callback para a janela GLFW
	glfwSetKeyCallback(window, key_callback);

    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
    {
        std::cout << "Failed to initialize GLAD" << std::endl;
    }

    // Compilando e buildando o programa de shader
    GLuint shaderID = setupShader();
    
    // Configurando as formas
    GLuint circleVAO = setupCircleGeometry(100, 0.5f); // Círculo
    GLuint octagonVAO = setupOctagonGeometry(0.5f); // Octógono
    GLuint pentagonVAO = setupPentagonGeometry(0.5f); // Pentágono
    GLuint pacmanVAO = setupPacManGeometry(0.5f); // Pac-Man
    GLuint pizzaSliceVAO = setupPizzaSliceGeometry(0.5f); // Fatia de pizza

    GLint colorLoc = glGetUniformLocation(shaderID, "inputColor");
    glUseProgram(shaderID);

    while (!glfwWindowShouldClose(window))
    {
        glfwPollEvents();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        // Desenha o círculo
        glBindVertexArray(circleVAO);
        glUniform4f(colorLoc, 1.0f, 0.0f, 0.0f, 1.0f); // Cor do círculo (vermelho)
        glDrawArrays(GL_LINE_LOOP, 0, 100); // Desenha o círculo

        // Desenha o octógono
        glBindVertexArray(octagonVAO);
        glUniform4f(colorLoc, 0.0f, 1.0f, 0.0f, 1.0f); // Cor do octógono (verde)
        glDrawArrays(GL_LINE_LOOP, 0, 8); // Desenha o octógono

        // Desenha o pentágono
        glBindVertexArray(pentagonVAO);
        glUniform4f(colorLoc, 0.0f, 0.0f, 1.0f, 1.0f); // Cor do pentágono (azul)
        glDrawArrays(GL_LINE_LOOP, 0, 5); // Desenha o pentágono

        // Desenha o Pac-Man
        glBindVertexArray(pacmanVAO);
        glUniform4f(colorLoc, 1.0f, 1.0f, 0.0f, 1.0f); // Cor do Pac-Man (amarelo)
        glDrawArrays(GL_TRIANGLE_FAN, 0, 30); // Desenha o Pac-Man

        // Desenha a fatia de pizza
        glBindVertexArray(pizzaSliceVAO);
        glUniform4f(colorLoc, 1.0f, 0.8f, 0.0f, 1.0f); // Cor da fatia de pizza (laranja)
        glDrawArrays(GL_TRIANGLE_FAN, 0, 30); // Desenha a fatia de pizza

        glBindVertexArray(0);

        glfwSwapBuffers(window);
    }

    glDeleteVertexArrays(1, &circleVAO);
    glDeleteVertexArrays(1, &octagonVAO);
    glfwTerminate();
    return 0;
}

void key_callback(GLFWwindow* window, int key, int scancode, int action, int mode)
{
    if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
        glfwSetWindowShouldClose(window, GL_TRUE);
}

int setupShader()
{
    // Vertex shader
    GLuint vertexShader = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vertexShader, 1, &vertexShaderSource, NULL);
    glCompileShader(vertexShader);
    GLint success;
    GLchar infoLog[512];
    glGetShaderiv(vertexShader, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(vertexShader, 512, NULL, infoLog);
        std::cout << "ERROR::SHADER::VERTEX::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

    // Fragment shader
    GLuint fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragmentShader, 1, &fragmentShaderSource, NULL);
    glCompileShader(fragmentShader);
    glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(fragmentShader, 512, NULL, infoLog);
        std::cout << "ERROR::SHADER::FRAGMENT::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

    // Linkando os shaders
    GLuint shaderProgram = glCreateProgram();
    glAttachShader(shaderProgram, vertexShader);
    glAttachShader(shaderProgram, fragmentShader);
    glLinkProgram(shaderProgram);
    glGetProgramiv(shaderProgram, GL_LINK_STATUS, &success);
    if (!success) {
        glGetProgramInfoLog(shaderProgram, 512, NULL, infoLog);
        std::cout << "ERROR::SHADER::PROGRAM::LINKING_FAILED\n" << infoLog << std::endl;
    }
    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);

    return shaderProgram;
}

int setupCircleGeometry(int segments, float radius)
{
    std::vector<GLfloat> vertices;
    for (int i = 0; i < segments; ++i) {
        float angle = 2.0f * M_PI * float(i) / float(segments);
        float x = radius * cos(angle);
        float y = radius * sin(angle);
        vertices.push_back(x);
        vertices.push_back(y);
        vertices.push_back(0.0f); // z
    }

    GLuint VBO, VAO;
    glGenBuffers(1, &VBO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(GLfloat), vertices.data(), GL_STATIC_DRAW);

    glGenVertexArrays(1, &VAO);
    glBindVertexArray(VAO);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (GLvoid*)0);
    glEnableVertexAttribArray(0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    return VAO;
}

int setupOctagonGeometry(float radius)
{
    std::vector<GLfloat> vertices;
    for (int i = 0; i < 8; ++i) {
        float angle = 2.0f * M_PI * float(i) / 8.0f; // 8 segmentos para o octógono
        float x = radius * cos(angle);
        float y = radius * sin(angle);
        vertices.push_back(x);
        vertices.push_back(y);
        vertices.push_back(0.0f); // z
    }

    GLuint VBO, VAO;
    glGenBuffers(1, &VBO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(GLfloat), vertices.data(), GL_STATIC_DRAW);

    glGenVertexArrays(1, &VAO);
    glBindVertexArray(VAO);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (GLvoid*)0);
    glEnableVertexAttribArray(0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    return VAO;
}

int setupPentagonGeometry(float radius)
{
    std::vector<GLfloat> vertices;
    for (int i = 0; i < 5; ++i) {
        float angle = 2.0f * M_PI * float(i) / 5.0f; // 5 segmentos para o pentágono
        float x = radius * cos(angle);
        float y = radius * sin(angle);
        vertices.push_back(x);
        vertices.push_back(y);
        vertices.push_back(0.0f); // z
    }

    GLuint VBO, VAO;
    glGenBuffers(1, &VBO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(GLfloat), vertices.data(), GL_STATIC_DRAW);

    glGenVertexArrays(1, &VAO);
    glBindVertexArray(VAO);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (GLvoid*)0);
    glEnableVertexAttribArray(0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    return VAO;
}

int setupPacManGeometry(float radius)
{
    // Converter ângulos para radianos
    // float startAngle = glm::radians(-15.0f);
    // float endAngle = glm::radians(15.0f);
    float startAngle = -30.0f * M_PI / 180.0f;
    float endAngle = 30.0f * M_PI / 180.0f;

    std::vector<GLfloat> vertices;
    vertices.push_back(0.0f); // Centro
    vertices.push_back(0.0f);
    vertices.push_back(0.0f); // z

    int segments = 30;
    // Criar os vértices ao longo do círculo, excluindo o intervalo de abertura
    for (int i = 0; i <= segments; ++i) {
        float angle = 2.0f * M_PI * float(i) / float(segments);
        
        // Excluir os vértices dentro do intervalo da "boca"
        if (angle >= startAngle && angle <= endAngle) continue;

        float x = radius * cos(angle);
        float y = radius * sin(angle);
        vertices.push_back(x);
        vertices.push_back(y);
        vertices.push_back(0.0f); // z
    }

    GLuint VBO, VAO;
    glGenBuffers(1, &VBO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(GLfloat), vertices.data(), GL_STATIC_DRAW);

    glGenVertexArrays(1, &VAO);
    glBindVertexArray(VAO);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (GLvoid*)0);
    glEnableVertexAttribArray(0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    return VAO;
}

int setupPizzaSliceGeometry(float radius)
{
    std::vector<GLfloat> vertices;
    vertices.push_back(0.0f); // Centro
    vertices.push_back(0.0f);
    vertices.push_back(0.0f); // z

    int segments = 30;
    for (int i = 0; i <= segments; ++i) {
        float angle = (3.0f / 4.0f) * M_PI + (i * (M_PI / 4.0f) / segments); // ângulo de 45 graus
        float x = radius * cos(angle);
        float y = radius * sin(angle);
        vertices.push_back(x);
        vertices.push_back(y);
        vertices.push_back(0.0f); // z
    }

    GLuint VBO, VAO;
    glGenBuffers(1, &VBO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(GLfloat), vertices.data(), GL_STATIC_DRAW);

    glGenVertexArrays(1, &VAO);
    glBindVertexArray(VAO);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (GLvoid*)0);
    glEnableVertexAttribArray(0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    return VAO;
}