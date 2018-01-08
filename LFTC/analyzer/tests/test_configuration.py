from unittest import TestCase

from parser.grammar import Production
from parser.parser import Configuration


class TestConfiguration(TestCase):
    def test_configuration(self):
        prod = Production('start', ['A', '"a"', 'b'])
        conf1 = Configuration.from_production(prod)
        conf2 = Configuration('start', ['A', '"a"', 'b'])

        self.assertEqual(conf1, conf2)

        self.assertIn(conf1, {conf2, Configuration("aaa", [])})
        self.assertNotIn(conf1, {Configuration("aaa", [])})