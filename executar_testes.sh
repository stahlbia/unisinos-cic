#!/bin/bash

# --- Parâmetros ---
SERVER_IP="192.168.10.1"
USER_HOST="root"
IPERF_PORT="5201"
SSH_PORT="2222"
DURATION=30 # segundos
REPETITIONS=10
PACKETS=(128 256 512 1024 1280)
BANDWIDTH=("500M" "400M")

# --- Processa argumentos da linha de comando ---
# Este laço 'while' verifica e substitui os valores padrão pelos que forem passados
while [[ $# -gt 0 ]]; do
  key="$1"
  case $key in
    -server_ip)
      SERVER_IP="$2"
      shift 2
      ;;
    -user_host)
      USER_HOST="$2"
      shift 2
      ;;
    -iperf_port)
      IPERF_PORT="$2"
      shift 2
      ;;
    -ssh_port)
      SSH_PORT="$2"
      shift 2
      ;;
    -duration)
      DURATION="$2"
      shift 2
      ;;
    -repetitions)
      REPETITIONS="$2"
      shift 2
      ;;
    -packets)
      # Para arrays, passamos uma string com espaços e a convertemos
      PACKETS=($2)
      shift 2
      ;;
    -bandwidth)
      BANDWIDTH=($2)
      shift 2
      ;;
    *)
      # argumento desconhecido
      echo "Argumento desconhecido: $1"
      exit 1
      ;;
  esac
done

SERVER_USER_HOST="$USER_HOST@$SERVER_IP"

# Diretório para salvar os resultados locais e remotos
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
RESULT_DIR_LOCAL="resultados_${TIMESTAMP}"

mkdir -p "$RESULT_DIR_LOCAL"

echo "Resultados serão salvos em: $RESULT_DIR_LOCAL"

# --- Loop de Execução ---
for banda in "${BANDWIDTH[@]}"; do
  for pacote in "${PACKETS[@]}"; do
    echo "----------------------------------------------------------------------"
    echo "Iniciando teste: Largura de Banda=${banda}, Tamanho do Pacote=${pacote} bytes"
    echo "----------------------------------------------------------------------"
    for i in $(seq 1 $REPETITIONS); do
      echo "--> Executando repetição $i de $REPETITIONS..."

      # Nomes dos arquivos de log
      LOG_BASE="banda_${banda}_pacote_${pacote}_rep_${i}"
      LOG_IPERF="${RESULT_DIR_LOCAL}/${LOG_BASE}_iperf.json"
      LOG_CPU_CLIENTE="${RESULT_DIR_LOCAL}/${LOG_BASE}_cpu_cliente.txt"
      LOG_CPU_SERVIDOR="${LOG_BASE}_cpu_servidor.txt" # Nome do arquivo remoto

      # Comando iperf3
      iperf3 -c "$SERVER_IP" -p "$IPERF_PORT" -u -b "$banda" -l "$pacote" -t "$DURATION" -J --logfile "$LOG_IPERF"

      echo "Repetição $i finalizada. Logs locais salvos."
    done
  done
done

echo "Todos os testes foram concluídos!"
echo "Processo finalizado."