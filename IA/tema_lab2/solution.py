import heapq
import numpy as np


DIRS = {'U': (-1, 0),
        'L': (0, -1),
        'D': (1, 0),
        'R': (0, 1)}


def get_neighbors(state):
        board = state['board']
        moves = state['moves']

        lim_x, lim_y = board.shape
        blank_pos = np.where(board == 0)
        blank_x, blank_y = blank_pos[0][0], blank_pos[1][0]
        for name, direction in DIRS.items():
            next_x, next_y = blank_x + direction[0], blank_y + direction[1]
            if 0 <= next_x < lim_x and 0 <= next_y < lim_y:
                new_board = np.copy(board)
                new_board[blank_x, blank_y], new_board[next_x, next_y] = new_board[next_x, next_y], new_board[blank_x, blank_y]
                yield {'board': new_board, 'moves': moves + name}


def encode(array):
    return ','.join([str(x) for x in array.ravel()])


def bfs(initial_board, final_board):
    """BFS search for the n-puzzle
    Receives the initial and final board as arguments
    and returns the moves necessary to get to that."""
    to_visit = list()
    visited = set()
    to_visit.append({'board': initial_board, 'moves': ''})

    while to_visit:
        current_state = to_visit.pop(0)
        visited.add(encode(current_state['board']))

        for neighbor in get_neighbors(current_state):
            if np.array_equal(neighbor['board'], final_board):
                return neighbor['moves']
            if encode(neighbor['board']) not in visited:
                to_visit.append(neighbor)
    return None


def get_cost(board, final_board):
    """Returns the sum of all Manhattan distances
    between the positions aof the numbers and their final positions."""
    total_manhattan = 0
    for (x, y), num in np.ndenumerate(board):
        final_pos = np.where(final_board == num)
        final_x, final_y = final_pos[0][0], final_pos[1][0]
        total_manhattan += abs(x - final_x) + abs(y - final_y)
    return total_manhattan


class QueueItem(object):
    def __init__(self, priority, item):
        self. priority = priority
        self.item = item

    def __lt__(self, other):
        return self.priority < other.priority

    def __gt__(self, other):
        return self.priority > other.priority

    def __le__(self, other):
        return self.priority <= other.priority

    def __ge__(self, other):
        return self.priority >= other.priority


def gbfs(initial_board, final_board):
    """GBFS search for the n-puzzle
    Receives the initial and final board as arguments."""
    to_visit = list()
    visited = set()

    # just like the bfs but using a priority queue
    heapq.heappush(to_visit, QueueItem(0, {'board': initial_board, 'moves': ''}))

    while to_visit:
        current_state = to_visit[0].item
        heapq.heappop(to_visit)
        visited.add(encode(current_state['board']))

        for neighbor in get_neighbors(current_state):
            if np.array_equal(neighbor['board'], final_board):
                return neighbor['moves']
            if encode(neighbor['board']) not in visited:
                heapq.heappush(to_visit, QueueItem(get_cost(neighbor['board'], final_board), neighbor))
    return None


def test_bfs():
    input_initial = np.array([[0, 1],
                              [3, 2]])
    input_final = np.array([[3, 1],
                            [2, 0]])
    expected_output = 'DR'
    assert(bfs(input_initial, input_final) == expected_output)

    input_initial = np.array([[0, 1, 3],
                              [4, 2, 5],
                              [6, 7, 8]])
    input_final = np.array([[0, 1, 2],
                            [3, 4, 5],
                            [6, 7, 8]])
    expected_output = 'RDLURRDLULDRRULL'
    assert(bfs(input_initial, input_final) == expected_output)

    input_initial = np.array([[0, 1, 3],
                              [4, 2, 5],
                              [6, 7, 8]])
    input_final = np.array([[1, 2, 3],
                            [0, 4, 5],
                            [6, 7, 8]])
    expected_output = 'RDL'
    assert (bfs(input_initial, input_final) == expected_output)

    input_initial = np.array([[0, 1, 3],
                              [4, 2, 5],
                              [6, 7, 8]])
    input_final = np.array([[1, 2, 0],
                            [3, 4, 5],
                            [6, 7, 8]])
    expected_output = 'RDLURRDLULDRRU'
    assert (bfs(input_initial, input_final) == expected_output)

    input_initial = np.array([[0, 1],
                              [3, 2]])
    input_final = np.array([[3, 0],
                            [2, 1]])
    expected_output = 'DRU'
    assert (bfs(input_initial, input_final) == expected_output)


def test_gbfs():
    input_initial = np.array([[0, 1],
                              [3, 2]])
    input_final = np.array([[3, 1],
                            [2, 0]])
    assert gbfs(input_initial, input_final) == 'DR'

    input_initial = np.array([[0, 1, 3],
                              [4, 2, 5],
                              [6, 7, 8]])
    input_final = np.array([[1, 2, 3],
                            [0, 4, 5],
                            [6, 7, 8]])

    assert gbfs(input_initial, input_final) == 'RDL'

    input_initial = np.array([[0, 1, 3],
                              [4, 2, 5],
                              [6, 7, 8]])
    input_final = np.array([[1, 2, 0],
                            [3, 4, 5],
                            [6, 7, 8]])
    assert gbfs(input_initial, input_final) == 'RDLURRDLULDRRU'

    input_initial = np.array([[0, 1],
                              [3, 2]])
    input_final = np.array([[3, 0],
                            [2, 1]])
    assert gbfs(input_initial, input_final) == 'DRU'

    input_initial = np.array([[0, 1, 3],
                              [4, 2, 5],
                              [6, 7, 8]])
    input_final = np.array([[0, 1, 2],
                            [3, 4, 5],
                            [6, 7, 8]])
    assert gbfs(input_initial, input_final) == 'RRDLULDRRULDRULLDRRULDLURRDLUL'

