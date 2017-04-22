import heapq
import random

import numpy as np


def get_selected(population):
    """Implements a basic fitness-proportionate selection"""
    fitnesses = np.array([ind[0] for ind in population])
    pop = np.array([ind[1] for ind in population])
    probs = fitnesses.astype(float) / fitnesses.sum()
    while True:
        yield np.random.choice(pop, p=probs)


class GeneticAlgorithm(object):
    def __init__(self, population_size=200, elite_size=1,
                 crossover_prob=0.7, mutation_prob=0.01, mutation_rate=0.5):
        self.mutation_rate = mutation_rate
        self.mutation_prob = mutation_prob
        self.crossover_prob = crossover_prob
        self.elite_size = elite_size
        self.population_size = population_size

    def run(self, iterations=100):
        """Evolves the population for the given number of generations"""
        individuals = self.generate_chromosomes()
        population = [(self.get_fitness(ind), ind) for ind in individuals]
        for _ in range(iterations):
            yield population
            # initialize with the elite
            new_population = self.get_elite(population)
            selected = get_selected(population)
            while len(new_population) < self.population_size:
                # get the two parents
                parent1 = next(selected)
                parent2 = next(selected)
                # do the crossover if the probability check passses
                if random.random() <= self.crossover_prob:
                    offspring = self.get_offspring_chromosome(parent1, parent2)
                else:
                    offspring = random.choice([parent1, parent2])
                # mutate if necessary
                if random.random() <= self.mutation_prob:
                    offspring = self.get_mutated_chromosome(offspring)
                new_population.append((self.get_fitness(offspring), offspring))
            population = new_population

    def get_elite(self, population):
        """Returns the best individuals of last generation"""
        if self.elite_size == 0:
            return []
        # partial sort using heapq
        return heapq.nlargest(self.elite_size, population,
                              key=lambda individual: individual[0])

    def generate_chromosomes(self):
        raise NotImplementedError

    def get_fitness(self, chromosome):
        raise NotImplementedError

    def get_offspring_chromosome(self, parent1, parent2):
        raise NotImplementedError

    def get_mutated_chromosome(self, chromosome):
        raise NotImplementedError


def uniform_crossover(parent1_chrom, parent2_chrom, size):
    mask = random.randint(1, 2 ** size - 1)
    new_chrom = (parent1_chrom & mask) | (parent2_chrom & (~mask))
    return new_chrom


def uniform_mutation(chromosome, mutation_rate, size):
    mask = np.random.choice(2, size, p=[1 - mutation_rate,
                                        mutation_rate])
    int_mask = 0
    for elem in mask:
        int_mask = int_mask << 1 | elem
    return int_mask ^ chromosome
