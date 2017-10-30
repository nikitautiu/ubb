# Documentatie

## Structura 
Clasa `BaseLexer` implementeaza un analizor lexical de baza care 
primeste 5 liste de definitii de simboluri:
* separators
* reserved 
* operators 
* identifiers 
* constants

Fiecare dintre liste contine tupluri de forma `(cod, denumire, regex)`,
ex. `(10, 'for', r'for')`. Daca apelam metoda `parse` cu un text
aceasta va returna un tuplu `(FIP, TS_ID, TS_CONST)` unde `FIP`
este forma interna a programului(o lista de tupluri `(cod, id_tabela_simboluri)`).
`TS_ID` si `TS_CONST` sunt tabele de simboluri ale identificatorilor
respectiv ale constantelor.

Clasa `SymbolTable` implementeaza 2 metode:
* `put(symbol)` - primeste un simbol si returneaz hash-ul
* `get(hash)` - returneaza simbolul corespunzator hash-ului

In spate acestea folosesc o tabela de dispersie.

## Diagrama de clase
```
+------------+
| ParseError |
+-----+------+
      |
      |
 +----+------+   +--------------------+
 | BaseLexer +---+make_minilang_parser|
 +------+----+   +--------------------+
        |
 +------+-----+
 | SymbolTable|
 +------------+

```

## Tabela codificare

```
FLOAT_REGEX = [-+]?(([1-9][0-9]*)|(0?))(\.[0-9]+)(?![0-9])
INT_REGEX = [-+]?(([1-9][0-9]*)|0)(?![0-9])
CHAR_REGEX = \'[^\']\'
STRING_REGEX = "[^"]*"
```

| cod | nume | regex |
| --- | --- | --- |
| 0 | IDENTIFIER | [_a-zA-Z]+[_0-9a-zA-Z]* | 
| 1 | CONSTANT | INT_REGEX FLOAT_REGEX STRING_REGEX CHAR_REGEX | 
| 2 | const | const | 
| 3 | if | if | 
| 4 | else | else | 
| 5 | while | while | 
| 6 | void | void | 
| 7 | int | int | 
| 8 | float | float | 
| 9 | bool | bool | 
| 10 | char | char | 
| 11 | true | true | 
| 12 | false | false | 
| 13 | return | return | 
| 14 |   | \s+ | 
| 15 | ; | ;+ | 
| 16 | , | ,+ | 
| 17 | { | \{ | 
| 18 | } | \} | 
| 19 | ( | \( | 
| 20 | ) | \) | 
| 21 | [ | \[ | 
| 22 | ] | \] | 
| 23 | <= | <= | 
| 24 | \>= | \>= | 
| 25 | == | == | 
| 26 | != | != | 
| 27 | < | < | 
| 28 | \> | \> | 
| 29 | + | \+ | 
| 30 | - | - | 
| 31 | * | \* | 
| 32 | / | / | 
| 33 | ! | ! | 
| 34 | && | && | 
| 35 | \|\| | \|\| | 
| 36 | ^ | \^ | 
| 37 | = | = | 
 