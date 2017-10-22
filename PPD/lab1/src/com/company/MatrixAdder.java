package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;

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
    public static<T> ArrayList<ArrayList<T>> add (ArrayList<ArrayList<T>> first, ArrayList<ArrayList<T>> second, int numThreads, BinaryOperator<T> op) {
        final int n = first.size();
        final int m = first.get(0).size();
        ArrayList<T> contigFirst = new ArrayList<T>(n * m);
        ArrayList<T> contigSecond = new ArrayList<T>(n * m);
        ArrayList<T> contigResult = new ArrayList<T>(Collections.nCopies(n*m, null));

        // liniarize the arrays
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < m; ++j) {
                contigFirst.add(first.get(i).get(j));
            }
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < m; ++j) {
                contigSecond.add(second.get(i).get(j));
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
                    contigResult.set(j, op.apply(contigFirst.get(j), contigSecond.get(j))); // sum it up
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

        ArrayList<ArrayList<T>> result = new ArrayList<>(n);
        // deliniarize them back to matrix form
        for(int i = 0; i < n; ++i) {
            result.add(new ArrayList<>(m));
            for (int j = 0; j < m; ++j) {
                result.get(i).add(contigResult.get(i * n + j));
            }
        }

        return result;
    }

}
