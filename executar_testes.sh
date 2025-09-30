#!/bin/bash

# # --- Parâmetros ---
# SERVER_IP="172.17.0.2"
# SERVER_USER_HOST="root@172.17.0.2" 
# DURACAO=30 # segundos
# REPETICOES=10
# PACOTES=(128 256 512 1024 1280)
# BANDAS=("1000M" "800M")

# --- Parâmetros ---
SERVER_IP="169.254.117.167"
SERVER_USER_HOST="root@169.254.117.167"
PORT="5201"
SSH_PORT="2222"
DURACAO=10 # segundos
REPETICOES=2
PACOTES=(128 256)
BANDAS=("1000M" "800M")

# Diretório para salvar os resultados locais e remotos
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
RESULT_DIR_LOCAL="resultados_${TIMESTAMP}"
RESULT_DIR_REMOTO="resultados_servidor_${TIMESTAMP}" # Diretório no servidor

mkdir -p "$RESULT_DIR_LOCAL"
ssh "-p $SSH_PORT" "$SERVER_USER_HOST" "mkdir -p $RESULT_DIR_REMOTO"

echo "Resultados locais serão salvos em: $RESULT_DIR_LOCAL"
echo "Resultados do servidor serão salvos em $SERVER_USER_HOST:$RESULT_DIR_REMOTO"

# --- Loop de Execução ---
for banda in "${BANDAS[@]}"; do
  for pacote in "${PACOTES[@]}"; do
    echo "------------------------------------------------------------"
    echo "Iniciando teste: Largura de Banda=${banda}, Tamanho do Pacote=${pacote} bytes"
    echo "------------------------------------------------------------"
    for i in $(seq 1 $REPETICOES); do
      echo "--> Executando repetição $i de $REPETICOES..."

      # Nomes dos arquivos de log
      LOG_BASE="banda_${banda}_pacote_${pacote}_rep_${i}"
      LOG_IPERF="${RESULT_DIR_LOCAL}/${LOG_BASE}_iperf.json"
      LOG_CPU_CLIENTE="${RESULT_DIR_LOCAL}/${LOG_BASE}_cpu_cliente.txt"
      LOG_CPU_SERVIDOR="${LOG_BASE}_cpu_servidor.txt" # Nome do arquivo remoto

      # Inicia a captura de CPU no CLIENTE (local) em background
      sar -u 1 35 > "$LOG_CPU_CLIENTE" &
      PID_SAR_CLIENTE=$!

      # Inicia a captura de CPU no SERVIDOR (remoto) em background !!
      # O comando é executado via SSH e o '&' no final o libera imediatamente
      ssh "-p $SSH_PORT" "$SERVER_USER_HOST" "sar -u 1 35 > ${RESULT_DIR_REMOTO}/${LOG_CPU_SERVIDOR} &"

      # Comando iperf3
      iperf3 -c "$SERVER_IP" -p "$PORT" -u -b "$banda" -l "$pacote" -t "$DURACAO" -J --logfile "$LOG_IPERF"

      # Espera a captura de CPU LOCAL terminar
      wait $PID_SAR_CLIENTE
      echo "Repetição $i finalizada. Logs locais salvos."
    done
  done
done

echo "Todos os testes foram concluídos!"
echo "Copiando arquivos de log da CPU do servidor..."

# Ao final de tudo, copia os arquivos do servidor para o diretório local !!
scp "${SERVER_USER_HOST}:${RESULT_DIR_REMOTO}/*.txt" "${RESULT_DIR_LOCAL}/"

echo "Arquivos do servidor copiados com sucesso para $RESULT_DIR_LOCAL."
echo "Processo finalizado."