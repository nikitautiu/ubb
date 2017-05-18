import numpy as np


def class_error(inputs, weights, threshold):
    inputs, weights = np.array(inputs), np.array(weights)
    X = inputs[:, :-1]
    Y = inputs[:, -1]
    sigm = 1 / (1 + np.exp(-(np.sum(X * weights, axis=1))))
    return np.mean(((sigm > threshold) * 1) != Y)


def polish_eval(expr):
    stack = []
    expr = reversed(expr)
    operators = {
        '+': lambda x, y: x + y,
        '-': lambda x, y: x - y,
        '/': lambda x, y: x / y,
        '*': lambda x, y: x * y,
    }

    for token in expr:
        if token in operators.keys():
            op2 = stack.pop(1)
            op1 = stack.pop(0)
            result = operators[token](op1, op2)
            stack.insert(0, result)
        else:
            stack.insert(0, token)
    return stack.pop()


def polish_with_mapping(expr, values, mapping=None):
    if mapping is None:
        # create a->0, b->1, ... mapping if not explicitly specified
        mapping = {chr(ord('a') + diff): diff for diff in range(values.size)}

    # split the expression and replace with mapping
    expanded_expr = [tok if tok not in mapping else values[mapping[tok]]
                     for tok in expr.split(' ')]
    return polish_eval(expanded_expr)


def polish(expr, values):
    return np.apply_along_axis(lambda x: polish_with_mapping(expr, x),
                               arr=values, axis=1)


def regr_error(expr, values):
    values = np.array(values, dtype=int)
    X = values[:, :-1]
    Y = values[:, -1]
    return np.sum(np.abs(polish(expr, X) - Y))


def test_regr_error():
    assert regr_error('- b * a c', [[1, 2, 3, 4], [2, 3, 4, 5]]) == 15
    assert regr_error('- b * a c', [[1, 2, 3, -1], [2, 3, 4, -5]]) == 0
    assert regr_error('- b + a c', [[1, 2, 3, -1], [2, 3, 4, -5]]) == 3
    assert regr_error('- b + a c', [[1, 2, 3, -1], [2, 3, 4, 0]]) == 4
    assert regr_error('+ - c + a c b', [[1, 2, 3, -1], [2, 3, 4, 0]]) == 3


def test_class_error():
    assert class_error([[1, 2, 3, 0], [2, 3, 4, 1], [0.01, 0.1, 1, 0]],
                       [0.5, 0.0001, 0.025], 0.6) == 0.33333333333333331

    assert class_error([[1, 2, 3, 0], [2, 3, 4, 1], [0.01, 0.1, 1, 0]],
                       [0.5, 0.0001, 0.025], 0.2) == 0.66666666666666663

    assert class_error([[1, 2, 3, 0], [2, 3, 4, 1], [0.01, 0.1, 1, 0]],
                       [0.005, 1, 0.025], 0.5) == 0.66666666666666663

    assert class_error([[1, 2, 3, 0], [2, 3, 4, 0], [0.01, 0.1, 1, 0]],
                       [0.5, 0.0001, 0.025], 0.5) == 1

    assert class_error([[1, 2, 3, 1], [2, 3, 4, 1], [0.01, 0.1, 1, 1]],
                       [0.5, 0.0001, 0.025], 0.5) == 0


