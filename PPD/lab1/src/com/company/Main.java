package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args) {
//        ArrayList<ArrayList<Integer>> a = {{1, 2, 3}, {2, 1, 1}, {0, 3, 1}},
//            b[][] = {{-1, 2,1}, {1, 1, 1}, {0, -2, 3}};
        int[][] a2 = {{1, 2, 3}, {2, 1, 1}},
                b2 = {{1, 2},
                        {3, 1},
                        {0, 0}};

        // initialize the arrays
        ArrayList<ArrayList<Integer>> a = new ArrayList<>(), b = new ArrayList<>();
//        ArrayList<ArrayList<Integer>> a2 = new ArrayList<>(), b2 = new ArrayList<>();

        a.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
        a.add(new ArrayList<>(Arrays.asList(2, 1, 1)));
        a.add(new ArrayList<>(Arrays.asList(0, 3, 1)));

        b.add(new ArrayList<>(Arrays.asList(-1, 2, 1)));
        b.add(new ArrayList<>(Arrays.asList(1, 1, 1)));
        b.add(new ArrayList<>(Arrays.asList(0, -2, 3)));


        // declare the sum operator
        BinaryOperator<Integer> sumOp = (Integer x, Integer y) -> x + y;
        ArrayList<ArrayList<Integer>> c = MatrixAdder.add(a, b, 2, sumOp);
        int d[][] = MatrixMultiplier.mult(a2, b2, 2);

        // small sanity-check
        Utils.printMatrix(a);
        Utils.printMatrix(b);
        Utils.printMatrix(c);
//        Main.printMatrix(d);

        // bechmarks
        // integer
        System.out.println("\nINTEGER ADDITION");
        Benchmarks.runSumBenchmarks(sumOp, Utils::randomIntMatrix);

        System.out.println("\nINTEGER MULTIPLICATION");
        Benchmarks.runMultBenchmark();

        System.out.println("\nINTEGER ELEM-WISE MULTIPLICATION");
        Benchmarks.runSumBenchmarks((Integer x, Integer y) -> x * y, Utils::randomIntMatrix);

        System.out.println("\nINTEGER DOTCIRCLE");
        Benchmarks.runSumBenchmarks((Integer x, Integer y) -> 1*(1/x + 1/y), Utils::randomIntMatrix);

        // complex
        System.out.println("\nCOMPLEX ELEM-WISE MULTIPLICATION");
        Benchmarks.runSumBenchmarks((Complex x, Complex y) -> x.mult(y), Utils::randomComplexMatrix);

        System.out.println("\nCOMPLEX DOTCIRCLE");
        Benchmarks.runSumBenchmarks((Complex x, Complex y) -> x.inv().add(y.inv()).inv(), Utils::randomComplexMatrix);
    }

}
