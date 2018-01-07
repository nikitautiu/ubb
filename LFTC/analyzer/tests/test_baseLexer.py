from unittest import TestCase

from lexer.base import ParseError, BaseLexer
from tests.utils import remove_space_tokens


class TestBaseLexer(TestCase):
    def setUp(self):
        identifiers = [
            (0, 'IDENTIFIER', r'[a-z][a-z0-9]*')
        ]
        constants = [
            (1, 'CONSTANT', r'(\+|-)?[1-9][0-9]*|0')
        ]
        separators = [
            (2, '(', r'\('),
            (3, ')', r'\)'),
            (4, ';', r';+'),
            (5, ' ', r'\s+')
        ]
        reserved = [
            (6, 'signed', r'signed')
        ]
        operators = [
            (7, '<=', r'<='),
            (8, '>=', r'>='),
            (9, '<', r'<'),
            (10, '>', r'>'),
            (11, '+', r'\+'),
            (12, '-', r'-'),
            (13, '*', r'\*'),
            (14, '/', r'/')
        ]
        # initialize te lexer
        self.lexer = BaseLexer(separators, reserved, operators, identifiers, constants)

    def test_internal_form(self):
        # valid example, test if properly tokenized
        test_string = '1  + 2 - -3 * signed vari <= 15 > 0; 12'
        expected_codes = [
            1,   # 1
            11,  # +
            1,   # 2
            12,  # -
            1,   # -3
            13,  # *
            6,   # signed
            0,   # vari
            7,   # <=
            1,   # 15
            10,  # >
            1,   # 0
            4,   # ;
            1    # 12
        ]

        internal_form, _, _ = self.lexer.parse(test_string)
        code_list = remove_space_tokens(internal_form)  # remove all spaces
        self.assertListEqual(code_list, expected_codes)

        # invalid example, invalid operator ^
        test_string = '1  + 2 - -3 * signed vari <= 15 > 0; ^ 12 /  a + b'
        with self.assertRaises(ParseError):
            self.lexer.parse(test_string)

        # valid multiline test
        test_string = '1  + 2 - -3 * signed vari <= 15 > 0; 12\n  a + b ;; a'
        internal_form, _, _ = self.lexer.parse(test_string)

        # invalid example, invalid operator ^
        test_string = '1  + 2 - -3 * signed vari <= 15 > 0; 12 /  \n a + ^ b'
        with self.assertRaises(ParseError):
            self.lexer.parse(test_string)

        # valid multiline test
        test_string = '1  + 2 - -3 * signed vari <= 15 > 0; 12\n  a + b ;; a'
        internal_form, _, _ = self.lexer.parse(test_string)

        # TODO COMPARISON