import collections

import numpy as np

from utils import to_bit_mask, from_bit_mask

Particle = collections.namedtuple('Particle', ['params', 'fitness',
                                               'best_params', 'best_fitness',
                                               'velocity'])


class PSO(object):
    def __init__(self, swarm_size=200, inertia=2, cognitive_coef=2,
                 swarm_coef=2, max_velocity=10):
        self.max_velocity = max_velocity
        self.swarm_coef = swarm_coef
        self.cognitive_coef = cognitive_coef
        self.inertia = inertia
        self.swarm_size = swarm_size

    def run_swarm(self, iterations=100):
        param_list = self.generate_params()
        swarm = []
        for params in param_list:
            # initialize de swarm
            # the initial velocity for each component should be 0 initially
            # here it is None so it can be initialized elsewhere
            fitness = self.get_fitness(params)
            swarm.append(Particle(params, fitness, params, fitness, None))
        global_best = max(swarm, key=lambda x: x.fitness)

        yield swarm
        for _ in range(iterations):
            swarm = self.move_swarm(swarm, global_best.params)
            global_best = max(swarm + [global_best], key=lambda x: x.fitness)
            yield swarm

    def run(self, iterations=100):
        """Wrapper around the run_swarm method that returns
        the results in the same format as genetic's run."""
        for iteration in self.run_swarm(iterations):
            yield [(particle.fitness, particle.params) for particle in
                   iteration]

    def move_swarm(self, swarm, global_best):
        """Get the population and transform it according to the swarm type
        :rtype: list(Particle)
        """
        # compute the velocities

        new_velocities = self.compute_velocities(swarm,
                                                 global_best)
        all_params = [particle.params for particle in
                      swarm]  # get the canonical params
        new_params_list = self.move_params(all_params, new_velocities)

        new_swarm = []
        for new_params, old_particle, new_velocity in \
                zip(new_params_list, swarm, new_velocities):

            new_fitness = self.get_fitness(new_params)
            if new_fitness > old_particle.best_fitness:
                # the fitness was updated
                new_swarm.append(Particle(new_params, new_fitness,
                                          new_params, new_fitness,
                                          new_velocity))
            else:
                # the best fitness remains the same
                new_particle = old_particle._replace(params=new_params,
                                                     fitness=new_fitness,
                                                     velocity=new_velocity)
                new_swarm.append(new_particle)

        return new_swarm

    def compute_velocities(self, population, global_best_params):
        """Receives the set of particles, the global best
        and returns the updated velocities."""
        raise NotImplementedError

    def move_params(self, params, velocities):
        """Does the required transformations of the params
        and returns the new positions after having moved with the
        given velocities"""
        raise NotImplementedError

    def get_fitness(self, params):
        """Gets the fitness of a chromosome/param"""
        raise NotImplementedError

    def generate_params(self):
        """Generates the swarm population randomly"""
        raise NotImplementedError


def discrete_binary_move(params, velocities, nbpso=False):
    """Moves the params according to the velocities
    The velocities are expected to be a numpy matrix
    where each row is the velocities for one particle
    
    The nbpso argument tells whether to use the algorithm
    described by Hossein Nezamabadi in his paper."""
    sigm = 1. / (1. + np.exp(-velocities))  # sigmoid of velocities
    if not nbpso:
        rand_mat = np.random.rand(*velocities.shape)
        bit_mask = rand_mat < sigm  # get the bits of the new values
        return np.array([from_bit_mask(mask) for mask in bit_mask])

    # nbpso - the threshold function is centered in 0
    thresh_matrix = 2.0 * np.abs(sigm - 0.5)
    rand_mat = np.random.rand(*velocities.shape)
    flip_masks = rand_mat < thresh_matrix  # get the bits of the new values
    return params ^ np.array([from_bit_mask(mask) for mask in flip_masks])


def compute_binary_discrete_velocities(swarm, global_best_params,
                                       params_width, inertia,
                                       cognitive_coef, swarm_coef,
                                       max_velocity):
    """Returns a numpy matrix of velocities.
    Expects an iterable of particles which is the swarm"""
    velocity_matrix = np.array([p.velocity if p.velocity is not None
                                else ([0.] * params_width)
                                for p in swarm])
    # transform the features from binary encoded to a flat matrix
    param_array_list = [to_bit_mask(p.params, width=params_width) * 1.
                        for p in swarm]
    param_matrix = np.array(param_array_list)
    # same goes for best local features
    best_param_array_list = [
        to_bit_mask(p.best_params, width=params_width) * 1.
        for p in swarm]
    best_param_matrix = np.array(best_param_array_list)
    # and the global best
    global_best_matrix = to_bit_mask(global_best_params,
                                     width=params_width) * 1.
    # compute the diff between each elem and it's known best
    local_diff = best_param_matrix - param_matrix
    global_diff = global_best_matrix - param_matrix
    new_velocities = (velocity_matrix * inertia +
                      cognitive_coef * np.random.rand(
                          *velocity_matrix.shape) * local_diff +
                      swarm_coef * np.random.rand(
                          *velocity_matrix.shape) * global_diff)
    # restrict the velocities to the [-vmax, +vmax] interval
    # do it with minimum and maximum, they work with broadcasting
    return np.maximum(np.minimum(new_velocities, max_velocity),
                      -max_velocity)
