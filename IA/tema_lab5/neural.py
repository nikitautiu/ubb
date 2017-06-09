import numpy as np

def sigm(x):
    return 1. / (1. + np.exp(np.clip(-x, -500, 500)))


def sigm_deriv(x):
    x = np.clip(x, -500, 500)
    return sigm(x) / (1 - sigm(x))


class NeuralNet(object):
    def __init__(self, hidden_layers_size=[20], alpha=0.01,
                 activation_func=sigm, deriv_func=sigm_deriv):
        self.alpha = alpha
        self.hidden_layers_size = hidden_layers_size
        self.activation_func = activation_func
        self.deriv_func = deriv_func

    def init_weights(self, input_size, output_size):
        """Initializes the thetas uniformly from the interval (-0.5, 0.5)"""
        layer_sizes = [input_size] + self.hidden_layers_size + [output_size]
        # also add thetas for the bias
        self.thetas = [np.random.rand(num_out, 1 + num_in) - 1
                       for num_out, num_in in
                       zip(layer_sizes[1:], layer_sizes[:-1])]

    def feed_forward(self, X):
        """Feed-forward generator of the values on each layer"""
        yield X, X  # return them without the bias
        values = self.bias_values(X)

        for theta in self.thetas:
            # standard feed-forward, linear combination is fed
            # to the transfer function
            activ = values.dot(theta.T)
            vals = self.activation_func(activ)

            # yield both the activation and post-transfer value
            yield vals, activ  # same, without bias
            values = self.bias_values(vals[:])

    def back_prop(self, values, activations, expected):
        """Generates the gradient through back-propagation"""
        deltas = []
        error = values[-1] - expected
        deltas.append(error)

        # exclude the input, as there is no error there
        for activation_vals, theta in zip(activations[-2:0:-1], self.thetas[:0:-1]):
            activ_grad = self.deriv_func(activation_vals)
            deltas.append(activ_grad * deltas[-1].dot(theta)[:, 1:])  # no bias

        # the deltas are in reverse
        for delta, vals in zip(deltas[::-1], values[:-1]):
            yield delta.T.dot(self.bias_values(vals))  # pad with the ones

    def bias_values(self, activation_vals):
        return np.concatenate(
            (np.ones((activation_vals.shape[0], 1)), activation_vals), axis=1)

    def train(self, X, Y, num_classes, epochs=100):
        self.init_weights(X.shape[1], num_classes)  # randomly initialize the ewights

        # one hot encoding
        # must use ints for indexing
        expected_values = np.zeros((Y.shape[0], num_classes))
        expected_values[np.arange(expected_values.shape[0]),
                        Y.ravel().astype(int) - 1] = 1

        for _ in range(epochs):
            # separate the activations and values
            results = list(map(list, zip(*list(self.feed_forward(X)))))
            values, activations = results[0], results[1]

            grads = list(self.back_prop(values, activations, expected_values))

            self.thetas = [theta - self.alpha * grad
                           for theta, grad in zip(self.thetas, grads)]
            # yield the cost at each step
            cost = Y * np.log(values[-1]) + (1 - values[-1]) * np.log(1 - values[-1])
            yield -(1/X.shape[0]) * cost.ravel().sum()

    def predict(self, input):
        for layer in self.feed_forward(input): pass
        return layer[0]