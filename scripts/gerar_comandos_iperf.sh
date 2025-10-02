#!/bin/bash

# --- Parâmetros ---
SERVER_IP="192.168.10.2"
DURATION=30
REPETITIONS=10
PACKETS=(128 256 512 1024 1280)
BANDWIDTH=("100M" "80M") # BANDWIDTH de 100% e 80%
RESULT_DIR="resultados_teste_udp"
OUTPUT_SCRIPT="comandos/comandos_iperf_udp.sh"

# mkdir -p "$RESULT_DIR"

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