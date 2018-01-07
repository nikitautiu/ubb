from unittest import TestCase

from lexer.grammar import Grammar
from lexer.parser import state_automaton_from_grammar, table_from_grammar


class TestStateCreator(TestCase):
    def test_state_creation(self):
        gramm = Grammar.from_string('E -> E "+" T | T \n T -> "(" E ")" | "id"')
        config_sets, transitions = state_automaton_from_grammar(gramm, 'E')
        self.assertEqual(len(config_sets), 9)
        self.assertEqual(sum(map(len, transitions.values())), 14)

        actions, gotos = table_from_grammar(gramm, 'E')
        indiv_acts = [item for row in actions.values() for item in row.values()]

        # get the number of actions
        self.assertEqual(sum((act['action'] == 'SHIFT' for act in indiv_acts)), 9)
        self.assertEqual(sum((act['action'] == 'ACCEPT' for act in indiv_acts)), 1)
        self.assertEqual(sum((act['action'] == 'REDUCE' for act in indiv_acts)), 20)

        indiv_gotos = [item for row in gotos.values() for item in row.values()]
        self.assertEqual(len(indiv_gotos), 5)


