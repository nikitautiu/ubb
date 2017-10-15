package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by archie on 10/9/2017.
 */
public class MatrixAdder {
    /**
     * Prallelized matrix sum
     * @param first the first matrix of the sum
     * @param second the second matrix of the sum
     * @param numThreads the number of threads to parallelize the sum to
     * @return the result of the sum
     */
    public static int[][] add (int first[][], int second[][], int numThreads) {
        final int n = first.length;
        final int m = first[0].length;
        int contigFirst[] = new int[n * m];
        int contigResult[] = new int[n * m];
        int result[][] = new int[n][m];

        // liniarize the arrays
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < m; ++j) {
                contigFirst[i * n + j] = first[i][j];
            }
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < m; ++j) {
                contigResult[i * n + j] = second[i][j];
            }

        // make the distribution
        int numElems[] = new int[numThreads]; // the array with distribution of elems for each thread
        for(int i = 0; i < numThreads; ++i) {
            numElems[i] = (int)(n*m / numThreads);  // distribute them evenly
            if(i < (n*m) % numThreads)
                numElems[i]++;  // add te remaining ones
        }

        int currentElem = 0;
        List<Thread> threads = new ArrayList<>(numThreads);
        for(int i = 0; i < numThreads; ++i) {
            final int startPos = currentElem;
            final int stopPos = startPos + numElems[i];

            currentElem = stopPos; // the start position of the next partition

            // spawn the threads
            Thread thread = new Thread(() -> {
                for(int j = startPos; j < stopPos; ++j)
                    contigResult[j] += contigFirst[j]; // sum it up
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

        // transfer them back to the matrix
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < m; ++j) {
                result[i][j] = contigResult[i * n + j];
            }

        return result;
    }

    /**
     * Prints m numbers concurrently on n threads.
     * @param n the number of threads
     * @param m the number of numbers
     */
    public static void foo(int n, int m) {
        for(int i = 0; i < n; ++i) {
            final int threadNum = i;
            Thread thread = new Thread(() -> {
                String name = Thread.currentThread().getName();

                for(int j = 0; j < m; ++j) {
                    System.out.println("Thread " + threadNum + " - " + j);

                    // a bit of delay to see the concurrency better
                    try {
                        // random to observe the threads better
                        int delay = ThreadLocalRandom.current().nextInt(0, 1000);
                        TimeUnit.MILLISECONDS.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
}
