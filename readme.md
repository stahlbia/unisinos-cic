# Guia Completo para Avaliação de Roteador com Iperf3

Este guia detalha o processo de avaliação de desempenho de um roteador utilizando a ferramenta Iperf3, seguindo os requisitos e cenários especificados.

## 1. Pré-requisitos e Preparação do Ambiente

Antes de iniciar os testes, garanta que o ambiente esteja configurado corretamente. Para a execução desse teste, será utilizado containers do docker, por isso o setup começará desde a criação do container.

### 1.1. Instalação do Iperf3 e Ferramentas Adicionais

#### 1.1.1 Em ambos os computadores (Setup dos containers)

Faça o setup dos containers da seguinte forma

1. Baixar imagem oficial do Ubuntu

`sudo docker pull ubuntu`

2. Iniciar um novo container em segundo plano com um terminal interativo:

`docker run -itd --name micro-X --privileged ubuntu`

3. Entrar no container:

`docker exec -it micro-X /bin/bash`

4. Instalar apps:

`su -`
`apt-get update`
`apt-get install sudo`
`sudo apt install nano`
`sudo apt-get install iperf3 sysstat -y`

5. Para sair do container:

`exit`
`docker stop micro-X`
`docker start micro-X`

#### 1.1.2 No computador Micro A

Esse computador vai trabalhar como o servidor, para fazer o setup completo será necessário baixar as seguintes ferramentas:

`sudo apt-get install openssh-server -y` -> será utilizada uma conexão ssh para pegar os dados do servidor e trazer para o lado do cliente para a anlize

##### Troubleshoot

Se a conexão ssh não funcionar, rode os seguintes comandos:

`mkdir /run/sshd`
`/usr/sbin/sshd`
`passwd` -> crie uma senha simples como "root"
`nano /etc/ssh/sshd_config` -> encontre a linha que diz "#PermitRootLogin prohibit-password" e troque por "PermitRootLogin yes".
`kill $(pgrep sshd)`
`/usr/sbin/sshd`

#### 1.1.3 No computador Micro B

Esse computador vai trabalhar com o cliente, para fazer o setup completo será necessário baixar as seguintes ferramentas:

`sudo apt-get install openssh-client -y`
`ssh-keygen -t rsa`
`ssh-copy-id root@172.17.0.2` -> a senha é a mesma configurada no micro A

É importante fazer o setup do python3 para poder rodar os scripts

`sudo apt install python3`
`sudo apt install python3-pip`
`python3 -m venv redes-env`
`source ~/redes-env/bin/activate`
`pip3 install pandas matplotlib`
`deactivate` -> para desativar o env

### 1.2. Configuração de Rede

#### **Cenário 1: Sem Roteador (Conexão Direta)**

1. Conecte o Micro A diretamente ao Micro B com um cabo Ethernet.
2. Descubra o IP de cada dispositivo através do comando `ipconfig` 
3. Verifique a conexão:
  * No Micro B, execute: `ping {{IP do Micro A}}`

#### **Cenário 2: Com Roteador**

1. Conecte o Micro A a uma porta LAN do roteador.
2. Conecte o Micro B a outra porta LAN do roteador.
3. Descubra o IP de cada dispositivo através do comando `ipconfig` 
4. Verifique a conexão:
  * No Micro B, execute: `ping {{IP do Micro A}}`

## 2. Execução dos Testes

Os testes serão automatizados com um simples script shell para garantir a repetição e a consistência.

### 2.1. Iniciar o Servidor Iperf3

No **Micro A (Servidor)**, execute o seguinte comando. Ele ficará aguardando as conexões do cliente.

`iperf3 -s`

### 2.2. Executar o Script de Teste no Cliente

No **Micro B (Cliente)**, crie um arquivo através do comando `nano executar_testes.sh`, cole o script lá, após isso repita a sequência `ctrl + o; ENTER; ctrl + x`. Dê permissão de execução com o comando `chmod +x executar_testes.sh`, e execute com o comando `./executar_testes.sh`.

OBS1: Como são vários test cases, o script irá demorar cerca de 45 min para rodar completamente.

OBS2: Rode o script uma vez para cada cenário de teste.

## 3. Processamento dos Dados 

Agora com todas as informações dos testes, a melhor maneira de processar todos os dados é através de um script, nesse caso será em Python, utilizando a biblioteca `pandas`. Para isso, crie um arquivo através do comando `nano processar_resultados.py`, cole o script lá, após isso repita a sequência `ctrl + o; ENTER; ctrl + x`. Execute o arquivo com o comando `python3 processar_resultados.py`, isso irá gerar dois arquivos `.csv` com os dados.

## 4. Apresentação dos resultados

Com as tabelas prontas, utilizaremos o script `gerar_graficos.py` para comparar os dados e gerar os gráficos.

### 4.1 Setup para o script

Para rodar o script é necessário ter as bibliotecas `pandas` e `matplotlib` instaladas. Se não tiver, rode os seguintes comandos:

`pip install pandas matplotlib`

### 4.1 Script

Crie um arquivo através do comando `nano gerar_graficos.py`, cole o script lá, após isso repita a sequência `ctrl + o; ENTER; ctrl + x`. Execute o arquivo com o comando `python3 gerar_graficos.py`.

Para rodar o script é importante que ele esteja no mesmo diretório dos arquivos `.csv`.
