import numpy as np


def to_bit_mask(x, width=None):
    """Returns a boolean array of the binary representation of the given
    number."""
    return np.array(list(np.binary_repr(x, width=width))) == '1'


def from_bit_mask(x):
    """Returns a number from a iterable which is a binary representation of it
    """
    nr = 0
    for i, j in enumerate(x):
        if j:
            nr += 1 << i
    return nr
