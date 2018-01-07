import re
from collections import namedtuple


Production = namedtuple('Production', ['non_terminal', 'symbols'])


def is_terminal(symbol):
    # returns whether the symbol is terminal or not
    return symbol.startswith("\"")


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
        # split it on the arrow
        non_terminal, productions = list(map(lambda x: x.strip(), rule.split('->')))
        for production in productions.strip().split('|'):
            self.productions.append(Production(non_terminal, tuple(re.findall(r'"[^"]+"|[^ ]+', production))))

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

    def get_productions(self, non_terminal=None):
        """Get all productions for a non terminal or all productions in general if unspecified"""
        filtered_terms = filter(lambda x: x.non_terminal == non_terminal or non_terminal is None, self.productions)
        return set(filtered_terms)

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
