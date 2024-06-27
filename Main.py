# Main.py
import time
import numpy as np
from SortMethods import SortMethods
from util import generate_sorted_array, generate_reversed_array, generate_random_array

def measure_time(sort_func, arr):
    start_time = time.time_ns()
    sort_func(arr.copy())
    end_time = time.time_ns()
    return end_time - start_time

def main():
    array_sizes = [2**i for i in range(7, 17)]  # Sizes from 128 to 65536
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
    
    for scenario_name, scenario_func in scenarios.items():
        print(f"Testing scenario: {scenario_name}")
        for size in array_sizes:
            print(f"Array size: {size}")
            times = {method: [] for method in sort_methods.keys()}
            
            for _ in range(10):
                arr = scenario_func(size)
                for method_name, sort_func in sort_methods.items():
                    time_taken = measure_time(sort_func, arr)
                    times[method_name].append(time_taken)
            
            # Calculate mean and standard deviation
            for method_name in sort_methods.keys():
                mean_time = np.mean(times[method_name])
                std_dev = np.std(times[method_name])
                filtered_times = [t for t in times[method_name] if (mean_time - std_dev) <= t <= (mean_time + std_dev)]
                final_mean_time = np.mean(filtered_times)
                print(f"{method_name}: {final_mean_time:.2f} ns")

if __name__ == "__main__":
    main()
