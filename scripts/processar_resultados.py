import json
import pandas as pd
import re
import os

def extrair_dados_iperf(json_data, size_bytes, bandwidth_arg, run):
    """
    Extrai dados de performance de um dicionário Python (carregado de um JSON do iperf3).
    """
    try:
        sum_data = json_data.get('end', {}).get('sum', {})
        cpu_data = json_data.get('end', {}).get('cpu_utilization_percent', {})
        seconds = sum_data.get('seconds', 0)
        packets = sum_data.get('packets', 0)
        bytes_val = sum_data.get('bytes', 0)
        pps = (packets / seconds) if seconds > 0 else 0
        bytes_per_sec = (bytes_val / seconds) if seconds > 0 else 0
        resultado = {
            "size_bytes": size_bytes, "bandwidth_arg": bandwidth_arg, "run": run,
            "seconds": seconds, "bits_per_sec": sum_data.get('bits_per_second'),
            "bytes": bytes_val, "packets": packets, "lost_packets": sum_data.get('lost_packets'),
            "lost_percent": sum_data.get('lost_percent'), "jitter_ms": sum_data.get('jitter_ms'),
            "pps": pps, "bytes_per_sec": bytes_per_sec,
            "cpu_percent_host_total": cpu_data.get('host_total'),
            "cpu_percent_remote_total": cpu_data.get('remote_total')
        }
        return resultado
    except (KeyError, TypeError) as e:
        print(f"Erro ao processar o JSON: {e}")
        return None

# --- Script principal ---
diretorio = "resultados_com_roteador"
todos_os_dados = []
padrao_nome_arquivo = re.compile(r"result_(\d+)_([\w\d]+)_run(\d+)")

print(f"Lendo arquivos do diretório: '{diretorio}'...")
if not os.path.isdir(diretorio):
    print(f"Erro: O diretório '{diretorio}' não foi encontrado.")
else:
    for nome_arquivo in os.listdir(diretorio):
        match = padrao_nome_arquivo.search(nome_arquivo)
        if match:
            size = int(match.group(1))
            bw = match.group(2)
            run = int(match.group(3))
            
            caminho_completo = os.path.join(diretorio, nome_arquivo)
            
            # CORREÇÃO FINAL: Usar encoding='utf-16' para ler os arquivos
            with open(caminho_completo, 'r', encoding='utf-16') as f:
                try:
                    dados = json.load(f)
                    
                    if "error" in dados:
                        print(f"Aviso: Ignorando '{nome_arquivo}' pois contém um erro.")
                        continue
                    
                    dados_processados = extrair_dados_iperf(dados, size, bw, run)
                    if dados_processados:
                        print(f"--> Sucesso: Arquivo '{nome_arquivo}' processado.")
                        todos_os_dados.append(dados_processados)
                        
                except json.JSONDecodeError:
                    print(f"--> Erro: O arquivo '{nome_arquivo}' não pôde ser lido como um JSON válido.")
        else:
            if nome_arquivo.endswith(".json"):
                print(f"Aviso: O JSON '{nome_arquivo}' foi ignorado por não corresponder ao padrão de nome.")

    if todos_os_dados:
        df = pd.DataFrame(todos_os_dados)
        nome_csv_saida = "resultados_compilados.csv"
        df.to_csv(nome_csv_saida, index=False)
        print("\n--- Processamento concluído! ---")
        print(f"\nDataFrame criado e salvo em '{nome_csv_saida}'")
        print(df.head())
    else:
        print("\nNenhum arquivo válido foi encontrado ou processado.")