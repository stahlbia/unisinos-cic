# Use a imagem base do Ubuntu mais recente
FROM ubuntu:latest

# Evita prompts interativos durante a instalação de pacotes
ENV DEBIAN_FRONTEND=noninteractive

# Atualiza a lista de pacotes e instala todas as ferramentas necessárias
RUN apt-get update && apt-get install -y \
    sudo \
    nano \
    net-tools \
    iproute2 \
    iperf3 \
    sysstat \
    openssh-server

# Cria o diretório necessário para o serviço SSH
RUN mkdir /var/run/sshd

# Expõe a porta 22 para permitir conexões SSH externas
EXPOSE 22
# Expõe a porta 5201, padrão do iperf3, para testes de rede
EXPOSE 5201

# Define o comando padrão para iniciar o servidor SSH quando o container for executado
# A opção '-D' executa o servidor em primeiro plano, mantendo o container ativo
CMD ["/bin/bash", "/usr/sbin/sshd", "-D"]