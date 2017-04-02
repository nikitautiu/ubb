import random
import heapq
import numpy as np


def get_selected(population):
    """Implements a basic fitness proportionate selection"""
    fitnesses = np.array([ind[0] for ind in population])
    pop = np.array([ind[1] for ind in population])
    probs = fitnesses.astype(float) / fitnesses.sum()
    while True:
        yield np.random.choice(pop, p=probs)


class GeneticAlgorithm(object):
    def __init__(self, population_size=200, elite_size=1,
                 crossover_prob=0.7, mutation_prob=0.01):
        self.mutation_prob = mutation_prob
        self.crossover_prob = crossover_prob
        self.elite_size = elite_size
        self.population_size = population_size

    def evolve(self, max_generations):
        """Evolves the population for the given number of generations"""
        individuals = self.generate_chromosomes()
        population = [(self.get_fitness(ind), ind) for ind in individuals]
        for _ in range(max_generations):
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


class JewelryGenetic(GeneticAlgorithm):
    def __init__(self, max_weight, items, mutation_rate=0.1, **kwargs):
        """Receives exactly the same arguments as genetic algorithm
        plus the max weight allowed and the weights of each object.
        `exceed_penalty_coef` represents how penalised is a invidual who
        exceed the total weight"""
        super(JewelryGenetic, self).__init__( **kwargs)
        self.mutation_rate = mutation_rate
        self.items = items
        self.nr_items = len(items)
        self.max_weight = max_weight

        # preprocess
        self.weights = np.array([item[0] for item in items])
        self.prices = np.array([item[1] for item in items])

    def get_fitness(self, chromosome):
        mask = np.array(list(np.binary_repr(chromosome, width=self.nr_items))) == '1'
        total_weight = self.weights[mask].sum()
        total_price = self.prices[mask].sum()

        if total_weight <= self.max_weight:
            return total_price
        return max(total_price - abs(self.max_weight - total_weight) * 4, 0)

    def generate_chromosomes(self):
        """Generates random uniformly distributed chromosomes"""
        return list(np.random.choice(1 << self.nr_items, self.population_size))

    def get_offspring_chromosome(self, parent1_chrom, parent2_chrom):
        """Uniform crossover algorithm"""
        mask = random.randint(1, 2 ** self.nr_items - 1)
        return (parent1_chrom & mask) | (parent2_chrom & (~mask))

    def get_mutated_chromosome(self, chromosome):
        mask = np.random.choice(2, self.nr_items, p=[1 - self.mutation_rate,
                                                     self.mutation_rate])
        int_mask = 0
        for elem in mask:
            int_mask = int_mask << 1 | elem
        return int_mask ^ chromosome
