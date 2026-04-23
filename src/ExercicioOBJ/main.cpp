/* Exercício Grau A – Computação Gráfica – Unisinos 2026
 * Seleção e aplicação de transformações em objetos 3D (.obj)
 * Autora: Ana Beatriz Stahl
 */

#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <vector>

using namespace std;

#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include "Camera.h"

void key_callback(GLFWwindow* window, int key, int scancode, int action, int mode);
void mouse_callback(GLFWwindow* window, double xpos, double ypos);
int  setupShader();
int  loadSimpleOBJ(const string& filePath, int& nVertices, glm::vec3 color);

const GLuint WIDTH = 900, HEIGHT = 600;

// Vertex shader: suporta override de cor para o wireframe de seleção
const GLchar* vertexShaderSource = R"glsl(
#version 410 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;
uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;
uniform bool useColorOverride;
uniform vec3 colorOverride;
out vec4 finalColor;
void main()
{
    gl_Position = projection * view * model * vec4(position, 1.0);
    finalColor = useColorOverride ? vec4(colorOverride, 1.0) : vec4(color, 1.0);
}
)glsl";

const GLchar* fragmentShaderSource = R"glsl(
#version 410 core
in  vec4 finalColor;
out vec4 color;
void main()
{
    color = finalColor;
}
)glsl";

struct SceneObject {
    GLuint     VAO;
    int        nVertices;
    string     name;
    glm::vec3  position;
    glm::vec3  rotation; // accumulated angles in radians
    glm::vec3  scale;
    bool       spinX, spinY, spinZ;
};

// Globals accessible from callbacks
vector<SceneObject> objects;
int  selectedIdx = 0;
bool usePerspective = true;

Camera camera(glm::vec3(0.0f, 1.5f, -8.0f), glm::vec3(0.0f, 1.0f, 0.0f), 90.0f, -10.0f);
float deltaTime  = 0.0f;
float lastFrame  = 0.0f;
float lastMouseX = WIDTH  / 2.0f;
float lastMouseY = HEIGHT / 2.0f;
bool  firstMouse = true;

const float TRANS_SPEED = 3.0f;
const float SCALE_SPEED = 1.0f;
const float ROT_SPEED   = 2.0f;

