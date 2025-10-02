import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def limpar_e_gerar_graficos():
    """
    Função final para carregar, limpar, analisar e gerar os gráficos.
    """
    # Nomes dos arquivos CSV de entrada
    file_com_roteador = 'resultados_com_roteador.csv'
    file_sem_roteador = 'resultados_sem_roteador.csv'

    try:
        # Carrega os arquivos CSV
        print("Carregando arquivos de dados...")
        df_com_roteador = pd.read_csv(file_com_roteador)
        df_sem_roteador = pd.read_csv(file_sem_roteador)

        # --- LIMPEZA E CONVERSÃO FORÇADA DE DADOS ---
        cols_para_converter = [
            'seconds', 'lost_percent', 'jitter_ms', 'pps', 'bytes_per_sec', 
            'cpu_percent_host_total', 'cpu_percent_remote_total'
        ]

        print("\nIniciando a limpeza e conversão de dados...")
        for df_name, df in [('Com Roteador', df_com_roteador), ('Sem Roteador', df_sem_roteador)]:
            for col in cols_para_converter:
                if col in df.columns:
                    df[col] = df[col].astype(str)
                    df[col] = df[col].str.replace('.', '', regex=False)
                    df[col] = df[col].str.replace(',', '.', regex=False)
                    df[col] = pd.to_numeric(df[col], errors='coerce')
        
        df_com_roteador.fillna(0, inplace=True)
        df_sem_roteador.fillna(0, inplace=True)
        print("Limpeza de dados concluída.")
        
        # --- ANÁLISE DOS DADOS ---
        print("\nCalculando médias para cada tamanho de pacote...")

        # Adicionado 'numeric_only=True' para que o pandas ignore colunas de texto (como 'bandwidth_arg')
        # ao calcular a média.
        avg_com_roteador = df_com_roteador.groupby('size_bytes').mean(numeric_only=True).reset_index()
        avg_sem_roteador = df_sem_roteador.groupby('size_bytes').mean(numeric_only=True).reset_index()

        # --- CÁLCULO DA VAZÃO TEÓRICA ---
        link_speed_bps = 1e9
        ethernet_overhead = 39
        packet_sizes_bytes = avg_sem_roteador['size_bytes']
        frame_sizes_bytes = packet_sizes_bytes + ethernet_overhead
        theoretical_pps = link_speed_bps / (frame_sizes_bytes * 8)
        theoretical_bps = (link_speed_bps * (packet_sizes_bytes / frame_sizes_bytes)) / 8

        # --- GERAÇÃO DOS GRÁFICOS ---
        print("\nGerando gráficos...")
        
        # Gráfico 1: Vazão de Pacotes
        plt.figure(figsize=(12, 7))
        plt.plot(avg_com_roteador['size_bytes'], avg_com_roteador['pps'], marker='o', linestyle='-', label='Com Roteador (Experimental)')
        plt.plot(avg_sem_roteador['size_bytes'], avg_sem_roteador['pps'], marker='s', linestyle='-', label='Sem Roteador (Experimental)')
        plt.plot(packet_sizes_bytes, theoretical_pps, marker='x', linestyle='--', color='k', label='Teórico (Rede 1 Gbit/s)')
        plt.title('Gráfico 1: Vazão de Pacotes na Rede', fontsize=16)
        plt.xlabel('Tamanho do Pacote (bytes)', fontsize=12)
        plt.ylabel('Pacotes por Segundo (pps)', fontsize=12)
        plt.grid(True, which="both", ls="--")
        plt.legend()
        plt.xscale('log')
        plt.yscale('log')
        plt.savefig('vazao_pacotes.png')
        plt.close()
        print("- 'vazao_pacotes.png' salvo.")

        # Gráfico 2: Vazão de Bytes
        plt.figure(figsize=(12, 7))
        plt.plot(avg_com_roteador['size_bytes'], avg_com_roteador['bytes_per_sec'], marker='o', linestyle='-', label='Com Roteador (Experimental)')
        plt.plot(avg_sem_roteador['size_bytes'], avg_sem_roteador['bytes_per_sec'], marker='s', linestyle='-', label='Sem Roteador (Experimental)')
        plt.plot(packet_sizes_bytes, theoretical_bps, marker='x', linestyle='--', color='k', label='Teórico (Rede 1 Gbit/s)')
        plt.title('Gráfico 2: Vazão de Bytes na Rede', fontsize=16)
        plt.xlabel('Tamanho do Pacote (bytes)', fontsize=12)
        plt.ylabel('Bytes por Segundo (Bps)', fontsize=12)
        plt.grid(True, which="both", ls="--")
        plt.legend()
        plt.xscale('log')
        plt.savefig('vazao_bytes.png')
        plt.close()
        print("- 'vazao_bytes.png' salvo.")

        # Gráfico 3: Utilização da CPU
        avg_com_roteador['cpu_total'] = avg_com_roteador['cpu_percent_host_total'] + avg_com_roteador['cpu_percent_remote_total']
        avg_sem_roteador['cpu_total'] = avg_sem_roteador['cpu_percent_host_total'] + avg_sem_roteador['cpu_percent_remote_total']
        plt.figure(figsize=(12, 7))
        plt.plot(avg_com_roteador['size_bytes'], avg_com_roteador['cpu_total'], marker='o', linestyle='-', label='Com Roteador')
        plt.plot(avg_sem_roteador['size_bytes'], avg_sem_roteador['cpu_total'], marker='s', linestyle='-', label='Sem Roteador')
        plt.title('Gráfico 3: Utilização da CPU vs. Tamanho do Pacote', fontsize=16)
        plt.xlabel('Tamanho do Pacote (bytes)', fontsize=12)
        plt.ylabel('Utilização Total da CPU (%)', fontsize=12)
        plt.grid(True, which="both", ls="--")
        plt.legend()
        plt.xscale('log')
        plt.savefig('utilizacao_cpu.png')
        plt.close()
        print("- 'utilizacao_cpu.png' salvo.")
        
        print("\nAnálise concluída com sucesso!")

    except FileNotFoundError:
        print(f"\nERRO: Arquivos não encontrados!")
    except Exception as e:
        print(f"\nOcorreu um erro inesperado: {e}")

if __name__ == '__main__':
    limpar_e_gerar_graficos()