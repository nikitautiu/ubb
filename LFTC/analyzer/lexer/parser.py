from collections import namedtuple, defaultdict

from lexer.grammar import is_terminal, Production


class Configuration(object):
    def __init__(self, non_terminal, symbols, dot=0):
        self.dot = dot
        self.symbols = tuple(symbols)
        self.non_terminal = non_terminal

    @staticmethod
    def from_production(prod):
        return Configuration(prod.non_terminal, prod.symbols, 0)

    def to_production(self):
        return Production(self.non_terminal, self.symbols)

    def advance(self):
        """Move the dot to the right"""
        return Configuration(self.non_terminal, self.symbols, self.dot + 1)

    @property
    def is_final(self):
        """Can no longer shift dot"""
        return self.dot == len(self.symbols)

    @property
    def current_symbol(self):
        """Return the current symbol to shif t over"""
        return self.symbols[self.dot]

    @property
    def transitioned_symbol(self):
        """If used as a transition, get the last symbol"""
        return self.symbols[self.dot - 1]

    def __str__(self):
        """Returns the representation of the config"""
        return "{} -> {} . {}".format(self.non_terminal,
                                      ' '.join(self.symbols[:self.dot]),
                                      ' '.join(self.symbols[self.dot:]))

    def __repr__(self):
        return str(self)

    def __eq__(self, other):
        return self.non_terminal == other.non_terminal and self.symbols == other.symbols and self.dot == other.dot

    def __hash__(self):
        return hash(self.non_terminal) + hash(self.symbols) + hash(self.dot)


Transition = namedtuple('Transition', ['end', 'symbol'])


class StateMachineCreator(object):
    def __init__(self, grammar, start_symbol):
        self.grammar = grammar
        self.start_symbol = start_symbol

    def get_next_id(self):
        return max(self.configuration_sets.keys(), default=-1) + 1

    def get_closure(self, config_set):
        """Get the set with all the derivations for the
        given config set"""
        new_set = {item for item in config_set}

        old_len = -1
        ok = True
        # derive until no more
        while ok:
            for config in {item for item in new_set}:
                derivs = self.get_derivations(config)
                new_set |= derivs
            ok = len(new_set) != old_len
            old_len = len(new_set)
        return new_set

    def get_derivations(self, config):
        """Returns the derivations of a certain config"""
        if not config.is_final:
            symbol = config.current_symbol
            if not is_terminal(symbol):
                # add all the derivations, with dot at the beginning
                return {Configuration.from_production(prod)
                        for prod in self.grammar.get_productions(symbol)}

        return set()

    def add_possible_transitions(self, state_ind):
        """Add all possible transitions give a state"""
        new_transitions = {}  # a dict of symbol: new_state

        for config in self.configuration_sets[state_ind]:
            if not config.is_final:
                # can transition some more
                next_config = config.advance()
                transition_symbol = next_config.transitioned_symbol

                # append all transitions through one symbol to the same state
                new_transitions[transition_symbol] = new_transitions.get(transition_symbol, set()) | {next_config}

        # expend the closures
        new_transitions = {key: self.get_closure(state) for key, state in new_transitions.items()}

        # add the transitons and if necessary, the new state if not in the big set
        for symbol, new_state in new_transitions.items():
            destination_ind = next((ind for ind, state in self.configuration_sets.items() if state == new_state), None)
            if destination_ind is None:
                destination_ind = self.get_next_id()  # make a new state
                self.configuration_sets[destination_ind] = new_state

            # add the transition ot the new, or existing state
            self.transitions[state_ind] |= {Transition(destination_ind, symbol)}

    def build_from_productions(self):
        self.configuration_sets = {}  # a dictionary containing all the states
        self.transitions = defaultdict(lambda: set())  # a set of transitions for each

        # add the intial state
        self.configuration_sets[self.get_next_id()] = self.get_closure({Configuration('START', (self.start_symbol,))})

        pos = 0
        while pos != len(self.configuration_sets):
            # iterate all configuration sets,
            self.add_possible_transitions(pos)
            pos += 1

    def get_states_and_transitions(self):
        return self.configuration_sets, self.transitions


def state_automaton_from_grammar(grammar, start_symbol):
    """Given a grammar and a start symbol, return the state machine
    to build the table from"""
    processor = StateMachineCreator(grammar, start_symbol)
    processor.build_from_productions()
    return processor.configuration_sets, processor.transitions


def get_successors(transitions):
    """Given a dictionary of transitions return
    the succesors [start][symbol] -> end"""
    succesors = {}

    for start, trans in transitions.items():
        succesors[start] = {}
        for transition in trans:
            # we can retrieve the transitioned symbol from the configuration
            succesors[start][transition.symbol] = transition.end

    return succesors


def table_from_grammar(grammar, start_symbol):
    """Givena a grammar and a start sybol, return the LR0 table and goto"""
    states, transitions = state_automaton_from_grammar(grammar, start_symbol)
    successors = get_successors(transitions)

    # table is actually a sparse matrix with default dicts
    actions = defaultdict(lambda: defaultdict(lambda: {'action': 'ERROR'}))  # defaults to errors
    goto = defaultdict(lambda: {})

    cols = grammar.get_terminals() | {'END'}  # mark the end with END

    for ind, state in states.items():
        for config in state:
            # every action from this row means reduction
            if config.non_terminal != 'START' and config.is_final:
                for col in cols:
                    actions[ind][col] = {'action': 'REDUCE', 'production': config.to_production()}

            # shift actions
            if not config.is_final and is_terminal(config.current_symbol):
                current_successor = successors.get(ind, {}).get(config.current_symbol, None)
                if current_successor is not None:
                    # must check that it actually transitions. may be redundant
                    actions[ind][config.current_symbol] = {'action': 'SHIFT', 'end': current_successor}

        if Configuration('START', (start_symbol,), 1) in state:
            actions[ind]['END'] = {'action': 'ACCEPT'}  # accept action

    # compute the gotos, easy with successors
    for start, succesor_dict in successors.items():
        for symbol, end in succesor_dict.items():
            if not is_terminal(symbol):
                # the keys are the so not needed
                goto[start][symbol] = end

    return actions, goto
