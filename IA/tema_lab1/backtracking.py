"""Lab1 - ex 5"""
import itertools


def is_solution(tower):
    """Returns whether a solution is valid or not"""
    for i in range(len(tower) - 1):
        if tower[i][1] == tower[i+1][1]:
            return False
    return True


def generate_towers(cubes, k):
    """Returns a list of lists, of length k, which satisfy the conditions
    that they have ascending lengths and no two adjacent cubes are the same
    color. The cubes are given as a list of tuples(length, color).

    Args:
        cubes: list of pairs(length, color)
        k: the length of the tower
    Returns:
        a list of lists with the index of the cubes in the tower
    """
    # presort them to avoid having to arrange them
    cubes.sort(key=lambda x: x[0])
    for tower in itertools.combinations(cubes, k):
        if is_solution(tower):
            yield tower


def test_backtracking():
    cubes = [(1, 1), (2, 2), (3, 2), (1, 2), (2, 3)]
    k = 3
    expected = [((1, 1), (1, 2), (2, 3)),
                ((1, 1), (2, 2), (2, 3)),
                ((1, 1), (2, 3), (3, 2)),
                ((1, 2), (2, 3), (3, 2)),
                ((2, 2), (2, 3), (3, 2))]
    assert list(generate_towers(cubes, k)) == expected

    cubes = [(1, 1), (2, 2), (3, 2), (1, 2), (2, 3)]
    k = 2
    expected = [((1, 1), (1, 2)),
                ((1, 1), (2, 2)),
                ((1, 1), (2, 3)),
                ((1, 1), (3, 2)),
                ((1, 2), (2, 3)),
                ((2, 2), (2, 3)),
                ((2, 3), (3, 2))]
    assert list(generate_towers(cubes, k)) == expected

    cubes = [(1, 1), (2, 2), (3, 2), (1, 2), (2, 3)]
    k = 1
    expected = [((1, 1),), ((1, 2),), ((2, 2),), ((2, 3),), ((3, 2),)]
    assert list(generate_towers(cubes, k)) == expected

    cubes = [(1, 1), (2, 2), (3, 2), (1, 2), (2, 3)]
    k = 1
    expected = [((1, 1),), ((1, 2),), ((2, 2),), ((2, 3),), ((3, 2),)]
    assert list(generate_towers(cubes, k)) == expected

    cubes = [(1, 1), (5, 2), (1, 2), (1, 2), (1, 3)]
    k = 3
    expected = [((1, 1), (1, 2), (1, 3)),
                ((1, 1), (1, 2), (1, 3)),
                ((1, 1), (1, 3), (5, 2)),
                ((1, 2), (1, 3), (5, 2)),
                ((1, 2), (1, 3), (5, 2))]
    assert list(generate_towers(cubes, k)) == expected
