# Documentatie
Pentru parsarea unor limbaje date, implementam in Python un parser LR(0) care primeste un CFG si isi generaza regulile de parsare. Dupa generarea regulilor el poate primi un sir de token-i si returneaza daca a avut sau nu succes parasarea si sirul de derivare care a dus la input.

## Structura 
### Lexer
Clasa `BaseLexer` implementeaza un analizor lexical de baza care 
primeste 5 liste de definitii de simboluri:
* separators
* reserved 
* operators 
* identifiers 
* constants**

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

#### Diagrama de clase
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

### Parser
Parser-ul primeste un obiect de tip `Grammar` si un simbol neterminal de start pe baza caruia isi construieste reguluile. Folosind algoritmul de construire a unui RL(0). Procedeul este impartit in 2 clase.

Prima primeste gramatica si un simbol de inceput si returneaza 2 tabele: unul de actiuni si unul de goto.

Acestea pot fi date ulterior la o clasa `Parser` care implementeaza algoritmul de parsare LR. Aceaste primeste un sir de tokeni ca cel dat de lexer.

#### Diagrama de clase
```
+------------+       +------------+
| Grammar    |-------| Production |
+-----+------+       +------------+
      |
      |
 +----+--------------------------+   +---------------+
 | StateMachineCreator           |---+ Configuration |
 +------+------------------------+   +---------------+
        |
 +------+-----+
 | Parser     |
 +------------+

```