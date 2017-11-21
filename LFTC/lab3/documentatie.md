## BNF

```
<letter> ::= "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z" | "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z"
<nonzero_digit> ::= "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
<punctuation> ::= " " | "." | "," | ";" | ":" | "<" | ">" | "=" | "?" | "!" | " "
<digit> ::= "0" | <nonzero_digit>
<identifier> ::=  "_" | <letter> | "_" <identifier_suffix> | <letter> <identifier_suffix>
<identifier_suffix> ::= "_" <identifier_suffix> | <letter> <identifier_suffix> | <digit> <identifier_suffix> | "_" | <letter> | <digit>
<const> ::= <numeric_literal> | <char_literal> | <string_literal> | <bool_literal>
<numeric_literal> ::= <int_literal> | <float_literal>
<int_literal> ::= "-" <number> | "+" <number>  | <number>
<number> ::= <nonzero_digit> | <nonzero_digit> <digit_list>
<digit_list> ::= <digit> | <digit> <digit_list>
<float_literal> ::= "-" <unsigned_float> | "+" <unsigned_float> | <unsigned_float>
<unsigned_float> ::= "." <digit_list> | <number> "." <digit_list> 
<string_literal> ::= '"' <string_character_list> '"' | '"' '"'
<string_character_list> ::= <string_character> | <string_character> <string_character_list> 
<char_literal> ::= "'" <string_character> "'"
<string_character> ::= <letter> | <digit> | <punctuation>
<bool_literal> ::= "<true>" | "<false>"
<program> ::= EPSILON | <func_declaration_list>
<func_declaration_list> ::= <func_declaration> | <func_declaration> <func_declaration_list>
<func_declaration> ::= <func_header> <instruction_block>
<func_header> ::= <func_return> "(" ")" | <func_return> "(" <func_signature> ")"
<func_return> ::= <type> <identifier> | "<void>" <identifier> 
<func_signature> ::=  <func_param_declaration> | <func_param_declaration> "," <func_signature>
<func_param_declaration> ::= <type> <identifier>
<instruction_block> ::= "{" "}" | "{" <instruction_list> "}"
<insturction_list> ::= <instruction> | <instruction> <instruction_list>
<instruction> ::=  <conditional_instr> | <loop_instr> | <return_instr> | <expr_instr> | <declaration_instr>
<declaration_instr> ::= <type> <assignment_expr_list> <assingnment_expr> ";" | <type> <assignment_expr> ";"
<assignment_expr_list> ::= <assignment_expr> "," | <assignment_expr> "," <assignment_expr_list>
<type> ::= "<float>" | "<int>" | "<bool>" | "<char>" | "<float>" <array_type> | "<int>" <array_type> | "<bool>" <array_type> | "<char>" <array_type>
<array_type> ::= <array_size> | <array_size> <array_type>
<array_size> ::= "[" <int_literal> "]" 
<condtional_instr> ::= <if_instr> <elif_instr> | <if_instr> <elif_instr_list> | <if_instr> <elif_instr> <else_instr> | <if_instr> <elif_instr_list> <else_instr>
<if_instr> ::= "if" "(" <expr> ")" <instruction_block>
<elif_instr_list> ::= <elif_instr> | <elif_instr> <elif_instr_list>
<elif_instr> ::= "if" "<else>" "(" <expr> ")" <instruction_block>
<else_instr> ::= "<else>" "(" <expr> ")" <instruction_block>
<loop_instr> ::= "<while>" "(" <expr> ")" <instruction_block>
<return_instr> ::= "<return>" <expr> ";"
<expr_instr> ::= <expr> ";"
<expr> ::= <assignment_expr> | <binary_expr> | <pre_unary_expr> | <index_expr> | <call_expr> | <const> | <identifier>
<assignment_expr> ::= <identifier> "=" <expr>
<binary_expr> ::= <expr> <binary_op> <expr>
<binary_op> ::= "<" | ">" | "<=" | ">=" | "!=" | "==" | "+" | "-" | "/" | "*" | "&&" | "||" | "^"
<index_expr> ::= <identifier> <subscript_list>
<subscript_list> ::= <subscript> | <subscript> <subscript_list>
<subscript> ::= "[" <expr> "]"
<pre_unary_expr> ::= <pre_unary_op> <expr>
<pre_unary_op> ::= "+" | "-" | "!"
<call_expr> ::= <identifier> "(" <call_params> ")"
<call_params> ::= <expr_list> <expr> | <expr>
<expr_list> ::= <expr> | <expr> "," <expr_list>
```


