import time

def ler_arquivo(arquivo):
    with open(arquivo, 'r') as f:
        linhas = f.readlines()
        j = 0
        matrizes = []
        while j < len(linhas):
            tamanho = int(linhas[j].strip())
            if tamanho == 0:
                break
            matriz = []
            for i in range(j + 1, j + tamanho + 1):
                x, y = map(int, linhas[i].strip().split())
                matriz.append((x, y))
            matrizes.append(matriz)
            j = j+tamanho+1
    return matrizes

def comparar_resultados(resultados, caminho_arquivo):
    with open(caminho_arquivo, 'r', encoding='utf-8') as arquivo:
        conteudo_arquivo = [linha for linha in arquivo.read().splitlines() if linha.strip()]
    return resultados == conteudo_arquivo

def criar_matriz_dp(placas, max_soma):
    # Inicialização da matriz dp
    dp = [[False] * (max_soma + 1) for _ in range(max_soma + 1)]
    dp[0][0] = True  # Caso base: soma 0 é sempre possível
    
    # Atualização da matriz dp considerando ambas as orientações das placas
    for x, y in placas:
        for i in range(max_soma, -1, -1):
            for j in range(max_soma, -1, -1):
                if dp[i][j]:
                    # Considera as duas orientações da placa
                    if i + x <= max_soma and j + y <= max_soma:
                        dp[i + x][j + y] = True
                    if i + y <= max_soma and j + x <= max_soma:
                        dp[i + y][j + x] = True
    return dp

def buscar_melhor_soma(dp, max_soma):
    # Busca a maior soma balanceada (onde dp[s][s] é True)
    for soma in range(max_soma, 0, -1):  # Ignorar o caso trivial de soma = 0
        if dp[soma][soma]:
            return soma
    return -1  # Retorna -1 se nenhuma soma balanceada maior que 0 for encontrada

def verificar_descarte(placas, max_soma, melhor_soma):
    placa_descartada = None
    for idx, (x, y) in enumerate(placas):
        # Recalcula dp descartando a placa atual
        dp_descartando = criar_matriz_dp(placas[:idx] + placas[idx + 1:], max_soma)
        
        # Verifica se a soma balanceada ainda é possível
        if dp_descartando[melhor_soma][melhor_soma]:
            if not placa_descartada or (min(x, y), max(x, y)) < placa_descartada:
                placa_descartada = (min(x, y), max(x, y))
    
    if placa_descartada:
        return f"{melhor_soma} descartada a placa {placa_descartada[0]} {placa_descartada[1]}"
    else:
        return f"{melhor_soma} nenhuma placa descartada"

def processar(arquivo_in, arquivo_out):
    inicio_tempo = time.time()
    
    dados = ler_arquivo(arquivo_in)
    resultados = []

    for placas in dados:
        # Soma máxima possível
        max_soma = sum(max(x, y) for x, y in placas)
        dp = criar_matriz_dp(placas, max_soma)
        
        # Verificar maior soma balanceada
        melhor_soma = buscar_melhor_soma(dp, max_soma)
        if melhor_soma == -1:
            resultados.append("impossível")
            continue
        
        # Verificar se é necessário descartar
        resultados.append(verificar_descarte(placas, max_soma, melhor_soma))
    
    fim_tempo = time.time()
    tempo_execucao = (fim_tempo - inicio_tempo) * 1000
    resultado = comparar_resultados(resultados, arquivo_out)
    if resultado:
        print(f"CERTO: resultado de ${arquivo_in} igual ao conteúdo de ${arquivo_out} | tempo: {tempo_execucao:.3f} ms | tempo inicio: {inicio_tempo} e tempo fim: {fim_tempo}")
    else:
        print(f"ERRADO: resultado de ${arquivo_in} diferente ao conteúdo de ${arquivo_out} | tempo: {tempo_execucao:.3f} ms | tempo inicio: {inicio_tempo} e tempo fim: {fim_tempo}")

processar("./in/in.txt", "./out/out.txt")
processar("./in/in1.txt", "./out/out1.txt")
processar("./in/in2.txt", "./out/out2.txt")
processar("./in/in6.txt", "./out/out6.txt")
processar("./in/in7.txt", "./out/out7.txt")
processar("./in/in5.txt", "./out/out5.txt")
processar("./in/in3.txt", "./out/out3.txt")
processar("./in/in4.txt", "./out/out4.txt")
