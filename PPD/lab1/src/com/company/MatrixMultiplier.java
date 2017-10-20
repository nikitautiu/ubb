package com.company;

import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplier {
    /**
     * Prallelized matrix multiplication
     * @param first the first matrix of the multiplication
     * @param second the second matrix of the multiplication
     * @param numThreads the number of threads to parallelize the multiplication to
     * @return the result of the multiplication
     */
    public static int[][] mult(int first[][], int second[][], int numThreads) {
        final int n = first.length;
        final int m = first[0].length;
        final int p = second[0].length;

        int contigFirst[] = new int[n * m];
        int contigSecond[] = new int[m * p];
        int contigResult[] = new int[n * p];

        int result[][] = new int[n][p];

        // liniarize the arrays
        // to take advantage of the cache
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < m; ++j)
                contigFirst[i * m + j] = first[i][j];
        for(int i = 0; i < m; ++i)
            for(int j = 0; j < p; ++j)
                contigSecond[i + j * m] = second[i][j];

        // make the distribution
        // compute per line each
        int numElems[] = new int[numThreads]; // the array with distribution of elems for each thread
        for(int i = 0; i < numThreads; ++i) {
            numElems[i] = (int)((n*p) / numThreads);  // distribute them evenly
            if(i < (n*p) % numThreads)
                numElems[i]++;  // add te remaining ones
        }

        int currentElem = 0;
        List<Thread> threads = new ArrayList<>(numThreads);
        for(int i = 0; i < numThreads; ++i) {
            final int startElem = currentElem;
            final int stopElem = startElem + numElems[i];

            currentElem = stopElem; // the start position of the next partition

            // spawn the threads
            Thread thread = new Thread(() -> {
                for(int j = startElem; j < stopElem; ++j) {
                    final int startFirst = (j / p) * m; // the line * elements per line
                    final int startSecond = (j % p) * m;  // the column * elemnts per col

                    // sum it up
                    contigResult[j] = 0;
                    for(int k = 0; k < m; ++k)
                        contigResult[j] += contigFirst[startFirst + k] * contigSecond[startSecond + k];

                }
            });
            thread.start();
            threads.add(thread);
        }

        // join the threads
        for(Thread th : threads)
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        // deliniarize
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < p; ++j) {
                result[i][j] = contigResult[i * n + j];
            }

        return result;
    }
}
