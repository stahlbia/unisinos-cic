# util.py
import random

def generate_sorted_array(n):
    return list(range(n))

def generate_reversed_array(n):
    return list(range(n, 0, -1))

def generate_random_array(n, unique=True):
    if unique:
        return random.sample(range(n * 2), n)
    else:
        return [random.randint(0, n) for _ in range(n)]