int main()
{
    glfwInit();

    // macOS requires forward-compat core profile; uncomment if needed:
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    #ifdef __APPLE__
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    #endif

    GLFWwindow* window = glfwCreateWindow(WIDTH, HEIGHT, "ExercicioOBJ", nullptr, nullptr);
    glfwMakeContextCurrent(window);

    glfwSetKeyCallback(window, key_callback);
    glfwSetCursorPosCallback(window, mouse_callback);
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress)) {
        cerr << "Failed to initialize GLAD" << endl;
        return -1;
    }

    cout << "Renderer: " << glGetString(GL_RENDERER) << endl;
    cout << "OpenGL:   " << glGetString(GL_VERSION)  << endl;
    cout << "\n=== Controles ===" << endl;
    cout << "TAB          : selecionar proximo objeto" << endl;
    cout << "X / Y / Z    : alternar rotacao no eixo (toggle)" << endl;
    cout << "Setas L/R    : transladar no eixo X" << endl;
    cout << "Setas U/D    : transladar no eixo Z" << endl;
    cout << "Q / E        : transladar no eixo Y" << endl;
    cout << "+ / -        : escala uniforme" << endl;
    cout << "WASD + mouse : mover/rotacionar camera" << endl;
    cout << "P            : alternar perspectiva/ortografica" << endl;
    cout << "ESC          : fechar\n" << endl;

    int width, height;
    glfwGetFramebufferSize(window, &width, &height);
    glViewport(0, 0, width, height);

    GLuint shaderID = setupShader();
    glUseProgram(shaderID);

    // Hardcoded: 3 objects positioned in a row
    auto addObject = [&](const string& path, const string& name,
                         glm::vec3 pos, glm::vec3 color)
    {
        SceneObject obj;
        obj.VAO       = loadSimpleOBJ(path, obj.nVertices, color);
        obj.name      = name;
        obj.position  = pos;
        obj.rotation  = glm::vec3(0.0f);
        obj.scale     = glm::vec3(1.0f);
        obj.spinX = obj.spinY = obj.spinZ = false;
        objects.push_back(obj);
    };

    addObject("../assets/Modelos3D/Suzanne.obj",       "Suzanne",       glm::vec3(-3.5f, 0.0f, 0.0f), glm::vec3(0.85f, 0.35f, 0.35f));
    addObject("../assets/Modelos3D/SuzanneSubdiv1.obj","SuzanneSubdiv1",glm::vec3( 0.0f, 0.0f, 0.0f), glm::vec3(0.35f, 0.80f, 0.35f));
    addObject("../assets/Modelos3D/Cube.obj",          "Cube",          glm::vec3( 3.5f, 0.0f, 0.0f), glm::vec3(0.35f, 0.50f, 0.90f));

    glEnable(GL_DEPTH_TEST);

    while (!glfwWindowShouldClose(window))
    {
        float currentFrame = (float)glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        glfwPollEvents();

        // Camera movement (WASD)
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) camera.processKeyboard("FORWARD",  deltaTime);
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) camera.processKeyboard("BACKWARD", deltaTime);
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) camera.processKeyboard("LEFT",     deltaTime);
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) camera.processKeyboard("RIGHT",    deltaTime);

        SceneObject& sel = objects[selectedIdx];

        // Continuous rotation on toggled axes
        if (sel.spinX) sel.rotation.x += ROT_SPEED * deltaTime;
        if (sel.spinY) sel.rotation.y += ROT_SPEED * deltaTime;
        if (sel.spinZ) sel.rotation.z += ROT_SPEED * deltaTime;

        // Translation (arrow keys = X/Z, Q/E = Y)
        if (glfwGetKey(window, GLFW_KEY_LEFT)  == GLFW_PRESS) sel.position.x -= TRANS_SPEED * deltaTime;
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) sel.position.x += TRANS_SPEED * deltaTime;
        if (glfwGetKey(window, GLFW_KEY_UP)    == GLFW_PRESS) sel.position.z += TRANS_SPEED * deltaTime;
        if (glfwGetKey(window, GLFW_KEY_DOWN)  == GLFW_PRESS) sel.position.z -= TRANS_SPEED * deltaTime;
        if (glfwGetKey(window, GLFW_KEY_Q)     == GLFW_PRESS) sel.position.y += TRANS_SPEED * deltaTime;
        if (glfwGetKey(window, GLFW_KEY_E)     == GLFW_PRESS) sel.position.y -= TRANS_SPEED * deltaTime;

        // Uniform scale (= / -)
        if (glfwGetKey(window, GLFW_KEY_EQUAL) == GLFW_PRESS)
            sel.scale += glm::vec3(SCALE_SPEED * deltaTime);
        if (glfwGetKey(window, GLFW_KEY_MINUS) == GLFW_PRESS) {
            sel.scale -= glm::vec3(SCALE_SPEED * deltaTime);
            if (sel.scale.x < 0.05f) sel.scale = glm::vec3(0.05f);
        }

        // Window title shows current selection
        glfwSetWindowTitle(window,
            ("ExercicioOBJ | [" + to_string(selectedIdx + 1) + "/" +
             to_string(objects.size()) + "] " + sel.name +
             " | TAB=proximo  X/Y/Z=rotacao  Setas=transladar  +/-=escala").c_str());

        glClearColor(0.15f, 0.15f, 0.15f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Projection
        glm::mat4 projection;
        if (usePerspective)
            projection = glm::perspective(glm::radians(45.0f), (float)WIDTH / HEIGHT, 0.1f, 100.0f);
        else
            projection = glm::ortho(-8.0f, 8.0f, -6.0f, 6.0f, 0.1f, 100.0f);
        glUniformMatrix4fv(glGetUniformLocation(shaderID, "projection"), 1, GL_FALSE, glm::value_ptr(projection));

        // View
        glm::mat4 view = camera.getViewMatrix();
        glUniformMatrix4fv(glGetUniformLocation(shaderID, "view"), 1, GL_FALSE, glm::value_ptr(view));

        for (int i = 0; i < (int)objects.size(); i++)
        {
            SceneObject& obj = objects[i];

            glm::mat4 model = glm::mat4(1.0f);
            model = glm::translate(model, obj.position);
            model = glm::rotate(model, obj.rotation.x, glm::vec3(1.0f, 0.0f, 0.0f));
            model = glm::rotate(model, obj.rotation.y, glm::vec3(0.0f, 1.0f, 0.0f));
            model = glm::rotate(model, obj.rotation.z, glm::vec3(0.0f, 0.0f, 1.0f));
            model = glm::scale(model, obj.scale);

            glUniformMatrix4fv(glGetUniformLocation(shaderID, "model"), 1, GL_FALSE, glm::value_ptr(model));
            glUniform1i(glGetUniformLocation(shaderID, "useColorOverride"), 0);

            // Solid fill
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glBindVertexArray(obj.VAO);
            glDrawArrays(GL_TRIANGLES, 0, obj.nVertices);

            // Yellow wireframe overlay for the selected object
            if (i == selectedIdx)
            {
                glUniform1i(glGetUniformLocation(shaderID, "useColorOverride"), 1);
                glUniform3f(glGetUniformLocation(shaderID, "colorOverride"), 1.0f, 1.0f, 0.0f);

                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                glEnable(GL_POLYGON_OFFSET_LINE);
                glPolygonOffset(-1.0f, -1.0f);
                glLineWidth(2.0f);

                glDrawArrays(GL_TRIANGLES, 0, obj.nVertices);

                glDisable(GL_POLYGON_OFFSET_LINE);
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            }

            glBindVertexArray(0);
        }

        glfwSwapBuffers(window);
    }

    glfwTerminate();
    return 0;
}

