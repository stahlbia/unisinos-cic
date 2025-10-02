import json
import pandas as pd
import re
import os
from pprint import pprint

def extrair_dados_iperf(json_data, size_bytes, bandwidth_arg, run):
    """
    Extrai dados de performance de um dicionário Python (carregado de um JSON do iperf3).

    Args:
        json_data (dict): Os dados carregados do arquivo JSON.
        size_bytes (int): O tamanho do pacote (metadado externo).
        bandwidth_arg (str): A banda alvo (metadado externo).
        run (int): O número da execução (metadado externo).

    Returns:
        dict: Um dicionário com os dados extraídos e calculados.
    """
    try:
        # Acessa os principais blocos de dados de forma segura
        sum_data = json_data.get('end', {}).get('sum', {})
        cpu_data = json_data.get('end', {}).get('cpu_utilization_percent', {})

        # Pega os valores de segundos e pacotes para cálculos
        seconds = sum_data.get('seconds', 0)
        packets = sum_data.get('packets', 0)
        bytes_val = sum_data.get('bytes', 0)

        # Calcula pacotes por segundo (pps) e bytes por segundo
        # Evita divisão por zero
        pps = (packets / seconds) if seconds > 0 else 0
        bytes_per_sec = (bytes_val / seconds) if seconds > 0 else 0

        # Monta o dicionário de resultados
        resultado = {
            "size_bytes": size_bytes,
            "bandwidth_arg": bandwidth_arg,
            "run": run,
            "seconds": seconds,
            "bits_per_sec": sum_data.get('bits_per_second'),
            "bytes": bytes_val,
            "packets": packets,
            "lost_packets": sum_data.get('lost_packets'),
            "lost_percent": sum_data.get('lost_percent'),
            "jitter_ms": sum_data.get('jitter_ms'),
            "pps": pps,
            "bytes_per_sec": bytes_per_sec,
            "cpu_percent_host_total": cpu_data.get('host_total'),
            "cpu_percent_remote_total": cpu_data.get('remote_total')
        }
        return resultado

    except (KeyError, TypeError) as e:
        print(f"Erro ao processar o JSON: {e}")
        return None

# --- Script principal para processar múltiplos arquivos ---

diretorio = "resultados_teste_udp"
todos_os_dados = []

# Expressão regular para o padrão: banda_{bandwidth_arg}_pacote_{size_bytes}_rep_{run}_iperf.json
padrao_nome_arquivo = re.compile(r"banda_([\w\d]+)_pacote_(\d+)_rep_(\d+)_iperf\.json")

print(f"Lendo arquivos do diretório: '{diretorio}'...")

if not os.path.isdir(diretorio):
    print(f"Erro: O diretório '{diretorio}' não foi encontrado.")
else:
    for nome_arquivo in os.listdir(diretorio):
        match = padrao_nome_arquivo.search(nome_arquivo)
        if match:
            bw = match.group(1)
            size = int(match.group(2))
            run = int(match.group(3))
            
            caminho_completo = os.path.join(diretorio, nome_arquivo)
            print(f"Processando arquivo: {nome_arquivo}")
            
            with open(caminho_completo, 'r') as f:
                try:
                    dados = json.load(f)
                    
                    # --- MODIFICAÇÃO ADICIONADA AQUI ---
                    # Verifica se a chave "error" existe no JSON
                    if "error" in dados:
                        print(f"--> Aviso: O arquivo '{nome_arquivo}' contém um erro e será IGNORADO.")
                        # O 'continue' interrompe esta iteração e pula para o próximo arquivo
                        continue
                    # --- FIM DA MODIFICAÇÃO ---
                    
                    dados_processados = extrair_dados_iperf(dados, size, bw, run)
                    if dados_processados:
                        todos_os_dados.append(dados_processados)
                        
                except json.JSONDecodeError:
                    print(f"--> Aviso: O arquivo '{nome_arquivo}' não é um JSON válido e será IGNORADO.")
        else:
             if nome_arquivo.endswith(".json"):
                print(f"Aviso: O arquivo JSON '{nome_arquivo}' não corresponde ao padrão de nome esperado e foi ignorado.")

    if todos_os_dados:
        df = pd.DataFrame(todos_os_dados)
        
        nome_csv_saida = "resultados_compilados.csv"
        df.to_csv(nome_csv_saida, index=False)
        
        print("\n--- Processamento concluído! ---")
        print(f"\nDataFrame criado e salvo em '{nome_csv_saida}' (apenas com dados válidos)")
    else:
        print("\nNenhum arquivo válido foi encontrado ou processado.")