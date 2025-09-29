import json
import os
import pandas as pd
import numpy as np

def parse_sar_cpu(filepath):
    """Extrai o uso médio de CPU '%user' de um arquivo sar."""
    try:
        with open(filepath, 'r') as f:
            lines = f.readlines()
        
        cpu_usages = []
        for line in lines:
            if 'Average:' in line and 'all' in line:
                # Exemplo de linha: Average: all    9.95    0.00    1.01    0.00    0.00   89.04
                parts = line.split()
                # O valor de %user é geralmente a 3ª coluna (índice 2)
                return float(parts[2])
        return None
    except (IOError, IndexError, ValueError):
        return None

def processar_diretorio(diretorio):
    """Processa todos os arquivos de log em um diretório e retorna um DataFrame."""
    dados = []
    for filename in os.listdir(diretorio):
        if filename.endswith("_iperf.json"):
            parts = filename.replace('_iperf.json', '').split('_')
            banda = parts[1]
            pacote = int(parts[3])
            rep = int(parts[5])
            
            # Caminho para o arquivo de CPU correspondente
            cpu_file_cliente = os.path.join(diretorio, f"banda_{banda}_pacote_{pacote}_rep_{rep}_cpu_cliente.txt")
            # Caminho para o arquivo de CPU do servidor
            cpu_file_servidor = os.path.join(diretorio, f"banda_{banda}_pacote_{pacote}_rep_{rep}_cpu_servidor.txt")

            cpu_cliente = parse_sar_cpu(cpu_file_cliente)
            cpu_servidor = parse_sar_cpu(cpu_file_servidor)

            try:
                with open(os.path.join(diretorio, filename), 'r') as f:
                    iperf_data = json.load(f)
                
                sum_data = iperf_data['end']['sum']
                vazao_bps = sum_data['bits_per_second']
                total_pacotes = sum_data.get('packets', 0)
                pacotes_ps = 0
                total_segundos = sum_data.get('seconds', 1) # Usa 1 para evitar divisão por zero
                if total_segundos > 0:
                    pacotes_ps = total_pacotes / total_segundos
                
                dados.append({
                    'banda_alvo': banda,
                    'tamanho_pacote': pacote,
                    'repeticao': rep,
                    'vazao_bps': vazao_bps,
                    'pacotes_ps': pacotes_ps,
                    'cpu_cliente_%': cpu_cliente,
                    'cpu_servidor_%': cpu_servidor
                })
            except (IOError, json.JSONDecodeError, KeyError):
                print(f"Erro ao processar o arquivo: {filename} {IOError, json.JSONDecodeError, KeyError}")
                continue
    
    return pd.DataFrame(dados)

# --- Script Principal ---
# Processe os dados para ambos os cenários
df_sem_roteador = processar_diretorio('resultados_sem_roteador')
df_com_roteador = processar_diretorio('resultados_com_roteador')

# Agrupar, calcular média e desvio padrão
agg_funcs = {
    'vazao_bps': ['mean', 'std'],
    'pacotes_ps': ['mean', 'std'],
    'cpu_cliente_%': ['mean', 'std'],
    'cpu_servidor_%': ['mean', 'std']
}

tabela_sem_roteador = df_sem_roteador.groupby(['banda_alvo', 'tamanho_pacote']).agg(agg_funcs).reset_index()
tabela_com_roteador = df_com_roteador.groupby(['banda_alvo', 'tamanho_pacote']).agg(agg_funcs).reset_index()

tabela_sem_roteador.columns = ['_'.join(col).strip('_') for col in tabela_sem_roteador.columns.values]
tabela_com_roteador.columns = ['_'.join(col).strip('_') for col in tabela_com_roteador.columns.values]

# Salvar tabelas em CSV para fácil visualização e para usar nos gráficos
tabela_sem_roteador.to_csv('tabela_final_sem_roteador.csv', index=False)
tabela_com_roteador.to_csv('tabela_final_com_roteador.csv', index=False)

print("Tabela Sem Roteador: Salva")
print("\nTabela Com Roteador: Salva")