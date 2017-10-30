import re
from collections import namedtuple


# the type to hold the definition of a symbol
SymbolDefinition = namedtuple('SymbolDefinition', ['code', 'id', 'regex'])


def symbol_def_from_list(def_tuples):
    """Given a list of tuples with
    (code, id, regex) generate a list of symbol definitions
    from it"""
    return [SymbolDefinition(code, id, re.compile(r'({}).*'.format(regex))) for code, id, regex in def_tuples]


class ParseError(Exception):
    def __init__(self, *args):
        if len(args) == 3:
            line, pos, line_text = tuple(args)
            error_msg = "A parse error occurred on line {}, position {}: \n {}".format(line, pos, line_text)
            super().__init__(error_msg)
        else:
            super().__init__(args[0])


class SymbolTable(object):
    def __init__(self):
        self.symbols = {}

    def get(self, symbol_hash):
        return self.symbols[symbol_hash]

    def put(self, symbol):
        if symbol not in self.symbols.values():
            self.symbols[hash(symbol)] = symbol
        return hash(symbol)


class BaseLexer(object):
    """Base class for lexing
    Initialized with definitions for the following categories of symbols:
    * separators
    * reserved words
    * operators
    * identifiers
    * constants
    The definitions are given as lists of `SymbolDefinition`.
    """

    def __init__(self, separators, reserved, operators, identifiers, constants):
        # intialize the lists of definitions
        self.constants = symbol_def_from_list(constants)
        self.identifiers = symbol_def_from_list(identifiers)
        self.operators = symbol_def_from_list(operators)
        self.reserved = symbol_def_from_list(reserved)
        self.separators = symbol_def_from_list(separators)

    def parse_with_definitions(self, text, def_list):
        """Given a text, and a list of definitions try to return the definition
        and the full text of the match, if any. If none is matched, return `None`."""
        for definition in def_list:
            match_obj = definition.regex.match(text)
            if match_obj:
                # if there is a match return it
                return definition, match_obj.group(1)
            # otherwise just go on

        # if none is found, return none
        return None

    def parse_text(self, text):
        """Passes over all the definitions in this order
        * separators
        * reserved words
        * constants
        * identifiers
        * operators
        And returns the definition and matching text"""
        for def_list in [self.separators, self.reserved, self.constants,
                         self.identifiers, self.operators]:
            match = self.parse_with_definitions(text, def_list)
            if match:
                if def_list is self.identifiers and len(match[1]) > 8:
                    # check that identifiers are shorter than 8 characters
                    raise ParseError("Identifier {} longer than 8 characters".format(match[1]))
                return match  # return it if it exists

        return None

    def parse(self, text):
        """Receives a text, parses it and returns it internal form and
        the symbol tables corresponding to the identifiers and constants.

        The return value is a tuple of (internal_form, identifier_table, constant_table)"""
        pos = 0
        internal_form = []

        # initialize the symbol tables
        constant_table = SymbolTable()
        identifier_table = SymbolTable()

        # iterate over the string, token by token
        while pos != len(text):
            match = self.parse_text(text[pos:])  # only pos and above
            if match:
                match_def, match_text = match  # unpack the tuple

                index = -1
                # add it to either the constants or identifiers if needed
                if match_def in self.identifiers:
                    index = identifier_table.put(match_text)
                if match_def in self.constants:
                    index = constant_table.put(match_text)

                # index will be -1 if neither of them
                internal_form.append((match_def.code, index))  # add the token

                # increment the position
                pos += len(match_text)
            else:
                # return the rest of the line as the error text
                line_num = text[:pos].count('\n')
                line_pos = pos - text.rfind('\n', 0, pos)  # find position on line
                line_text = text[pos:text.find('\n', pos)]
                raise ParseError(line_num, line_pos, line_text)  # return the verbose error

        # return the result
        return internal_form, identifier_table, constant_table