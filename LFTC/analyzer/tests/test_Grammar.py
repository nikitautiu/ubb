from unittest import TestCase

from lexer.grammar import Grammar, Production


class TestGrammar(TestCase):
    def test_grammar(self):
        gramm = Grammar.from_string('S -> "a" A \n A -> "b"')

        self.assertSetEqual(gramm.get_non_terminals(), {'S', 'A'})
        self.assertSetEqual(gramm.get_terminals(), {'"a"', '"b"'})
        self.assertTrue(gramm.is_regular())

        prods = gramm.get_productions()
        self.assertIn(Production("S", ('"a"', 'A')), prods)

        gramm = Grammar.from_string('Sassas -> "salutarea lume" ABSOLUT  "bine" | "ana" \n ABSO_aLUT -> "basfsaffas"')
        self.assertSetEqual(gramm.get_non_terminals(), {'Sassas', 'ABSO_aLUT'})
        self.assertSetEqual(gramm.get_terminals(), {'"salutarea lume"', '"basfsaffas"', '"bine"', '"ana"'})

