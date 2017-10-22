#include <iostream>
#include <vector>
#include "operations.h"
#include "utils.h"


int main() {
    std::cout << "Hello, World!" << std::endl;
    std::vector<std::vector<int>> vect_a = {{1, 2, 3}, {2, 0, 1}, {5, 1, 2}};
    std::vector<std::vector<int>> vect_b = {{0, -9, 1}, {10, 2, 1}, {5, 6, 0}};
    std::vector<std::vector<int>> vect_c = {{0, -1}, {1, 2}, {2, 0}};

    // compute the sum and product
    auto vect_sum = mat_add(vect_a, vect_b);
    auto vect_prod = mat_mult(vect_a, vect_c);

    print_matrix(vect_sum);
    std::cout << '\n';

    print_matrix(vect_prod);
    std::cout << '\n';

    std::cout << "INTEGER\n";
    std::cout << "ADDITION\n";
    std::cout << "1000 x 1000 - 1 thread - " << benchmark_mat_op<int>(1000, 1000, 1, mat_add<int>) << '\n';
    std::cout << "1000 x 1000 - 2 thread - " << benchmark_mat_op<int>(1000, 1000, 2, mat_add<int>) << '\n';
    std::cout << "1000 x 1000 - 4 thread - " << benchmark_mat_op<int>(1000, 1000, 4, mat_add<int>) << '\n';
    std::cout << "1000 x 1000 - 6 thread - " << benchmark_mat_op<int>(1000, 1000, 6, mat_add<int>) << '\n';
    std::cout << "1000 x 1000 - 8 thread - " << benchmark_mat_op<int>(1000, 1000, 8, mat_add<int>) << '\n';

    std::cout << "\nMULTIPLICATION\n";
    std::cout << "1000 x 1000 - 1 thread - " << benchmark_mat_op<int>(1000, 1000, 1, mat_mult<int>) << '\n';
    std::cout << "1000 x 1000 - 2 thread - " << benchmark_mat_op<int>(1000, 1000, 2, mat_mult<int>) << '\n';
    std::cout << "1000 x 1000 - 4 thread - " << benchmark_mat_op<int>(1000, 1000, 4, mat_mult<int>) << '\n';
    std::cout << "1000 x 1000 - 6 thread - " << benchmark_mat_op<int>(1000, 1000, 6, mat_mult<int>) << '\n';
    std::cout << "1000 x 1000 - 8 thread - " << benchmark_mat_op<int>(1000, 1000, 8, mat_mult<int>) << '\n';

    // now test for complex numbers as well
    std::cout << "COMPLEX\n";
    std::cout << "ADDITION\n";
    std::cout << "1000 x 1000 - 1 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 1, mat_add<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 2 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 2, mat_add<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 4 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 4, mat_add<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 6 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 6, mat_add<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 8 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 8, mat_add<std::complex<double>>) << '\n';

    std::cout << "\nMULTIPLICATION\n";
    std::cout << "1000 x 1000 - 1 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 1, mat_mult<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 2 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 2, mat_mult<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 4 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 4, mat_mult<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 6 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 6, mat_mult<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 8 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 8, mat_mult<std::complex<double>>) << '\n';

    // elemntwise multiplication
    std::cout << "\nELEMENTWISE MULTIPLICATION\n";
    std::cout << "INTEGER\n";
    std::cout << "1000 x 1000 - 1 thread - " << benchmark_mat_op<int>(1000, 1000, 1, mat_elemwise_mult<int>) << '\n';
    std::cout << "1000 x 1000 - 2 thread - " << benchmark_mat_op<int>(1000, 1000, 2, mat_elemwise_mult<int>) << '\n';
    std::cout << "1000 x 1000 - 4 thread - " << benchmark_mat_op<int>(1000, 1000, 4, mat_elemwise_mult<int>) << '\n';
    std::cout << "1000 x 1000 - 6 thread - " << benchmark_mat_op<int>(1000, 1000, 6, mat_elemwise_mult<int>) << '\n';
    std::cout << "1000 x 1000 - 8 thread - " << benchmark_mat_op<int>(1000, 1000, 8, mat_elemwise_mult<int>) << '\n';

    std::cout << "\nCOMPLEX\n";
    std::cout << "1000 x 1000 - 1 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 1, mat_elemwise_mult<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 2 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 2, mat_elemwise_mult<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 4 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 4, mat_elemwise_mult<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 6 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 6, mat_elemwise_mult<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 8 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 8, mat_elemwise_mult<std::complex<double>>) << '\n';

    // dot-circle operator
    std::cout << "\nODOT\n";
    std::cout << "INTEGER\n";
    std::cout << "1000 x 1000 - 1 thread - " << benchmark_mat_op<float>(1000, 1000, 1, mat_dotcircle<float>) << '\n';
    std::cout << "1000 x 1000 - 2 thread - " << benchmark_mat_op<float>(1000, 1000, 2, mat_dotcircle<float>) << '\n';
    std::cout << "1000 x 1000 - 4 thread - " << benchmark_mat_op<float>(1000, 1000, 4, mat_dotcircle<float>) << '\n';
    std::cout << "1000 x 1000 - 6 thread - " << benchmark_mat_op<float>(1000, 1000, 6, mat_dotcircle<float>) << '\n';
    std::cout << "1000 x 1000 - 8 thread - " << benchmark_mat_op<float>(1000, 1000, 8, mat_dotcircle<float>) << '\n';

    std::cout << "\nCOMPLEX\n";
    std::cout << "1000 x 1000 - 1 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 1, mat_dotcircle<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 2 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 2, mat_dotcircle<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 4 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 4, mat_dotcircle<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 6 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 6, mat_dotcircle<std::complex<double>>) << '\n';
    std::cout << "1000 x 1000 - 8 thread - " << benchmark_mat_op<std::complex<double>>(1000, 1000, 8, mat_dotcircle<std::complex<double>>) << '\n';
}
