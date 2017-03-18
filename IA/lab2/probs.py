import itertools


def chess_dist(vect1, vect2):
    """Calculeaza distanta chessboard a 2 vectori"""
    return max([abs(a - b) for a, b in itertools.izip(vect1, vect2)])


def baycurtis_dist(vect1, vect2):
    """Calculeaza distanta bay curtis a 2 vectori"""
    nom = sum([abs(a - b) for a, b in itertools.izip(vect1, vect2)])
    denom = sum([a + b for a, b in itertools.izip(vect1, vect2)])
    return 1. * nom / denom


def adj_list_from_edges(edges):
    """Returns the  adjacency list repr of the tree and the root"""
    adj_list = {}
    parents = set()
    children = set()
    for edge in edges:
        parent, child = edge
        parents.add(parent)
        children.add(child)

        adj_list.setdefault(parent, [])
        adj_list[parent].append(child)

    # add the leafs
    for child in children:
        adj_list.setdefault(child, [])

    # the root is the only vertex who's not a child
    return adj_list, (parents - children).pop()


def bfs(edges):
    """Return the bfs traversal of the tree"""
    adj_list, root = adj_list_from_edges(edges)
    queue = [root]
    visited = set()  # all the visited nodes
    while queue:
        curr_node = queue.pop(0)
        visited.add(curr_node)
        yield curr_node
        unvisited_children = [child for child in adj_list[curr_node] if child not in visited]
        queue.extend(unvisited_children)


def dfs(edges):
    """Return the dfs traversal of the tree"""
    adj_list, root = adj_list_from_edges(edges)
    queue = [root]
    visited = set()  # all the visited nodes
    while queue:
        curr_node = queue.pop(0)
        visited.add(curr_node)
        yield curr_node
        unvisited_children = [child for child in adj_list[curr_node] if
                              child not in visited]
        queue = unvisited_children + queue



def sw_dist(vect1, vect2):
    """Return the Smith-Watermann distance between 2 vectors"""

    s = [[0 for i in range(len(vect2))] for j in range(len(vect1))]
    for i in range(len(vect1)):
        for j in range(len(vect2)):

            if vect1[i] == vect2[j]:
                s[i][j] = 1
            else:
                s[i][j] = -1

    h = [[0 for i in range(len(vect2) + 1)] for j in range(len(vect1) + 1)]
    for i in range(1, len(vect1) + 1):
        for j in range(1, len(vect2) + 1):
            term1 = h[i-1][j-1] + s[i-1][j-1]
            term2 = max([h[i-k][j] - k for k in range(1, i + 1)] or [0])
            term3 = max([h[i][j-l] - l for l in range(1, j + 1)] or [0])
            h[i][j] = max([term1, term2, term3, 0])

    return max([max(line) for line in h])
