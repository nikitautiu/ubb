## Sintaxa

```
letter = "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z" | "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z"
nonzero_digit = "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
punctuation = " " | "." | "," | ";" | ":" | "<" | ">" | "=" | "?" | "!" | " "
digit = "0" | nonzero_digit
identifier =  ("_" | letter) [identifier_suffix]
identifier_suffix = {"_" | letter | digit}

const = numeric_literal | char_literal | string_literal | bool_literal
numeric_literal = int_literal | float_literal
int_literal = ["-"|"+"] [nonzero_digit {digit}]
float_literal = ["-"] [nonzero_digit {digit}] ["." {digit}]
string_literal = '"' string_character '"'
char_literal = "'" string_character "'"
string_character = {letter | digit | punctuation}
bool_literal = "true" | "false"
program = {func_declaration}
func_declaration = func_header instruction_block
func_header = func_return "(" [func_signature] ")"
func_return = (type | "void") identifier
func_signature = {func_param_declaration ","} func_param_declaration
func_param_declaration = type identifier
instruction_block = "{" {instruction} "}"
instruction =  conditional_instr | loop_instr | return_instr | expr_instr | declaration_instr
declaration_instr = type {assignment_expr ","} assingnment_expr ";"
type = "float" | "int" | "bool" | "char" [array_type]
array_type = array_size {array_size}
array_size = "[" int_literal "]" 
condtional_instr = if_instr {elif_instr} [else_instr]
if_instr = "if" "(" expr ")" instruction_block
elif_instr = "if" "else" "(" expr ")" instruction_block
else_instr = "else" "(" expr ")" instruction_block
loop_instr = "while" "(" expr ")" instruction_block
return_instr = "return" expr ";"
expr_instr = expr ";"
expr = assignment_expr | binary_expr | pre_unary_expr | index_expr | call_expr | const | identifier
assignment_expr = identifier "=" expr
binary_expr = expr binary_op expr
binary_op = "<" | ">" | "<=" | ">=" | "!=" | "==" | "+" | "-" | "/" | "*" | "&&" | "||" | "^"
index_expr = identifier subscript {subscript}
subscript = "[" expr "]"
pre_unary_expr = pre_unary_op expr
pre_unary_op = "+" | "-"
call_expr = identifier "(" call_params ")"
call_params = {expr ","} expr
```


## Reguli lexicale
```
letter = "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z" | "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z"
nonzero_digit = "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
punctuation = " " | "." | "," | ";" | ":" | "<" | ">" | "=" | "?" | "!" | " "
digit = "0" | nonzero_digit
```

| Tip atom | Cod |
|---|---|
| identifier | 0 |
| const | 1 |
| if | 2 |
| else | 3 |
| while | 4 |
| void | 5 |
| int | 6 |
| float | 7 |
| bool | 8 |
| char | 9 |
| true | 10 |
| false | 11 |
| ; | 12 |
| , | 13 |
| { | 14 |
| } | 15 |
| ( | 16 |
| ) | 17 |
| [ | 18 |
| ] | 19 |
| < | 20 |
| > | 21 |
| <= | 22 |
| \>= | 23 |
| != | 24 |
| == | 25 |
| + | 26 |
| - | 27 |
| / | 28 |
| * | 29 |
| && | 30 |
| || | 31 |
| ^ | 32 |

