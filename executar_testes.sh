#!/bin/bash

# --- Parâmetros ---
SERVER_IP="172.17.0.2"
USER_HOST="root"
IPERF_PORT="5201"
SSH_PORT="2222"
DURATION=30 # segundos
REPETITIONS=10
PACKETS=(128 256 512 1024 1280)
BANDWIDTH=("1000M" "800M")

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
      shift # remove a chave
      shift # remove o valor
      ;;
    -repetitions)
      REPETITIONS="$2"
      shift 2 # outra forma de remover a chave e o valor
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
RESULT_DIR_REMOTO="resultados_servidor_${TIMESTAMP}" # Diretório no servidor

mkdir -p "$RESULT_DIR_LOCAL"
ssh "-p $SSH_PORT" "$SERVER_USER_HOST" "mkdir -p $RESULT_DIR_REMOTO"

echo "Resultados locais serão salvos em: $RESULT_DIR_LOCAL"
echo "Resultados do servidor serão salvos em $SERVER_USER_HOST:$RESULT_DIR_REMOTO"

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

      # Inicia a captura de CPU no CLIENTE (local) em background
      sar -u 1 35 > "$LOG_CPU_CLIENTE" &
      PID_SAR_CLIENTE=$!

      # Inicia a captura de CPU no SERVIDOR (remoto) em background !!
      # O comando é executado via SSH e o '&' no final o libera imediatamente
      ssh "-p $SSH_PORT" "$SERVER_USER_HOST" "sar -u 1 35 > ${RESULT_DIR_REMOTO}/${LOG_CPU_SERVIDOR} &"

      # Comando iperf3
      iperf3 -c "$SERVER_IP" -p "$IPERF_PORT" -u -b "$banda" -l "$pacote" -t "$DURATION" -J --logfile "$LOG_IPERF"

      # Espera a captura de CPU LOCAL terminar
      wait $PID_SAR_CLIENTE
      echo "Repetição $i finalizada. Logs locais salvos."
    done
  done
done

echo "Todos os testes foram concluídos!"
echo "Copiando arquivos de log da CPU do servidor..."

# Ao final de tudo, copia os arquivos do servidor para o diretório local !!
scp -P ${SSH_PORT} "${SERVER_USER_HOST}:${RESULT_DIR_REMOTO}/*.txt" "${RESULT_DIR_LOCAL}/"

echo "Arquivos do servidor copiados com sucesso para $RESULT_DIR_LOCAL."
echo "Processo finalizado."