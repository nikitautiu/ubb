# define literal regexes
from lexer.base import BaseLexer

FLOAT_REGEX = r'[-+]?(([1-9][0-9]*)|(0?))(\.[0-9]+)(?![0-9])'
INT_REGEX = r'[-+]?(([1-9][0-9]*)|0)(?![0-9])'
CHAR_REGEX = r'\'[^\']\''
STRING_REGEX = r'"[^"]*"'

IDENTIFIERS = [
    (0, 'IDENTIFIER', r'[_a-zA-Z]+[_0-9a-zA-Z]*')
]

CONSTANTS = [
    (1, 'CONSTANT', r'|'.join(map(lambda x: '(' + x + ')',
                                  [INT_REGEX, FLOAT_REGEX, CHAR_REGEX, STRING_REGEX])))
]

RESERVED = [
    (2, 'const', r'const'),
    (3, 'if', r'if'),
    (4, 'else', r'else'),
    (5, 'while', r'while'),
    (6, 'void', r'void'),
    (7, 'int', r'int'),
    (8, 'float', r'float'),
    (9, 'bool', r'bool'),
    (10, 'char', r'char'),
    (11, 'true', r'true'),
    (12, 'false', r'false'),
    (13, 'return', r'return'),

]

SEPARATORS = [
    (14, ' ', r'\s+'),
    (15, ';', r';+'),
    (16, ',', r',+'),
    (17, '{', r'\{'),
    (18, '}', r'\}'),
    (19, '(', r'\('),
    (20, ')', r'\)'),
    (21, '[', r'\['),
    (22, ']', r'\]'),
]

OPERATORS = [
    (23, '<=', r'<='),
    (24, '>=', r'>='),
    (25, '==', r'=='),
    (26, '!=', r'!='),
    (27, '<', r'<'),
    (28, '>', r'>'),
    (29, '+', r'\+'),
    (30, '-', r'-'),
    (31, '*', r'\*'),
    (32, '/', r'/'),
    (33, '!', r'!'),
    (34, '&&', r'&&'),
    (35, '||', r'\|\|'),
    (36, '^', r'\^'),
    (37, '=', r'='),
]


def make_minilang_parser():
    """Initializes a parser with the default
    minilang specs"""
    return BaseLexer(SEPARATORS, RESERVED, OPERATORS,
                     IDENTIFIERS, CONSTANTS)