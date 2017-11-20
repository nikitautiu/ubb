from unittest import TestCase

from structures import Automaton


class TestAutomaton(TestCase):
    def test_automaton(self):
        auto = Automaton.from_string('q0 q1 a\nq0 q1 b\nq1 q1 c\nq0 q1')
        self.assertSetEqual(auto.get_alphabet(), {'a', 'b', 'c'})
        self.assertSetEqual(auto.get_final_states(), {'q1'})