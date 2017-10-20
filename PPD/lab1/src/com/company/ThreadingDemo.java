package com.company;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ThreadingDemo {
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
