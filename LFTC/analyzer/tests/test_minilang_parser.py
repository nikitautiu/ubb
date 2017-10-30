from unittest import TestCase

from lexer import make_minilang_parser, ParseError
from tests.utils import remove_space_tokens


class TestMinilang(TestCase):
    def test_minilang(self):
        parser = make_minilang_parser()

        test_text = "func(001, b)"
        with self.assertRaises(ParseError):
            parser.parse(test_text)

        test_text = "func(1, 2, c);"
        expected_tokens = [0, 19, 1, 16, 1, 16, 0, 20, 15]
        tokens, _, _ = parser.parse(test_text)
        self.assertListEqual(remove_space_tokens(tokens, 14), expected_tokens)

        test_text = """
        void print(int a, int b[10]) {
            int x = 10, y = a;
            while(x < a) {
                x = x + y;
                y = a;
            }
            return "ana are mere";
        }
        """
        expected_tokens = [6, 0, 19, 7, 0, 16, 7, 0, 21, 1, 22, 20, 17, 7, 0, 37, 1, 16, 0, 37, 0, 15, 5, 19, 0, 27, 0, 20, 17, 0, 37, 0, 29, 0, 15, 0, 37, 0, 15, 18, 13, 1, 15, 18]
        tokens, ids, consts = parser.parse(test_text)
        self.assertListEqual(remove_space_tokens(tokens, 14), expected_tokens)
        self.assertSetEqual(set(ids.symbols.values()), {'a', 'b', 'x', 'y', 'print'})
        self.assertSetEqual(set(consts.symbols.values()), {'10', '"ana are mere"'})


        test_text = """
        void print(int a, int b[10]) {
            int x = 10, y = a;
            while(x < a) {
                x = x + y;
                y = a;
            }
            x(1, 2, __asdfghjklqwe);
            return 7;
        }
        """
        with self.assertRaises(ParseError):
            parser.parse(test_text)