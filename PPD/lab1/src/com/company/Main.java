package com.company;

public class Main {

    private static void printMatrix(int mat[][]) {
        final int n = mat.length;
        final int m = mat[0].length;

        for(int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j)
                System.out.print(mat[i][j] + " ");
            System.out.print("\n");
        }

        System.out.print("\n");
    }

    /**
     * Returns a random n x m matrix
     */
    private static int[][] randomMatrix(int n, int m) {
        int matrix[][] = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j< m; j++) {
                matrix[i][j] = (int) (Math.random()*10); // geting numbres between 1 and 10
            }
        }

        return matrix;
    }

    /**
     * Prints the execution time of the sum of 2 matrices of n x m
     * elements on numThreads threads.
     */
    private static void benchmarkSum(int n, int m, int numThreads) {
        int a[][] = Main.randomMatrix(n, m), b[][] = Main.randomMatrix(n, m);

        // add em up
        final long startTime = System.nanoTime();
        MatrixAdder.add(a, b, numThreads);
        final long duration = System.nanoTime() - startTime;

        // print results
        System.out.println("Added 2 " + n + " by " + m + " matrices on " + numThreads + " threads in " +
                           duration / 1000000. + " miliseconds");
    }

    public static void main(String[] args) {
        int a[][] = {{1, 2, 3}, {2, 1, 1}, {0, 3, 1}},
            b[][] = {{-1, 2,1}, {1, 1, 1}, {0, -2, 3}};
        int c[][] = MatrixAdder.add(a, b, 2);

        // small sanity-check
        System.out.println("Summing");
        Main.printMatrix(a);
        Main.printMatrix(b);
        Main.printMatrix(c);

        // bechmarks
        Main.benchmarkSum(10, 10, 1);
        Main.benchmarkSum(10, 10, 2);
        Main.benchmarkSum(10, 10, 4);
        Main.benchmarkSum(10, 10, 8);

        Main.benchmarkSum(1000, 1000, 1);
        Main.benchmarkSum(1000, 1000, 2);
        Main.benchmarkSum(1000, 1000, 4);
        Main.benchmarkSum(1000, 1000, 8);

    }
}
