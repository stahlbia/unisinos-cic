# Guia Completo para Avaliação de Roteador com Iperf3

Este guia detalha o processo de avaliação de desempenho de um roteador utilizando a ferramenta Iperf3, seguindo os requisitos e cenários especificados pelo professor.

## 1 Pré-requisitos e Preparação do Ambiente

Antes de iniciar os testes, garanta que o ambiente esteja configurado corretamente. Para a execução desse teste, será utilizado containers do docker, por isso o setup começará desde a criação do container.

### 1.1 Criando os containers com o Dockerfile

Para criar os containers com o Dockerfile é preciso primeiro criar a imagem de cada container em sua respectiva máquina. Para isso, dê um comando `cd micro-x` para entrar na pasta com o Dockerfile, e execute `docker build -t nome-ambiente .`. Para iniciar o container rode o comando `docker run -itd --name micro-x -p 5201:5201 -p 2222:22 --privileged nome-ambiente`, e para executar o container rode o comando `docker exec -it micro-x /bin/bash`.

#### 1.1.1 Dockerfile

O Dockerfile instala a versão mais recente do ubuntu e instala os pacotes e ferramentas que serão necessários para executar os testes. Perceba que cada micro possui um Dockerfile diferente.

#### 1.1.2 Comando docker run

O comando `docker run` inicia o container, e para isso é necessário passar as seguintes tags:

- `-itd`: executa o container de forma interativa `-i`, aloca um pseudo-TTY `-t` e roda em modo detached/background `-d`
- `--name VALOR`: define qual vai ser o nome do container
- `-p PORTA:PORTA`: mapeia a porta do container para a porta do computador. precisa pro ssh e pra usar o iperf3
- `--privileged`: concede privilégios extendidos ao container
- `nome-ambiente`: indica a partir de qual imagem o container será criado. nesse caso é a do Dockerfile

#### 1.1.3 Comando úteis do docker

- Entrar no container: `docker exec -it micro-x /bin/bash`
- Sair do container: `exit`
- Iniciar o container: `docker start micro-x`
- Parar o container: `docker stop micro-x`

### 1.2 Configurando a rede

#### OBS: É necessário repetir a configuração da rede para cada cenário de teste

O IP que será utilizado nos testes é o IP da rede da máquina física, ou seja, os próximos comandos não serão executados dentro do container, e sim no terminal da própria máquina. Esses valores de IP (principalmente o da máquina do Micro A), serão utilizados em comandos seguintes, então é bom anotar em algum lugar. Se a conexão for desfeita e refeita novamente, é preciso pegar os IPs de novo, pois se o DNS estiver ativado para pegar IP automático, eles irão mudar.

#### MacOS

`ifconfig | grep "inet "` -> para pegar o valor do ip (169.254.225.132)

#### Windows 11

`ipconfig` -> pegar o Endereço IPv4 do Adaptador Ethernet Ethernet (169.254.7.56)

#### 1.2.1 Configurando o ssh: Micro A

O micro A irá trabalhar como o servidor, para isso será necessário usar ssh para o outro container conseguir buscar algumas informações, para isso, dentro do container micro A, execute os seguintes comandos:

- `mkdir /run/sshd`
- `/usr/sbin/sshd`
- `passwd` -> crie uma senha simples como "root"
- `nano /etc/ssh/sshd_config` -> encontre a linha que diz "#PermitRootLogin prohibit-password" e troque por "PermitRootLogin yes".
- `kill $(pgrep sshd)`
- `/usr/sbin/sshd`
- `ps aux | grep sshd` -> verificar se o serviço tá on

#### 1.2.2 Configurando o ssh: Micro B

O micro B irá trabalhar como o cliente, para isso será necessário instalar e configurar uma chave ssh (faça os passos do micro A antes disso):

- `ssh-keygen -t rsa`
- `ssh-copy-id -p 2222 root@{IP_MICRO_A}` -> a senha é a mesma configurada no micro A

### 1.3 Cenários de testes

#### 1.3.1 Cenário 1: Sem Roteador (Conexão Direta)

1. Conecte o Micro A diretamente ao Micro B com um cabo Ethernet.
2. Descubra o IP de cada dispositivo (tópico 1.2)

#### 1.3.2 Cenário 2: Com Roteador

1. Conecte o Micro A a uma porta LAN do roteador.
2. Conecte o Micro B a outra porta LAN do roteador.
3. Descubra o IP de cada dispositivo (tópico 1.2)

## 2 Execução dos Testes

Os testes serão automatizados com um simples script shell para garantir a repetição e a consistência.

### 2.1 Iniciar o servidor Iperf3

No **Micro A (Servidor)**, execute o seguinte comando. Ele ficará aguardando as conexões do cliente.

`iperf3 -s`

### 2.2 Executar o script de teste no cliente

No **Micro B (Cliente)**, crie um arquivo através do comando `nano executar_testes.sh`, cole o script lá, após isso repita a sequência `ctrl + o; ENTER; ctrl + x`. Dê permissão de execução com o comando `chmod +x executar_testes.sh`, e execute com o comando `./executar_testes.sh`.

No final, é necessário renomear as pastas que serão geradas pelo script de acordo com o cenário: `mv resultados_* resultados_sem_roteador` e `mv resultados_* resultados_com_roteador`

OBS1: O script pode ser executado com parâmetros para mudar algumas informações como as seguintes:

- `-server_ip`: passa o IP do Micro A
- `-user_host`: passo o nome de usuário do Micro A (normalmente é "root")
- `-iperf_port`: porta na qual o iperf3 foi programado para rodar (default=5201)
- `-ssh_port`: porta na qual o ssh for programado para conectar (default=2222)
- `-duration`: tempo que cada execução levará em segundos
- `-repetitions`: quantidade de vezes que cada test-case será repetido
- `-packets`: tamanhos dos pacotes que serão testados
- `-bandwidth`: tamanhos de banda que serão testados
- exemplo: `./executar_testes.sh -server_ip 169.254.225.132 -user_host root -iperf_port 5210 -ssh_port 2222 -duration 30 -repetitions 10 -packets "128 256 512 1024 1280" -bandwidth "800M 1000M"`

OBS2: Como são vários test cases, o script irá demorar cerca de 45 min para rodar completamente.

OBS3: Rode o script uma vez para cada cenário de teste.

## 3 Processamento dos Dados

Agora com todas as informações dos testes, a melhor maneira de processar todos os dados é através de um script, nesse caso será em Python, utilizando a biblioteca `pandas`. Para isso, crie um ambiente python através dos comandos:

- `python3 -m venv redes-env`
- `source ~/redes-env/bin/activate`
- `pip3 install pandas matplotlib`
- `deactivate` -> para desativar o env

Após isso, crie um arquivo através do comando `nano processar_resultados.py`, cole o script lá, após isso repita a sequência `ctrl + o; ENTER; ctrl + x`. Execute o arquivo com o comando `python3 processar_resultados.py`, isso irá gerar dois arquivos `.csv` com os dados.

## 4 Apresentação dos resultados

Com as tabelas prontas, utilizaremos o script `gerar_graficos.py` para comparar os dados e gerar os gráficos.

### 4.1 Setup para o script

Para rodar o script é necessário ter as bibliotecas `pandas` e `matplotlib` (que já foram instalados no tópico 3). Crie um arquivo através do comando `nano gerar_graficos.py`, cole o script lá, após isso repita a sequência `ctrl + o; ENTER; ctrl + x`. Execute o arquivo com o comando `python3 gerar_graficos.py`.

Para rodar o script é importante que ele esteja no mesmo diretório dos arquivos `.csv`.
