# define literal regexes
from lexer.base import BaseLexer
from parser.grammar import Grammar
from parser.parser import parser_from_grammar

FLOAT_REGEX = r'[-+]?(([1-9][0-9]*)|(0?))(\.[0-9]+)(?![0-9])'
INT_REGEX = r'[-+]?(([1-9][0-9]*)|0)(?![0-9])'
CHAR_REGEX = r'\'[^\']\''
STRING_REGEX = r'"[^"]*"'

IDENTIFIERS = [
    (0, 'IDENTIFIER', r'[_a-zA-Z]+[_0-9a-zA-Z]*')
]

CONSTANTS = [
    (1, 'CONSTANT', r'|'.join(map(lambda x: '(' + x + ')',
                                  [INT_REGEX, FLOAT_REGEX, CHAR_REGEX, STRING_REGEX])))
]

RESERVED = [
    (2, 'const', r'const'),
    (3, 'if', r'if'),
    (4, 'else', r'else'),
    (5, 'while', r'while'),
    (6, 'void', r'void'),
    (7, 'int', r'int'),
    (8, 'float', r'float'),
    (9, 'bool', r'bool'),
    (10, 'char', r'char'),
    (11, 'true', r'true'),
    (12, 'false', r'false'),
    (13, 'return', r'return'),
    (34, 'and', r'and'),
    (35, 'or', r'or'),
]

SEPARATORS = [
    (14, ' ', r'\s+'),
    (15, ';', r';+'),
    (16, ',', r',+'),
    (17, '{', r'\{'),
    (18, '}', r'\}'),
    (19, '(', r'\('),
    (20, ')', r'\)'),
    (21, '[', r'\['),
    (22, ']', r'\]'),
]

OPERATORS = [
    (23, '<=', r'<='),
    (24, '>=', r'>='),
    (25, '==', r'=='),
    (26, '!=', r'!='),
    (27, '<', r'<'),
    (28, '>', r'>'),
    (29, '+', r'\+'),
    (30, '-', r'-'),
    (31, '*', r'\*'),
    (32, '/', r'/'),
    (33, '!', r'!'),
    (36, '^', r'\^'),
    (37, '=', r'='),
]

GRAMMAR = """
program -> func_declaration_list
func_declaration_list -> func_declaration | func_declaration func_declaration_list
func_declaration -> func_header instruction_block
func_header -> func_return "(" ")" | func_return "(" func_signature ")"
func_return -> type "IDENTIFIER" | "void" "IDENTIFIER"
func_signature ->  func_param_declaration | func_param_declaration "," func_signature
func_param_declaration -> type "IDENTIFIER"
instruction_block -> "{" "}" | "{" instruction_list "}"
instruction_list -> instruction | instruction_list instruction
instruction ->  conditional_instr | loop_instr | return_instr | expr_instr | declaration_instr | assign_instr | call_instr
call_instr -> call_expr ";"
declaration_instr -> type assignment_expr_list ";" | type assignment_expr ";"
assignment_expr_list -> assignment_expr | assignment_expr_list "," assignment_expr
type -> "float" | "int" | "bool" | "char" |  array_type_decl
array_type_decl -> "int" array_type | "bool" array_type | "char" array_type | "float" array_type 
array_type -> array_size | array_size array_type
array_size -> "[" "CONSTANT" "]"
conditional_instr -> if_instr elif_instr | if_instr elif_instr_list | if_instr elif_instr else_instr | if_instr elif_instr_list  else_instr
if_instr -> "if" "(" expr ")" instruction_block
elif_instr_list -> elif_instr | elif_instr elif_instr_list
elif_instr -> "if" "else" "(" expr ")" instruction_block
else_instr -> "else" "(" expr ")" instruction_block
loop_instr -> "while" "(" expr ")" instruction_block
return_instr -> "return" expr ";"
assign_instr -> assignment_expr ";"
expr_instr -> expr ";"
expr -> assignment_expr | binary_expr | pre_unary_expr | index_expr | call_expr | "CONSTANT" | "IDENTIFIER"
assignment_expr -> "IDENTIFIER" "=" expr
binary_expr -> expr binary_op expr
binary_op -> "<" | ">" | "<=" | ">=" | "!=" | "==" | "+" | "-" | "/" | "*" | "and" | "or" | "^"
index_expr -> "IDENTIFIER" subscript_list
subscript_list -> subscript | subscript subscript_list
subscript -> "[" expr "]"
pre_unary_expr -> pre_unary_op expr
pre_unary_op -> "+" | "-" | "!"
call_expr -> "IDENTIFIER" "(" call_params ")"
call_params -> expr_list expr | expr
expr_list -> expr | expr "," expr_list
"""


def make_minilang_lexer():
    """Initializes a parser with the default
    minilang specs"""
    return BaseLexer(SEPARATORS, RESERVED, OPERATORS,
                     IDENTIFIERS, CONSTANTS)


def make_minilang_parser():
    """Initializes an LR0 parser for the minilanguage"""
    gramm = Grammar.from_string(GRAMMAR)
    return parser_from_grammar(gramm, 'program')