import re
from collections import namedtuple

Production = namedtuple('Production', ['non_terminal', 'symbols'])
Transition = namedtuple('Transition', ['start_state', 'end_state', 'symbol'])


def is_terminal(symbol):
    # returns whether the symbol is terminal or not
    return not symbol.isupper() and symbol != '@'


def is_production_regular(prod):
    """Checks whether a production is regular"""
    return len(prod.symbols) <= 2 and all(map(is_terminal, prod.symbols[:-1]))


class Grammar(object):
    """Class that encapsulates a grammar"""

    def __init__(self):
        self.productions = []

    def _add_rule(self, rule):
        """Given a rule "S -> aA| bB" process it
        and add it to the grammar"""
        rule = re.sub(r'\s*', '', rule)

        # split it on the arrow
        non_terminal, productions = rule.split('->')
        for production in productions.split('|'):
            self.productions.append(Production(non_terminal, list(production)))

    @staticmethod
    def from_string(repr):
        """Parses grammar from string"""
        gramm = Grammar()

        for rule in repr.strip().split('\n'):
            gramm._add_rule(rule)

        return gramm

    def to_string(self):
        """Returns the representation of the grammar"""
        production_dict = self.get_production_dict()

        string_prods = ['S -> ' + ' | '.join([''.join(symbols) for symbols in production_dict.pop('S')])]
        for non_terminal, symbols_list in production_dict.items():
            string_prods.append(non_terminal + ' -> ' + ' | '.join([''.join(symbols) for symbols in symbols_list]))

        # concateate em
        return '\n'.join(string_prods)

    def get_production_dict(self):
        production_dict = {}
        for prod in self.productions:
            non_terminal, symbols = prod.non_terminal, prod.symbols
            production_dict.setdefault(non_terminal, [])
            production_dict[non_terminal].append(symbols)  # add the symbols
        return production_dict

    def get_non_terminals(self):
        """Returns a set of all nonterminals"""
        return {non_terminal for non_terminal, symbols in self.productions}

    def get_terminals(self):
        """Return the set of terminals"""
        return {symbol for _, symbols in self.productions for symbol in symbols if is_terminal(symbol)}

    def get_productions(self, non_terminal):
        """Get all productions for a non terminal"""
        return set(filter(lambda x: x.non_terminal == non_terminal, self.productions))

    def is_regular(self):
        """Returns whether the grammar is regular or not"""
        # if they are the form aA
        are_productions_regular = all(map(is_production_regular, self.productions))
        if not are_productions_regular:
            return False

        # those that contain epsilon
        epsilon_prods = {prod.non_terminal for prod in self.productions if '@' in prod.symbols}
        if len(epsilon_prods) > 1 or (len(epsilon_prods) == 1 and list(epsilon_prods)[0] != 'S'):
            return False  # epsilon not in S
        s_has_epsilon = len(epsilon_prods) == 1 and list(epsilon_prods)[0] == 'S'
        s_producing_prods = {prod.non_terminal for prod in self.productions if 'S' in prod.symbols}

        # if s contains epsilon, it doesn't appear in other productions
        if s_has_epsilon and (
                len(s_producing_prods) > 1 or (len(s_producing_prods) == 1 and list(s_producing_prods)[0] != 'S')):
            return False

        return True

    def to_automaton(self):
        """Returns the corresponding automaton if the grammar is regular.
        Otherwise, raise a value error"""
        if not self.is_regular():
            raise ValueError('Grammar not regular')

        # get production dict
        prod_dict = self.get_production_dict()

        auto = Automaton()
        auto.start_state = 'S'
        auto.final_states = ['K']
        if ['@'] in prod_dict['S']:
            auto.final_states.append('S')  # s is also a final state

        # other productions
        for start, symbol_list in prod_dict.items():
            for symbols in symbol_list:
                if symbols != ['@']:
                    if is_terminal(symbols[-1]):
                        auto.transitions.append(Transition(start, 'K', symbols[-1]))
                    else:
                        auto.transitions.append(Transition(start, symbols[-1], symbols[0]))

        return auto


class Automaton(object):
    """Class that encapsulates an automaton"""

    def __init__(self):
        self.transitions = []
        self.final_states = []
        self.start_state = None

    @staticmethod
    def from_string(repr):
        """Constructs an automaton from a string"""
        auto = Automaton()
        lines = [line.strip() for line in repr.split('\n')]
        auto.transitions = [Transition(*line.split()) for line in lines[:-1]]
        auto.final_states = lines[-1].split()[1:]
        auto.start_state = lines[-1].split()[0]

        return auto

    def get_alphabet(self):
        """Returns the alphabet"""
        return {trans.symbol for trans in self.transitions}

    def get_final_states(self):
        """Returns all final states"""
        return set(self.final_states)

    def get_transitions(self):
        """Returns all transitions"""
        return set(self.transitions)

    def get_states(self):
        """REturns all states"""
        return {trans.start_state for trans in self.transitions} | {trans.end_state for trans in self.transitions}

    def to_string(self):
        """Return a string representation of the automaton"""
        return '\n'.join([' '.join([trans.start_state, trans.end_state, trans.symbol])
                          for trans in self.transitions]) + '\n' + self.start_state + ' ' + ' '.join(self.final_states)

    def to_grammar(self):
        """Returns the grammar corresponding to the automaton"""
        gramm = Grammar()

        state_mapping = {st: chr(ord('A') + i) for i, st in enumerate(self.get_states())}
        state_mapping[self.start_state] = 'S'

        # empty productions at first
        final_states = self.get_final_states()
        if self.start_state in final_states:
            gramm.productions.append(Production('S', ['@']))

        for trans in self.get_transitions():
            if trans.end_state in self.get_final_states():
                gramm.productions.append((Production(state_mapping[trans.start_state],
                                                     [trans.symbol])))

            gramm.productions.append((Production(state_mapping[trans.start_state],
                                                 [trans.symbol, state_mapping[trans.end_state]])))

        return gramm
