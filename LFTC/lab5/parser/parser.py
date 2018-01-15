import plyplus, plyplus.grammars
from plyplus import Grammar

prog_parser = Grammar("""
start: program;
IDENTIFIER: '[_a-zA-Z]+[_0-9a-zA-Z]*' 
    (%unless
        VOID: 'void';
        FLOAT: 'float';
        INT: 'int';
        BOOL: 'bool';
        CHAR: 'char';
        IF: 'if';
        ELSE: 'else';
        WHILE: 'while';
        RETURN: 'return';
    )
    ;

CONST: '([-+]?(([1-9][0-9]*)|0)(?![0-9]))|([-+]?(([1-9][0-9]*)|(0?))(\.[0-9]+)(?![0-9]))|("[^"]*")' ;
SPACES: '[ \t\n]+' (%ignore) ; 
OPASSIGN: '\+=|-=|\*=|/=|\*=|&&=|\|\|=|\^=|=';

program: func_declaration_list ;
func_declaration_list: func_declaration | func_declaration+ func_declaration_list ;
func_declaration: func_header instruction_block ;
func_header: func_return '\(' '\)' | func_return '\(' func_signature '\)' ;
func_return: type IDENTIFIER | VOID IDENTIFIER  ;
@func_signature:  func_param_declaration | (func_param_declaration ',')+ func_signature ;
func_param_declaration: type param ;
param: IDENTIFIER;
instruction_block: '\{' '\}' | '\{' instruction_list '\}' ;
@instruction_list: instruction | instruction+ instruction_list ;
@instruction:  conditional_instr | loop_instr | return_instr | call_instr | declaration_instr | assignment_instr;
declaration_instr: type assignment_expr_list';' ;
@assignment_expr_list: assignment_expr | (assignment_expr ',')+ assignment_expr_list ;
assignment_instr: assignment_expr_list ';' ;
type: FLOAT | INT | BOOL | CHAR | FLOAT array_type | INT array_type | BOOL array_type | CHAR array_type ;
array_type: array_size | array_size array_type ;
array_size: '\[' CONST '\]'  ;
@conditional_instr: if_instr elif_instr | if_instr elif_instr_list | if_instr elif_instr else_instr | if_instr elif_instr_list else_instr ;
if_instr: IF '\(' condition '\)' instruction_block ;
elif_instr_list: elif_instr | elif_instr elif_instr_list ;
elif_instr: IF ELSE '\(' condition '\)' instruction_block ;
else_instr: ELSE '\(' condition '\)' instruction_block ;
loop_instr: WHILE '\(' condition '\)' instruction_block ;
condition: expr;
return_instr: RETURN ret_value ';' ;
ret_value: expr;
call_instr: call_expr ';' ;
@expr: binary_expr | pre_unary_expr | index_expr | call_expr | CONST | IDENTIFIER ;
assignment_expr: lvalue assign_op rvalue ;
assign_op: OPASSIGN ;
lvalue: IDENTIFIER ;
rvalue: expr;
binary_expr: term binary_op term ;
term: expr;
binary_op: '<' | '>' | '<=' | '>=' | '!=' | '==' | '\+' | '-' | '/' | '\*' | '&&' | '\|\|' | '\^' ;
index_expr: IDENTIFIER subscript_list ;
subscript_list: subscript | subscript+ subscript_list ;
subscript: '\[' expr '\]' ;
pre_unary_expr: pre_unary_op term ;
pre_unary_op: '\+' | '-' | '!' ;
call_expr: IDENTIFIER '\(' expr_list '\)' ;
@expr_list: param | (param ',')+ expr_list ;
param: expr;
""")

res = prog_parser.parse("""
int print(int a, int b) {
    int x = 10, y = a + 2;
    while(x < a) {
        x = x - y;
        y += a;
    }
    x(1, 2, 1);
    return 7;
}
""")

res.to_png_with_pydot('example.png')