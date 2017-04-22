import itertools

import numpy as np

from genetic import GeneticAlgorithm, uniform_crossover, uniform_mutation
from pso import PSO, compute_binary_discrete_velocities, discrete_binary_move
from utils import to_bit_mask


def get_jewelry_fitness(max_weight, weights, prices, penalty_coef, params):
    """Given a configuration of the problem return the fitness of a 
    encoding. Where the params ar e an int-encoded bool array of what jewelry to
    take."""
    mask = to_bit_mask(params, width=len(weights))
    total_weight = weights[mask].sum()
    total_price = prices[mask].sum()
    if total_weight <= max_weight:
        return total_price
    return max(
        total_price - abs(max_weight - total_weight) * penalty_coef, 0)


def generate_configurations(size, weights):
    """Generate uniformly distributed, bit configurations for evolutionary
    algorithms."""
    return list(np.random.choice(1 << len(weights), size))


class JewelryGenetic(GeneticAlgorithm):
    def __init__(self, max_weight, items, penalty_coef=1000,
                 **kwargs):
        """Receives exactly the same arguments as genetic algorithm
        plus the max weight allowed and the weights of each object.
        `exceed_penalty_coef` represents how penalised is a invidual who
        exceed the total weight"""
        super(JewelryGenetic, self).__init__(**kwargs)
        self.penalty_coef = penalty_coef
        self.max_weight = max_weight

        # preprocess
        self.weights = np.array([item[0] for item in items])
        self.prices = np.array([item[1] for item in items])

    def get_fitness(self, chromosome):
        return get_jewelry_fitness(self.max_weight, self.weights, self.prices,
                                   self.penalty_coef, chromosome)

    def generate_chromosomes(self):
        """Generates random uniformly distributed chromosomes"""
        return generate_configurations(self.population_size, self.weights)

    def get_offspring_chromosome(self, parent1_chrom, parent2_chrom):
        """Uniform crossover algorithm"""
        return uniform_crossover(parent1_chrom, parent2_chrom,
                                 len(self.weights))

    def get_mutated_chromosome(self, chromosome):
        mutation_rate = self.mutation_rate
        size = len(self.weights)
        return uniform_mutation(chromosome, mutation_rate, size)


class JewelryPSO(PSO):
    def __init__(self, max_weight, items, penalty_coef=1000, max_velocity=6,
                 inertia=0.4, cognitive_coef=2, swarm_coef=2, nbpso=True,
                 **kwargs):
        """Receives exactly the same arguments as genetic algorithm
        plus the max weight allowed and the weights of each object.
        `exceed_penalty_coef` represents how penalised is a invidual who
        exceed the total weight.
        The nbpso argument tells whether to use the NBPSO algorithm.
        Check discrete_binary_move for more info."""

        # this being a binary pso, the parmeters need to be adjusted
        super(JewelryPSO, self).__init__(max_velocity=max_velocity,
                                         inertia=inertia,
                                         cognitive_coef=cognitive_coef,
                                         swarm_coef=swarm_coef,
                                         **kwargs)
        self.nbpso = nbpso
        self.penalty_coef = penalty_coef
        self.max_weight = max_weight

        # preprocess
        self.weights = np.array([item[0] for item in items])
        self.prices = np.array([item[1] for item in items])

    def get_fitness(self, params):
        return get_jewelry_fitness(self.max_weight, self.weights, self.prices,
                                   self.penalty_coef, params)

    def generate_params(self):
        return generate_configurations(self.swarm_size, self.weights)

    def compute_velocities(self, swarm, global_best_params):
        # velocity matrix, if it was None, it means that it's the first and
        # should be initialized correspondingly
        return compute_binary_discrete_velocities(swarm,
                                                  global_best_params,
                                                  len(self.weights),
                                                  self.inertia,
                                                  self.cognitive_coef,
                                                  self.swarm_coef,
                                                  self.max_velocity)

    def move_params(self, params, velocities):
        return discrete_binary_move(params, velocities, nbpso=self.nbpso)


def solve_jewelry_pso(max_weight, items, iterations=100, *args, **kwargs):
    """Receives the max_weight items,  the same additional
    parameters as the PSO class and, optionally, a number of iterations.
    Returns a tuple of (total_price, mask of items taken)."""
    pso_class = JewelryPSO(max_weight, items, *args, **kwargs)
    chained_generations = itertools.chain(
        *pso_class.run(iterations=iterations))
    best_particle = max(chained_generations, key=lambda part: part.fitness)

    return (best_particle.fitness, to_bit_mask(best_particle.params,
                                               width=len(items)))


def solve_jewelry(max_weight, items, algorithm, iterations=100, *args,
                  **kwargs):
    """Receives the max_weight items,  the same additional
    parameters as the Genetic class and, optionally, a number of generations.
    Returns a tuple of (total_price, mask of items taken).
    The type of algorithm can be specified via the algorithm param"""
    assert algorithm in ('pso', 'genetic')
    if algorithm == 'genetic':
        solver_obj = JewelryGenetic(max_weight, items, *args, **kwargs)
    else:
        solver_obj = JewelryPSO(max_weight, items, *args, **kwargs)

    chained_results = itertools.chain(
        *solver_obj.run(iterations=iterations))
    best_individual = max(chained_results,
                          key=lambda x: x[0])  # max by fitness

    mask = to_bit_mask(best_individual[1], width=len(items))
    prices = np.array([item[1] for item in items])
    return prices[mask].sum(), mask
