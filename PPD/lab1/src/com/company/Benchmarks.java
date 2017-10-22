package com.company;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class Benchmarks {
    /**
     * Prints the execution time of the sum of 2 matrices of n x m
     * elements on numThreads threads.
     */
    static<T> void benchmarkSum(int n, int m, int numThreads, BinaryOperator<T> op, MatrixFactory<T> fact) {
        ArrayList<ArrayList<T>> a = fact.randomMatrix(n, m), b = fact.randomMatrix(n, m);

        // add em up
        final long startTime = System.nanoTime();
        MatrixAdder.add(a, b, numThreads, op);
        final long duration = System.nanoTime() - startTime;

        // print results
        System.out.println(n + " x " + m + " - "+ numThreads + " - " + duration / 1000000.);
    }

    static void benchmarkMult(int n, int m, int numThreads) {
        ArrayList<ArrayList<Integer>> a1 = Utils.randomIntMatrix(n, m), b1 = Utils.randomIntMatrix(n, m);

        // workaround to translate to array
        int a[][] = new int[n][m], b[][] = new int[n][m];
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < m; ++j) {
                a[i][j] = a1.get(i).get(j);
                b[i][j] = b1.get(i).get(j);
            }

        // add em up
        final long startTime = System.nanoTime();
        MatrixMultiplier.mult(a, b, numThreads);
        final long duration = System.nanoTime() - startTime;

        // print results
        System.out.println(n + " x " + m + " - "+ numThreads + " - " + duration / 1000000.);
    }

    /**
     * Runs the predetermined benchmarks for the given binary operator
     * and the given matrix factory
     */
    static<T> void runSumBenchmarks(BinaryOperator<T> sumOp, MatrixFactory<T> fact) {
        benchmarkSum(1000, 1000, 1, sumOp, fact);
        benchmarkSum(1000, 1000, 2, sumOp, fact);
        benchmarkSum(1000, 1000, 4, sumOp, fact);
        benchmarkSum(1000, 1000, 6, sumOp, fact);
        benchmarkSum(1000, 1000, 8, sumOp, fact);
    }

    /**
     * Runs a predefined set of benchamrks for multiplciation on random
     * matrices.
     */
    static<T> void runMultBenchmark() {
        benchmarkMult(1000, 1000, 1);
        benchmarkMult(1000, 1000, 2);
        benchmarkMult(1000, 1000, 4);
        benchmarkMult(1000, 1000, 6);
        benchmarkMult(1000, 1000, 8);

    }
}
