import pandas as pd
import matplotlib.pyplot as plt
import os

# --- Configurações ---
ARQUIVO_SEM_ROTEADOR = 'tabela_final_sem_roteador.csv'
ARQUIVO_COM_ROTEADOR = 'tabela_final_com_roteador.csv'
PASTA_GRAFICOS = 'graficos'
PACOTES_TESTADOS = [128, 256, 512, 1024, 1280]

# Cria a pasta para salvar os gráficos, se ela não existir
if not os.path.exists(PASTA_GRAFICOS):
    os.makedirs(PASTA_GRAFICOS)

def carregar_dados():
    """Carrega os dados dos arquivos CSV para DataFrames do Pandas."""
    try:
        df_sem_roteador = pd.read_csv(ARQUIVO_SEM_ROTEADOR)
        df_com_roteador = pd.read_csv(ARQUIVO_COM_ROTEADOR)
        
        return df_sem_roteador, df_com_roteador
    except FileNotFoundError as e:
        print(f"Erro: Arquivo não encontrado - {e}. Certifique-se de que os arquivos CSV estão na mesma pasta que o script.")
        return None, None

def plotar_vazao_pacotes(df_sem, df_com, banda_str, banda_bps):
    """Gera e salva o gráfico de Vazão de Pacotes (pps)."""
    
    # Filtra os dados para a banda de largura atual
    dados_sem_r = df_sem[df_sem['banda_alvo'] == banda_str]
    dados_com_r = df_com[df_com['banda_alvo'] == banda_str]

    # 1. Cálculo da vazão teórica de pacotes
    vazao_teorica_pps = [banda_bps / (pacote * 8) for pacote in PACOTES_TESTADOS]

    plt.figure(figsize=(12, 7))
    
    # 2. Plotar as curvas
    plt.plot(PACOTES_TESTADOS, vazao_teorica_pps, label='Teórico', linestyle='--', marker='x', color='red')
    plt.plot(dados_sem_r['tamanho_pacote'], dados_sem_r['pacotes_ps_mean'], label='Sem Roteador', marker='o', color='blue')
    plt.plot(dados_com_r['tamanho_pacote'], dados_com_r['pacotes_ps_mean'], label='Com Roteador', marker='s', color='green')

    # 3. Configurações do gráfico
    plt.title(f'Vazão de Pacotes (pps) x Tamanho do Pacote\nBanda Alvo: {banda_str}')
    plt.xlabel('Tamanho do Pacote (Bytes)')
    plt.ylabel('Vazão (Pacotes por Segundo)')
    plt.xticks(PACOTES_TESTADOS)
    plt.grid(True, which='both', linestyle='--', linewidth=0.5)
    plt.legend()
    plt.tight_layout()

    # 4. Salvar o arquivo
    nome_arquivo = os.path.join(PASTA_GRAFICOS, f'vazao_pacotes_{banda_str}.png')
    plt.savefig(nome_arquivo)
    plt.close()
    print(f"Gráfico salvo: {nome_arquivo}")

def plotar_vazao_bytes(df_sem, df_com, banda_str, banda_bps):
    """Gera e salva o gráfico de Vazão de Bytes (bps)."""
    
    dados_sem_r = df_sem[df_sem['banda_alvo'] == banda_str]
    dados_com_r = df_com[df_com['banda_alvo'] == banda_str]

    plt.figure(figsize=(12, 7))
    
    # 1. Curva teórica (linha horizontal)
    plt.axhline(y=banda_bps, label=f'Teórico ({banda_str})', linestyle='--', color='red')
    
    # 2. Curvas experimentais
    plt.plot(dados_sem_r['tamanho_pacote'], dados_sem_r['vazao_bps_mean'], label='Sem Roteador', marker='o', color='blue')
    plt.plot(dados_com_r['tamanho_pacote'], dados_com_r['vazao_bps_mean'], label='Com Roteador', marker='s', color='green')

    # 3. Configurações do gráfico
    plt.title(f'Vazão de Bytes (bps) x Tamanho do Pacote\nBanda Alvo: {banda_str}')
    plt.xlabel('Tamanho do Pacote (Bytes)')
    plt.ylabel('Vazão (Bits por Segundo)')
    plt.xticks(PACOTES_TESTADOS)
    plt.ylim(bottom=0, top=banda_bps * 1.1) # Ajusta o limite do eixo Y
    plt.grid(True, which='both', linestyle='--', linewidth=0.5)
    plt.legend()
    plt.tight_layout()

    # 4. Salvar o arquivo
    nome_arquivo = os.path.join(PASTA_GRAFICOS, f'vazao_bytes_{banda_str}.png')
    plt.savefig(nome_arquivo)
    plt.close()
    print(f"Gráfico salvo: {nome_arquivo}")

def plotar_uso_cpu(df_sem, df_com, banda_str):
    """Gera e salva o gráfico de Uso de CPU (%)."""
    
    dados_sem_r = df_sem[df_sem['banda_alvo'] == banda_str]
    dados_com_r = df_com[df_com['banda_alvo'] == banda_str]

    plt.figure(figsize=(12, 7))
    
    # Plotar as 4 curvas de CPU
    plt.plot(dados_sem_r['tamanho_pacote'], dados_sem_r['cpu_cliente_%_mean'], label='CPU Cliente (Sem Roteador)', marker='o', linestyle=':', color='cyan')
    plt.plot(dados_sem_r['tamanho_pacote'], dados_sem_r['cpu_servidor_%_mean'], label='CPU Servidor (Sem Roteador)', marker='o', color='blue')
    plt.plot(dados_com_r['tamanho_pacote'], dados_com_r['cpu_cliente_%_mean'], label='CPU Cliente (Com Roteador)', marker='s', linestyle=':', color='lime')
    plt.plot(dados_com_r['tamanho_pacote'], dados_com_r['cpu_servidor_%_mean'], label='CPU Servidor (Com Roteador)', marker='s', color='green')

    # Configurações do gráfico
    plt.title(f'Uso de CPU (%) x Tamanho do Pacote\nBanda Alvo: {banda_str}')
    plt.xlabel('Tamanho do Pacote (Bytes)')
    plt.ylabel('Uso de CPU (%)')
    plt.xticks(PACOTES_TESTADOS)
    plt.ylim(bottom=0)
    plt.grid(True, which='both', linestyle='--', linewidth=0.5)
    plt.legend()
    plt.tight_layout()

    # Salvar o arquivo
    nome_arquivo = os.path.join(PASTA_GRAFICOS, f'uso_cpu_{banda_str}.png')
    plt.savefig(nome_arquivo)
    plt.close()
    print(f"Gráfico salvo: {nome_arquivo}")


def main():
    """Função principal para orquestrar a geração de gráficos."""
    df_sem_roteador, df_com_roteador = carregar_dados()
    
    if df_sem_roteador is None or df_com_roteador is None:
        return

    # Define as bandas de largura testadas para iterar
    bandas = {
        '1000M': 1_000_000_000, # 100% de 1 Gbit/s
        '800M': 800_000_000     # 80% de 1 Gbit/s
    }

    for banda_str, banda_bps in bandas.items():
        print(f"\n--- Gerando gráficos para a banda de {banda_str} ---")
        plotar_vazao_pacotes(df_sem_roteador, df_com_roteador, banda_str, banda_bps)
        plotar_vazao_bytes(df_sem_roteador, df_com_roteador, banda_str, banda_bps)
        plotar_uso_cpu(df_sem_roteador, df_com_roteador, banda_str)

if __name__ == '__main__':
    main()