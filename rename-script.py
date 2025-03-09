import os

# Função para renomear os arquivos
def renomear_arquivos(nome_pasta):
    # Obtém o caminho absoluto da pasta atual
    caminho_pasta = os.path.join(os.getcwd(), nome_pasta)
    
    # Verifica se a pasta existe
    if not os.path.exists(caminho_pasta):
        print(f"A pasta '{nome_pasta}' não existe.")
        return

    # Itera sobre todos os arquivos no diretório
    for nome_item in os.listdir(caminho_pasta):
        caminho_antigo = os.path.join(caminho_pasta, nome_item)
        
        # Verifica se é um arquivo ou uma pasta (não ignorando pastas)
        if os.path.isdir(caminho_antigo) or os.path.isfile(caminho_antigo):
            # Remove underlines e outros caracteres especiais (exceto espaços)
            nome_item = nome_item.replace('_', '').replace('-', '').replace('  ', ' ')
            
            # Converte o nome para minúsculas e substitui os espaços por hífens
            novo_nome = nome_item.lower().replace(' ', '-')
            
            # Cria o caminho completo para o novo nome
            caminho_novo = os.path.join(caminho_pasta, novo_nome)
            
            # Renomeia o arquivo
            os.rename(caminho_antigo, caminho_novo)
            print(f"Item renomeado: {nome_item} -> {novo_nome}")

# Nome da pasta que contém os arquivos a serem renomeados
nome_pasta = 'quimica/aula-4-termos-e-conceitos-do-átomo'  # Substitua pelo nome da sua pasta

# Chama a função para renomear os arquivos
renomear_arquivos(nome_pasta)