void key_callback(GLFWwindow* window, int key, int scancode, int action, int mode)
{
    if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
        glfwSetWindowShouldClose(window, GL_TRUE);

    if (key == GLFW_KEY_TAB && action == GLFW_PRESS)
        selectedIdx = (selectedIdx + 1) % (int)objects.size();

    if (key == GLFW_KEY_P && action == GLFW_PRESS)
        usePerspective = !usePerspective;

    if (action == GLFW_PRESS)
    {
        SceneObject& sel = objects[selectedIdx];

        if (key == GLFW_KEY_X) {
            sel.spinX = !sel.spinX;
            if (sel.spinX) { sel.spinY = false; sel.spinZ = false; }
        }
        if (key == GLFW_KEY_Y) {
            sel.spinY = !sel.spinY;
            if (sel.spinY) { sel.spinX = false; sel.spinZ = false; }
        }
        if (key == GLFW_KEY_Z) {
            sel.spinZ = !sel.spinZ;
            if (sel.spinZ) { sel.spinX = false; sel.spinY = false; }
        }
    }
}

void mouse_callback(GLFWwindow* window, double xpos, double ypos)
{
    if (firstMouse) {
        lastMouseX = (float)xpos;
        lastMouseY = (float)ypos;
        firstMouse = false;
    }
    float xoffset =  (float)xpos - lastMouseX;
    float yoffset =  lastMouseY  - (float)ypos; // reversed: y goes bottom-to-top
    lastMouseX = (float)xpos;
    lastMouseY = (float)ypos;
    camera.processMouseMovement(xoffset, yoffset);
}

int setupShader()
{
    auto compileShader = [](GLenum type, const GLchar* src) {
        GLuint shader = glCreateShader(type);
        glShaderSource(shader, 1, &src, NULL);
        glCompileShader(shader);
        GLint success; GLchar log[512];
        glGetShaderiv(shader, GL_COMPILE_STATUS, &success);
        if (!success) {
            glGetShaderInfoLog(shader, 512, NULL, log);
            cerr << "Shader compile error:\n" << log << endl;
        }
        return shader;
    };

    GLuint vs = compileShader(GL_VERTEX_SHADER,   vertexShaderSource);
    GLuint fs = compileShader(GL_FRAGMENT_SHADER, fragmentShaderSource);

    GLuint prog = glCreateProgram();
    glAttachShader(prog, vs);
    glAttachShader(prog, fs);
    glLinkProgram(prog);

    GLint success; GLchar log[512];
    glGetProgramiv(prog, GL_LINK_STATUS, &success);
    if (!success) {
        glGetProgramInfoLog(prog, 512, NULL, log);
        cerr << "Shader link error:\n" << log << endl;
    }
    glDeleteShader(vs);
    glDeleteShader(fs);
    return prog;
}

int loadSimpleOBJ(const string& filePath, int& nVertices, glm::vec3 color)
{
    vector<glm::vec3> vertices;
    vector<glm::vec2> texCoords;
    vector<glm::vec3> normals;
    vector<GLfloat>   vBuffer;

    ifstream file(filePath);
    if (!file.is_open()) {
        cerr << "Erro ao abrir: " << filePath << endl;
        return -1;
    }

    string line;
    while (getline(file, line)) {
        istringstream ss(line);
        string token;
        ss >> token;

        if (token == "v") {
            glm::vec3 v; ss >> v.x >> v.y >> v.z;
            vertices.push_back(v);
        } else if (token == "vt") {
            glm::vec2 vt; ss >> vt.s >> vt.t;
            texCoords.push_back(vt);
        } else if (token == "vn") {
            glm::vec3 vn; ss >> vn.x >> vn.y >> vn.z;
            normals.push_back(vn);
        } else if (token == "f") {
            string word;
            while (ss >> word) {
                int vi = 0, ti = 0, ni = 0;
                istringstream ws(word);
                string idx;
                if (getline(ws, idx, '/')) vi = !idx.empty() ? stoi(idx) - 1 : 0;
                if (getline(ws, idx, '/')) ti = !idx.empty() ? stoi(idx) - 1 : 0;
                if (getline(ws, idx))      ni = !idx.empty() ? stoi(idx) - 1 : 0;

                vBuffer.push_back(vertices[vi].x);
                vBuffer.push_back(vertices[vi].y);
                vBuffer.push_back(vertices[vi].z);
                vBuffer.push_back(color.r);
                vBuffer.push_back(color.g);
                vBuffer.push_back(color.b);
            }
        }
    }
    file.close();

    GLuint VBO, VAO;
    glGenBuffers(1, &VBO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, vBuffer.size() * sizeof(GLfloat), vBuffer.data(), GL_STATIC_DRAW);

    glGenVertexArrays(1, &VAO);
    glBindVertexArray(VAO);

    // position (location 0)
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(GLfloat), (GLvoid*)0);
    glEnableVertexAttribArray(0);
    // color (location 1)
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(GLfloat), (GLvoid*)(3 * sizeof(GLfloat)));
    glEnableVertexAttribArray(1);

    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    nVertices = (int)(vBuffer.size() / 6);
    return VAO;
}
