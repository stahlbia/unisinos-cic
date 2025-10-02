#!/bin/bash

# --- Parâmetros ---
SERVER_IP="192.168.10.1"
DURATION=30
REPETITIONS=10
PACKETS=(128 256 512 1024 1280)
BANDWIDTH=("500M" "400M") # BANDWIDTH de 100% e 80%
RESULT_DIR="resultados"
OUTPUT_SCRIPT="comandos_iperf.sh"

# --- Processa argumentos da linha de comando ---
# Este laço 'while' verifica e substitui os valores padrão pelos que forem passados
while [[ $# -gt 0 ]]; do
  key="$1"
  case $key in
    -server_ip)
      SERVER_IP="$2"
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
    -path_dir)
      RESULT_DIR=($2)
      shift 2
      ;;
    *)
      # argumento desconhecido
      echo "Argumento desconhecido: $1"
      exit 1
      ;;
  esac
done

mkdir -p "$RESULT_DIR"

# Loop principal para gerar cada linha de comando
for banda in "${BANDWIDTH[@]}"; do
  for pacote in "${PACKETS[@]}"; do
    for i in $(seq 1 $REPETITIONS); do
      # Constrói o nome do arquivo de log, incluindo o nome do diretório
      LOG_FILENAME="${RESULT_DIR}/banda_${banda}_pacote_${pacote}_rep_${i}_iperf.json"
      
      # Constrói o comando iperf3 completo
      # As aspas duplas em volta de \"${LOG_FILENAME}\" garantem que o nome do arquivo seja tratado corretamente
      COMANDO="iperf3 -c ${SERVER_IP} -u -b ${banda} -l ${pacote} -t ${DURATION} -J --logfile \"${LOG_FILENAME}\""
      
      # Escreve (anexa) o comando gerado no arquivo de saída
      echo "${COMANDO}" >> "${OUTPUT_SCRIPT}"
    done
  done
done

# Torna o script gerado executável
chmod +x "${OUTPUT_SCRIPT}"