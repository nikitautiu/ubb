class FuzzyDescriptions(object):
    """Encapsulates a description of a fuzzy variable
    It contains a set of functions for each fuzzy region"""

    def __init__(self):
        self._regions = {}
        self._inverse = {}

    def add_region(self, var_name, membership_func, inverse=None):
        """Adds a region with a given membership function, optionally
        an inverse function for the Sugeno or Tsukamato models"""
        self._regions[var_name] = membership_func
        self._inverse[var_name] = inverse

    def fuzzify(self, value):
        """Returns the fuzzified values for each region"""
        return {name: membership_func(value)
                for name, membership_func in self._regions.items()}

    def defuzzify(self, var_name, value):
        return self._inverse[var_name](value)


def trap_region(a, b, c, d):
    """Returns a higher order func for a trapezoidal fuzzy region"""
    return lambda x: max(0, min((x - a) / (b - a), (d - x) / (d - c), 1))


def tri_region(a, b, c):
    """Returns a higher order func for a triangular fuzzy region"""
    return trap_region(a, b, b, c)


def inverse_line(a, b):
    return lambda val: val * (b - a) + a


def inverse_tri(a, b, c):
    return lambda val: (inverse_line(a, b)(val) + inverse_line(c, b)(val)) / 2


class FuzzyRule(object):
    """Define a conjunctive fuzzy rule
    X and Y and .. => Z"""

    def __init__(self, inputs, out):
        """Receives the set of inputs and expected output"""
        self._out_var = out  # the name of the output var
        self._inputs = inputs

    def evaluate(self, inputs):
        """Receives a dict of all the input vals and returns the conjuction
        of their values"""
        return [self._out_var, min([inputs[descr_name][var_name]
                                    for descr_name, var_name in
                                    self._inputs.items()])]


class FuzzySystem(object):
    """Fuzzy system object
    Receives variable descriptions, and rules, and outputs the defuzzified 
    result of the system.
    """

    def __init__(self):
        self._in_descriptions = {}
        self._out_description = None
        self._rules = []

    def add_description(self, name, descr, out=False):
        """Receives a description"""
        if out:
            if self._out_description is None:
                self._out_description = descr
            else:
                raise ValueError('System already has and output')
        else:
            self._in_descriptions[name] = descr

    def add_rule(self, inputs, output):
        self._rules.append(FuzzyRule(inputs, output))

    def _compute_descrs(self, inputs):
        return {
            var_name: self._in_descriptions[var_name].fuzzify(inputs[var_name])
            for var_name, val in inputs.items()}

    def _compute_rules_fuzzy(self, fuzzy_inputs):
        """Returns the fuzzy output of all rules"""
        return [rule.evaluate(fuzzy_inputs) for rule in self._rules
                if rule.evaluate(fuzzy_inputs)[1] != 0]

    def compute(self, inputs):
        fuzzy_vals = self._compute_descrs(inputs)
        rule_vals = self._compute_rules_fuzzy(fuzzy_vals)

        fuzzy_out_vars = [(list(descr[0].values())[0], descr[1]) for descr in
                          rule_vals]
        weighted_total = 0
        weight_sum = 0
        for var in fuzzy_out_vars:
            weight_sum += var[1]
            weighted_total += self._out_description.defuzzify(*var) * var[1]

        return weighted_total / weight_sum


def build_system():
    temperatura = FuzzyDescriptions()
    temperatura.add_region('frig', trap_region(-1000, -35, -20, 5))
    temperatura.add_region('rece', tri_region(-5, 0, 10))
    temperatura.add_region('normal', trap_region(5, 10, 15, 20))
    temperatura.add_region('cald', tri_region(15, 20, 25))
    temperatura.add_region('foarte cald', trap_region(24, 30, 50, 1000))

    umiditate = FuzzyDescriptions()
    umiditate.add_region('uscat', tri_region(-1000, 0, 50))
    umiditate.add_region('normal', tri_region(0, 50, 100))
    umiditate.add_region('umed', tri_region(50, 100, 1000))

    durata = FuzzyDescriptions()
    durata = FuzzyDescriptions()
    durata.add_region('scurta', tri_region(-1000, 0, 50), inverse_line(50, 0))
    durata.add_region('medie', tri_region(0, 50, 100), inverse_tri(0, 50, 100))
    durata.add_region('lunga', tri_region(50, 100, 1000), inverse_line(50, 100))

    system = FuzzySystem()
    system.add_description('temperatura', temperatura)
    system.add_description('umiditate', umiditate)
    system.add_description('durata', durata, out=True)

    system.add_rule({'temperatura': 'frig', 'umiditate': 'umed'},
                    {'durata': 'scurta'})
    system.add_rule({'temperatura': 'rece', 'umiditate': 'umed'},
                    {'durata': 'scurta'})
    system.add_rule({'temperatura': 'normal', 'umiditate': 'umed'},
                    {'durata': 'scurta'})
    system.add_rule({'temperatura': 'cald', 'umiditate': 'umed'},
                    {'durata': 'scurta'})
    system.add_rule({'temperatura': 'foarte cald', 'umiditate': 'umed'},
                    {'durata': 'medie'})

    system.add_rule({'temperatura': 'frig', 'umiditate': 'normal'},
                    {'durata': 'scurta'})
    system.add_rule({'temperatura': 'rece', 'umiditate': 'normal'},
                    {'durata': 'medie'})
    system.add_rule({'temperatura': 'normal', 'umiditate': 'normal'},
                    {'durata': 'medie'})
    system.add_rule({'temperatura': 'cald', 'umiditate': 'normal'},
                    {'durata': 'medie'})
    system.add_rule({'temperatura': 'foarte cald', 'umiditate': 'normal'},
                    {'durata': 'lunga'})

    system.add_rule({'temperatura': 'frig', 'umiditate': 'uscat'},
                    {'durata': 'medie'})
    system.add_rule({'temperatura': 'rece', 'umiditate': 'uscat'},
                    {'durata': 'lunga'})
    system.add_rule({'temperatura': 'normal', 'umiditate': 'uscat'},
                    {'durata': 'lunga'})
    system.add_rule({'temperatura': 'cald', 'umiditate': 'uscat'},
                    {'durata': 'lunga'})
    system.add_rule({'temperatura': 'foarte cald', 'umiditate': 'uscat'},
                    {'durata': 'lunga'})

    return system


def test_fuzzy():
    system = build_system()
    assert system.compute({'umiditate': 65, 'temperatura': 17}) == 44.375
    assert system.compute({'umiditate': 65, 'temperatura': 35}) == 74.5
    assert system.compute(
        {'umiditate': 10, 'temperatura': 17}) == 68.57142857142857
    assert system.compute({'umiditate': 10, 'temperatura': 30}) == 84
    assert system.compute({'umiditate': 75, 'temperatura': 20}) == 37.5
