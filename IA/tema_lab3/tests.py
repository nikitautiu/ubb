import numpy as np
from jewelry import solve_jewelry


def comp_total_price(items, bit_mask):
    return np.array([item[1] for item in items])[bit_mask].sum()


def test_algorithm(algorithm):
    max_weight = 10
    items = [(2, 2), (3, 3), (5, 6), (6, 4)]
    result = solve_jewelry(max_weight, items, algorithm=algorithm)
    assert comp_total_price(items, result[1]) == 11

    max_weight = 17
    items = [(2, 2), (3, 3), (5, 6), (6, 4)]
    result = solve_jewelry(max_weight, items, algorithm=algorithm)
    assert comp_total_price(items, result[1]) == 15

    max_weight = 10
    items = [(2, 1), (1, 3), (11, 6), (10, 4)]
    result = solve_jewelry(max_weight, items, algorithm=algorithm)
    assert comp_total_price(items, result[1]) == 4

    max_weight = 2
    items = [(1, 1), (1, 3), (1, 3), (2, 6)]
    result = solve_jewelry(max_weight, items, algorithm=algorithm)
    assert comp_total_price(items, result[1]) == 6

    max_weight = 1
    items = [(1, 1), (1, 3), (1, 3), (2, 6)]
    result = solve_jewelry(max_weight, items, algorithm=algorithm)
    assert comp_total_price(items, result[1]) == 3