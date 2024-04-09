-- EXERCICIO 1: GERAR O SCRIPT DDL PARA CRIAÇÃO DO BANCO
--- 1. Criar tabela de Convenios
CREATE TABLE CONVENIO (
    idConvenio INTEGER PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
)

--- 2. Criar tabela de Pacientes
CREATE TABLE PACIENTE (
    idPaciente INTEGER PRIMARY KEY,
    dtNascimento DATE NOT NULL,
    nome VARCHAR(50) NOT NULL,
    diversos VARCHAR(50),
    idConvenio INTEGER REFERENCES CONVENIO(idConvenio)
)

--- 3. Criar tabela de Medicos
CREATE TABLE MEDICO (
    idMedico INTEGER PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
)

--- 4. Criar tabela de Consultas
CREATE TABLE CONSULTA (
    idConsulta INTEGER PRIMARY KEY,
    data DATE NOT NULL,
    diagnostico VARCHAR(100) NOT NULL,
    idPaciente INTEGER REFERENCES PACIENTE(idPaciente),
    idMedico INTEGER REFERENCES MEDICO(idMedico)
)

--- 5. Criar tabela de Exames
CREATE TABLE EXAME (
    idExame INTEGER PRIMARY KEY,
    idConsulta INTEGER REFERENCES CONSULTA(idConsulta),
    data DATE NOT NULL,
    resultados VARCHAR(100)
)

-- EXERCICIO 2: GERAR OS SCRIPTS DDL PARA ALTERAÇÃO DE TABELAS
--- a) alterar a tabela medico adicionando o campo endereco VARCHAR(100)
ALTER TABLE MEDICO ADD endereco VARCHAR(100)

--- b) alterar a tabela exame adicionando o campo nome VARCHAR(100)
ALTER TABLE EXAME ADD nome VARCHAR(100)

--- c) alterar a tabela medico alterando o tipo do campo nome para VARCHAR(100)
ALTER TABLE MEDICO ALTER COLUMN nome TYPE VARCHAR(100)

--- d) alterar a tabela paciente eliminando o campo diversos
ALTER TABLE PACIENTE DROP COLUMN diversos

--- e) eliminar a tabela convenio
---- 1. remover idConvenio de PACIENTE
ALTER TABLE PACIENTE DROP COLUMN idConvenio
---- 2. deletar a tabela convenio
DROP TABLE CONVENIO

-- ELIMINAR TODAS AS TABELAS PARA RECOMEÇAR
DROP TABLE PACIENTE, MEDICO, CONSULTA, EXAME
