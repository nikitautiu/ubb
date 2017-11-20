from unittest import TestCase

from structures import Grammar


class TestGrammar(TestCase):
    def test_grammar(self):
        gramm = Grammar.from_string('S -> @ | aA \n A -> @ | b')

        self.assertSetEqual(gramm.get_non_terminals(), {'S', 'A'})
        self.assertSetEqual(gramm.get_terminals(), {'a', 'b'})
        self.assertFalse(gramm.is_regular())

        gramm = Grammar.from_string('S -> @ | aA \n A -> a | b')
        self.assertTrue(gramm.is_regular())