#!/bin/env python3
import click

from structures import Grammar, Automaton


def output_grammar(gramm):
    """Output a grammar and its features"""
    click.secho('GRAMMAR', bold=True)
    click.echo(gramm.to_string())
    click.echo('Non-terminals: ' + ' '.join(gramm.get_non_terminals()))
    click.echo('Terminals: ' + ' '.join(gramm.get_terminals()))

    production_dict = gramm.get_production_dict()
    click.echo('Productions:')
    for non_terminal, symbols_list in production_dict.items():
        click.echo(non_terminal + ': ' + ', '.join([''.join(symbols) for symbols in symbols_list]))


def output_automaton(auto):
    """Output an automaton and its features"""
    click.secho('AUTOMATON', bold=True)
    click.echo(auto.to_string())
    click.echo('Alphabet: ' + ' '.join(auto.get_alphabet()))
    click.echo('States: ' + ' '.join(auto.get_states()))
    click.echo('End states: ' + ' '.join(auto.get_final_states()))

    transitions = auto.get_transitions()
    click.echo('Transitions:')
    for start_state, end_state, symbol in transitions:
        click.echo(start_state + ' -> ' + end_state + ': ' + symbol)


@click.command()
@click.option('--grammar/--automaton', default=True, help='Whether to input a grammar or automaton')
def transform(grammar):
    """Inputs either a grammar and outputs its automatom or the other way round"""
    stdin_text = click.get_text_stream('stdin')
    if grammar:
        # starts with grammar
        gramm = Grammar.from_string(stdin_text.read())
        output_grammar(gramm)
        if gramm.is_regular():
            click.echo('Grammar is regular')
            auto = gramm.to_automaton()
            output_automaton(auto)
        else:
            click.echo('Grammar is not regular')  # cannot transform to automata
    else:
        # starts with automaton
        auto = Automaton.from_string(stdin_text.read())
        output_automaton(auto)
        gramm = auto.to_grammar()
        output_grammar(gramm)


if __name__ == '__main__':
    transform()