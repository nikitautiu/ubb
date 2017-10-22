//
// Created by nikitautiu on 10/18/17.
//

#ifndef LAB2_OPERATIONS_H
#define LAB2_OPERATIONS_H

#include <vector>
#include <functional>
#include <thread>
#include <complex>

/*
 * Applies a binary operator element-wise on two
 * square 2d arrays. Returns the result.
 * Can be specified an operator other than std::plus
 * and a number of threads to do the operation on.
 */
template<typename T>
std::vector<std::vector<T>> mat_elemwise_op(const std::vector<std::vector<T>> &first,
                                            const std::vector<std::vector<T>> &second,
                                            std::size_t num_threads = 4,
                                            std::function<T(const T &, const T &)> bin_op = std::plus<T>()) {
    const auto n = first.size();
    const auto m = first[0].size();

    // declare the liniarized vectors
    auto lin_first = std::vector<T>(n * m);
    auto lin_second = std::vector<T>(n * m);
    auto lin_result = std::vector<T>(n * m);

    // liniarize the vectors
    for (auto i = 0; i < n; ++i)
        for (auto j = 0; j < m; ++j)
            lin_first[i * m + j] = first[i][j];
    for (auto i = 0; i < n; ++i)
        for (auto j = 0; j < m; ++j)
            lin_second[i * m + j] = second[i][j];

    // setup the threads
    auto thread_vect = std::vector<std::thread>();
    std::size_t current_pos = 0;
    for (auto i = 0; i < num_threads; ++i) {
        // find how many elems to distribute to this thread
        const std::size_t stop_pos = current_pos +
                                     (n * m) / num_threads +
                                     (int)(i < ((n * m) % num_threads));

        // spawn the threads
        thread_vect.push_back(std::thread([current_pos, stop_pos, bin_op, &lin_first, &lin_second, &lin_result] {
            for (auto i = current_pos; i < stop_pos; ++i)
                lin_result[i] = bin_op(lin_first[i], lin_second[i]);
        }));

        current_pos = stop_pos; // translate it right
    }

    // join em
    for (auto &th : thread_vect)
        th.join();

    // deliniarize
    auto result = std::vector<std::vector<T>>(n, std::vector<T>(m));
    for (auto i = 0; i < n; ++i)
        for (auto j = 0; j < m; ++j)
            result[i][j] = lin_result[i * m + j];

    return result;
}


template<typename T>
std::vector<std::vector<T>> mat_mult(const std::vector<std::vector<T>> &first,
                                     const std::vector<std::vector<T>> &second,
                                     std::size_t num_threads = 4) {
    const auto n = first.size();
    const auto m = first[0].size();
    const auto p = second[0].size();

    // declare the liniarized vectors
    // unlike in the case of addition, we are liniarizing
    // the second. column-wise(for caching improvement)
    auto lin_first = std::vector<T>(n * m);
    auto lin_second = std::vector<T>(m * p);
    auto lin_result = std::vector<T>(n * p);

    // liniarize the vectors
    for (auto i = 0; i < n; ++i)
        for (auto j = 0; j < m; ++j)
            lin_first[i * m + j] = first[i][j];
    for (auto i = 0; i < m; ++i)
        for (auto j = 0; j < p; ++j)
            lin_second[i + j * m] = second[i][j];

    // setup the threads
    auto thread_vect = std::vector<std::thread>();
    std::size_t current_pos = 0;
    for (auto i = 0; i < num_threads; ++i) {
        // find how many elems to distribute to this thread
        const std::size_t stop_pos = current_pos +
                                     (n * p) / num_threads +
                                     (i < ((n * p) % num_threads));

        // spawn the threads
        thread_vect.push_back(std::thread([=, &lin_first, &lin_second, &lin_result] {
            for (auto i = current_pos; i < stop_pos; ++i) {
                const std::size_t start_first = (i / p) * m; // the line * elements per line
                const std::size_t start_second = (i % p) * m;  // the column * elemnts per col
                lin_result[i] = (lin_first[i], lin_second[i]);

                // compute the linear combination
                for (int j = 0; j < m; ++j)
                    lin_result[i] += lin_first[start_first + j] * lin_second[start_second + j];
            }
        }));

        current_pos = stop_pos; // translate it right
    }

    // join em
    for (auto &th : thread_vect)
        th.join();

    // deliniarize
    auto result = std::vector<std::vector<T>>(n, std::vector<T>(p));
    for (auto i = 0; i < n; ++i)
        for (auto j = 0; j < p; ++j)
            result[i][j] = lin_result[i * p + j];

    return result;
}


template<typename T>
std::vector<std::vector<T>> mat_add(const std::vector<std::vector<T>> &first,
                                    const std::vector<std::vector<T>> &second,
                                    std::size_t num_threads = 4) {
    return mat_elemwise_op(first, second, num_threads);
}


template<typename T>
std::vector<std::vector<T>> mat_elemwise_mult(const std::vector<std::vector<T>> &first,
                                              const std::vector<std::vector<T>> &second,
                                              std::size_t num_threads = 4) {
    std::function<T(const T&, const T&)> op = [](const T& a, const T& b) -> T { return a * b; };
    return mat_elemwise_op(first, second, num_threads, op);
}


template<typename T>
std::vector<std::vector<T>> mat_dotcircle(const std::vector<std::vector<T>> &first,
                                          const std::vector<std::vector<T>> &second,
                                          std::size_t num_threads = 4) {
    std::function<T(const T&, const T&)> op = [](const T& a, const T& b) -> T { return 1. / (1. / a + 1. / b); };
    return mat_elemwise_op(first, second, num_threads, op);
}

template <>
std::vector<std::vector<std::complex<double>>> mat_dotcircle(const std::vector<std::vector<std::complex<double>>> &first,
                                          const std::vector<std::vector<std::complex<double>>> &second,
                                          std::size_t num_threads) {
    std::complex<double> one = 1.;
    std::function<std::complex<double>(const std::complex<double>&, const std::complex<double>&)> op =
            [one](const std::complex<double>& a, const std::complex<double>& b) -> std::complex<double>
            { return std::complex<double>(one / (one / a + one / b)); };
    return mat_elemwise_op(first, second, num_threads, op);
}

#endif //LAB2_OPERATIONS_H
