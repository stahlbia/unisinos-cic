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
    python3 \
    python3-pip \
    python3-venv

# Expõe a porta 5201, padrão do iperf3, para testes de rede
EXPOSE 5201

# Define o comando padrão para iniciar um terminal bash interativo quando o contêiner for executado
CMD ["/bin/bash"]