## Context-free Grammar
```
letter -> a | b | c | d | e | f | g | h | i | j | k | l | m | n | o | p | q | r | s | t | u | v | w | x | y | z | A | B | C | D | E | F | G | H | I | J | K | L | M | N | O | P | Q | R | S | T | U | V | W | X | Y | Z
nonzero_digit -> 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
punctuation -> " " | . | , | ; | : | < | > | = | ? | ! | " "
digit -> 0 | nonzero_digit
identifier ->  _ | letter | _ identifier_suffix | letter identifier_suffix
identifier_suffix -> _ identifier_suffix | letter identifier_suffix | digit identifier_suffix | _ | letter | digit
const -> numeric_literal | char_literal | string_literal | bool_literal
numeric_literal -> int_literal | float_literal
int_literal -> - number | + number  | number
number -> nonzero_digit | nonzero_digit digit_list
digit_list -> digit | digit digit_list
float_literal -> - unsigned_float | + unsigned_float | unsigned_float
unsigned_float -> . digit_list | number . digit_list 
string_literal -> " string_character_list " | ""
string_character_list -> string_character | string_character string_character_list 
char_literal -> ' string_character '
string_character -> letter | digit | punctuation
bool_literal -> true | false
program -> EPSILON | func_declaration_list
func_declaration_list -> func_declaration | func_declaration func_declaration_list
func_declaration -> func_header instruction_block
func_header -> func_return ( ) | func_return ( func_signature )
func_return -> type identifier | void identifier 
func_signature ->  func_param_declaration | func_param_declaration , func_signature
func_param_declaration -> type identifier
instruction_block -> { } | { instruction_list }
insturction_list -> instruction | instruction instruction_list
instruction ->  conditional_instr | loop_instr | return_instr | expr_instr | declaration_instr
declaration_instr -> type assignment_expr_list assingnment_expr ; | type assignment_expr ;
assignment_expr_list -> assignment_expr , | assignment_expr , assignment_expr_list
type -> float | int | bool | char | float array_type | int array_type | bool array_type | char array_type
array_type -> array_size | array_size array_type
array_size -> [ int_literal ] 
condtional_instr -> if_instr elif_instr | if_instr elif_instr_list | if_instr elif_instr else_instr | if_instr elif_instr_list else_instr
if_instr -> if ( expr ) instruction_block
elif_instr_list -> elif_instr | elif_instr elif_instr_list
elif_instr -> if else ( expr ) instruction_block
else_instr -> else ( expr ) instruction_block
loop_instr -> while ( expr ) instruction_block
return_instr -> return expr ;
expr_instr -> expr ;
expr -> assignment_expr | binary_expr | pre_unary_expr | index_expr | call_expr | const | identifier
assignment_expr -> identifier = expr
binary_expr -> expr binary_op expr
binary_op -> < | > | <= | >= | != | == | + | - | / | * | && | || | ^
index_expr -> identifier subscript_list
subscript_list -> subscript | subscript subscript_list
subscript -> [ expr ]
pre_unary_expr -> pre_unary_op expr
pre_unary_op -> + | - | !
call_expr -> identifier ( call_params )
call_params -> expr_list expr | expr
expr_list -> expr | expr , expr_list
```

* **Non-terminals**: letter, nonzero_digit, punctuation, digit, nonzero_digit, identifier, letter, identifier_suffix, letter, identifier_suffix, identifier_suffix, identifier_suffix, letter, identifier_suffix, digit, identifier_suffix, letter, digit, const, numeric_literal, char_literal, string_literal, bool_literal, numeric_literal, int_literal, float_literal, int_literal, number, number, number, number, nonzero_digit, nonzero_digit, digit_list, digit_list, digit, digit, digit_list, float_literal, unsigned_float, unsigned_float, unsigned_float, unsigned_float, digit_list, number, digit_list, string_literal, string_character_list, string_character_list, string_character, string_character, string_character_list, char_literal, string_character, string_character, letter, digit, punctuation, bool_literal, true, false, program, func_declaration_list, func_declaration_list, func_declaration, func_declaration, func_declaration_list, func_declaration, func_header, instruction_block, func_header, func_return, func_return, func_signature, func_return, type, identifier, void, identifier, func_signature, func_param_declaration, func_param_declaration, func_signature, func_param_declaration, type, identifier, instruction_block, instruction_list, insturction_list, instruction, instruction, instruction_list, instruction, conditional_instr, loop_instr, return_instr, expr_instr, declaration_instr, declaration_instr, type, assignment_expr_list, assingnment_expr, type, assignment_expr, assignment_expr_list, assignment_expr, assignment_expr, assignment_expr_list, type, float, int, bool, char, float, array_type, int, array_type, bool, array_type, char, array_type, array_type, array_size, array_size, array_type, array_size, int_literal, condtional_instr, if_instr, elif_instr, if_instr, elif_instr_list, if_instr, elif_instr, else_instr, if_instr, elif_instr_list, else_instr, if_instr, expr, instruction_block, elif_instr_list, elif_instr, elif_instr, elif_instr_list, elif_instr, else, expr, instruction_block, else_instr, else, expr, instruction_block, loop_instr, while, expr, instruction_block, return_instr, return, expr, expr_instr, expr, expr, assignment_expr, binary_expr, pre_unary_expr, index_expr, call_expr, const, identifier, assignment_expr, identifier, expr, binary_expr, expr, binary_op, expr, binary_op, index_expr, identifier, subscript_list, subscript_list, subscript, subscript, subscript_list, subscript, expr, pre_unary_expr, pre_unary_op, expr, pre_unary_op, call_expr, identifier, call_params, call_params, expr_list, expr, expr, expr_list, expr, expr, expr_list, 
* **Terminals**: !, !=, &&, (, ), *, +, ,, , , -, ., /, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, :, ;, <, <=, =, ==, >, >=, ?, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, \[, \], ^, _, a, b, bool, c, char, d, e, else, f, false, float, g, h, i, if, int, j, k, l, m, n, o, p, q, r, return, s, t, true, u, v, void, w, while, x, y, z, {, ||, }
