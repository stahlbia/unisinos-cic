# Main.py
import time
import numpy as np
from SortMethods import SortMethods
from util import generate_sorted_array, generate_reversed_array, generate_random_array
import os
from datetime import datetime

def measure_time(sort_func, arr):
    start_time = time.time_ns()
    sort_func(arr.copy())
    end_time = time.time_ns()
    return end_time - start_time

def save_results_to_md(results, scenario_name, array_sizes, sort_methods):
    filename = f'results_{scenario_name}.md'
    with open(filename, 'w') as f:
        f.write(f"## Scenario: {scenario_name}\n")
        f.write("| Array Size | " + " | ".join(sort_methods.keys()) + " |\n")
        f.write("| --- " * (len(sort_methods) + 1) + "|\n")
        for i, size in enumerate(array_sizes):
            row = [f"{size}"]
            for method_name in sort_methods.keys():
                row.append(f"{results[method_name][i]:.2f}")
            f.write("| " + " | ".join(row) + " |\n")
        f.write("\n")
    print(f"Resultados salvos em {filename}")

def main():
    array_sizes = [2**i for i in range(7, 15)]  # Sizes from 128 to 65536
    scenarios = {
        "sorted": generate_sorted_array,
        "reversed": generate_reversed_array,
        "random_unique": lambda n: generate_random_array(n, unique=True),
        "random_repeated": lambda n: generate_random_array(n, unique=False),
    }
    
    sort_methods = {
        "Bubble Sort": SortMethods.bubble_sort,
        "Insertion Sort": SortMethods.insertion_sort,
        "Selection Sort": SortMethods.selection_sort,
        "Heap Sort": SortMethods.heap_sort,
        "Shell Sort": SortMethods.shell_sort,
        "Merge Sort": SortMethods.merge_sort,
        "Quick Sort": SortMethods.quick_sort,
    }

    # Register the start time
    start_datetime = datetime.now()
    start_time = time.time()
    total_steps = len(scenarios) * len(array_sizes) * len(sort_methods) * 10
    current_step = 0
    
    for scenario_name, scenario_func in scenarios.items():
        results = {method: [] for method in sort_methods.keys()}
        
        for size in array_sizes:
            times = {method: [] for method in sort_methods.keys()}
            arr = scenario_func(size)  # Gera um novo array para o cenário e tamanho atuais
            
            for _ in range(10):
                for method_name, sort_func in sort_methods.items():
                    array_copy = arr.copy()  # Certifique-se de copiar o array para cada execução
                    time_taken = measure_time(sort_func, array_copy)
                    times[method_name].append(time_taken)
                    current_step += 1
                    progress = (current_step / total_steps) * 100
                    os.system('cls' if os.name == 'nt' else 'clear')
                    print(f"Start Time: {start_datetime.strftime('%Y-%m-%d %H:%M:%S')}")
                    print(f"Scenario: {scenario_name} | Size: {size} | Sort Method: {method_name}")
                    print(f"Progress: {progress:.2f}%")
            
            # Calculate mean and standard deviation
            for method_name in sort_methods.keys():
                mean_time = np.mean(times[method_name])
                std_dev = np.std(times[method_name])
                filtered_times = [t for t in times[method_name] if (mean_time - std_dev) <= t <= (mean_time + std_dev)]
                final_mean_time = np.mean(filtered_times)
                results[method_name].append(final_mean_time)
        
        save_results_to_md(results, scenario_name, array_sizes, sort_methods)
    
    end_time = time.time()
    total_time = (end_time - start_time) / 60  # Total time in minutes
    print(f"Total time: {total_time:.2f} minutes")

if __name__ == "__main__":
    main()
